package com.atm.controller;

import com.atm.model.*;
import com.atm.repository.MenuRepository;
import com.atm.service.AccountService;
import com.atm.service.MenuService;
import com.atm.service.TransactionService;
import com.atm.service.ValidationService;
import com.atm.utils.Constant;
import com.atm.validator.TransferValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AtmController {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private TransferValidator transferValidator;

    @GetMapping("/login")
    public String loadLogin(@ModelAttribute("loginParameter") LoginParam loginParam, ModelMap model, String error, String logout){
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({""})
    public String login(@ModelAttribute("loginParameter") LoginParam loginParameter, ModelMap model, BindingResult bindingResult) {
        return toMainMenu(model);
    }

    @PostMapping({"/withdraw"})
    public String otherWithdraw(OtherAmountParam param, ModelMap model) {
        Account loginUser = authenticateUser();

        String error = validationService.withdrawalValidation(loginUser.getAccountNo(), param.getAmount());
        if (error != null) {
            model.addAttribute("error", error);
            return "withdraw_other";
        }

        // process deduct
        Transaction trx = transactionService.withdrawProcess(loginUser.getAccountNo(), param.getAmount());
        model.addAttribute("transaction_summary", trx);
        model.addAttribute("trxType", Constant.TRX_TYPE.WD);
        // print receipt
        return "receipt";
    }

    private String toMainMenu(ModelMap model) {
        getPageMenu(model, "main_menu");
        return "welcome";
    }

    private void getPageMenu(ModelMap model, String ownerScreen) {
        List<Menu> menus = menuService.getMenuScreen(ownerScreen);
        model.addAttribute("menus", menus.isEmpty()?noMenu():menus);
    }

    @GetMapping({"/withdraw"})
    public String withdraw(ModelMap model, @RequestParam(required = false, name = "amount") String amount) {
        Account loginUser = authenticateUser();

        getPageMenu(model, "withdraw_menu");
        if (amount != null && amount.matches("-?\\d+(\\.\\d+)?")) {
            String error = validationService.withdrawalValidation(loginUser.getAccountNo(),Double.valueOf(amount));
            if (error != null) {
                model.addAttribute("error", error);
                return "withdraw?amount=";
            }
            // process deduct
            Transaction trx = transactionService.withdrawProcess(loginUser.getAccountNo(),Double.valueOf(amount));
            trx.setRefNo("-");
            trx.setDestinationAccount("-");
            model.addAttribute("transaction_summary", trx);
            model.addAttribute("trxType", Constant.TRX_TYPE.WD);
            // print receipt
            return "receipt";
        } else if (amount.equals("other")){
            // load other amount screen
            return "withdraw_other";
        }

        return "withdraw";
    }

    @GetMapping({"/transfer"})
    public String transfer(@ModelAttribute("param") TransferParam param, ModelMap model, BindingResult bindingResult) {
        TransferParam tp = new TransferParam();
        tp.setReference(transactionService.getRefNo());
        model.addAttribute("tfParam", tp);
        return "transfer";
    }

    @PostMapping({"/transfer"})
    public String transferProcess(@ModelAttribute("param") TransferParam param, ModelMap model,BindingResult bindingResult) throws ValidationException {
        Account loginUser = authenticateUser();

        transferValidator.validate(param, bindingResult);
        if (bindingResult.hasErrors())
            return "transfer";

        Transaction trx = transactionService.transferProcess(loginUser.getAccountNo(),param.getDstAccountNo(), param.getTrxAmount(), param.getReference());
        model.addAttribute("transaction_summary", trx);
        model.addAttribute("trxType", Constant.TRX_TYPE.TF);
        model.addAttribute("refNo", param.getReference());
        // add destination account balance
        return "receipt";
    }

    @GetMapping({"/history"})
    public String history(@ModelAttribute("param") TrxSearchParam param, ModelMap model) {
        getPageMenu(model, "history_menu");
        return "history";
    }

    @GetMapping({"/top10history"})
    public String top10History(ModelMap model) {
        Account loginUser = authenticateUser();

        model.addAttribute("transaction_list",transactionService.getTransactionList(loginUser.getAccountNo()));
        model.addAttribute("acct", loginUser.getAccountNo());
        model.addAttribute("acctName", loginUser.getName());
        model.addAttribute("balance", accountService.getAccountDetail(loginUser.getAccountNo()).getBalance());
        return "history_list";
    }

    private Account authenticateUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account userDetail = null;
        if (principal instanceof UserDetails) {
            String loginUser = ((UserDetails) principal).getUsername();
            userDetail = accountService.getAccountDetail(loginUser);
        }
        return userDetail;
    }

    @PostMapping({"/history"})
    public String getHistory(@ModelAttribute("param") TrxSearchParam param, ModelMap model){
        Account userDetail = authenticateUser();

        String[] startdate = param.getStartDate().split("/");
        String[] endate = param.getEndDate().split("/");
        LocalDateTime start = LocalDateTime.of(Integer.valueOf(startdate[2]),Integer.valueOf(startdate[0]),Integer.valueOf(startdate[1]),00,00,00);
        LocalDateTime end = LocalDateTime.of(Integer.valueOf(endate[2]),Integer.valueOf(endate[0]),Integer.valueOf(endate[1]),00,00,00).plusDays(1);

        model.addAttribute("transaction_list",transactionService.getByDateRange(userDetail.getAccountNo(), start, end));

        model.addAttribute("acct", userDetail.getAccountNo());
        model.addAttribute("acctName", userDetail.getName());
        model.addAttribute("balance", accountService.getAccountDetail(userDetail.getAccountNo()).getBalance());
        return "history_list";
    }

    @GetMapping({"/history-all"})
    public String getHistoryPaged(@ModelAttribute("param") TrxSearchParam param, ModelMap model, @PageableDefault(value = 10) Pageable pageable){
        Account userDetail = authenticateUser();

        Page<Transaction> transactionPage = transactionService.getAllTransactionList(userDetail.getAccountNo(), pageable);

        model.addAttribute("transaction_list",transactionPage.getContent());
        model.addAttribute("total_elements", transactionPage.getTotalElements());
        model.addAttribute("number",transactionPage.getNumber());
        model.addAttribute("number_of_elements",transactionPage.getNumberOfElements());
        model.addAttribute("size", transactionPage.getSize());
        model.addAttribute("total_pages", transactionPage.getTotalPages());

        model.addAttribute("acct", userDetail.getAccountNo());
        model.addAttribute("acctName", userDetail.getName());
        model.addAttribute("balance", accountService.getAccountDetail(userDetail.getAccountNo()).getBalance());
        return "history_list_2";
    }

    private List<Menu> noMenu(){
        List<Menu> noMenu = new ArrayList<>();
        Menu menu = new Menu();
        menu.setMenuId("6bae670323a74f679c20ab3exxc277cc");
        menu.setDescription("No menu provided on database");
        menu.setSequence(0);
        menu.setStatus(true);
        menu.setScreenOwner("no_menu");
        noMenu.add(menu);
        return noMenu;
    }

    @GetMapping({"/main"})
    public String main(ModelMap model){
        return toMainMenu(model);
    }
}
