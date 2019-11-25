package com.atm.controller;

import com.atm.model.*;
import com.atm.repository.MenuRepository;
import com.atm.service.*;
import com.atm.utils.Constant;
import com.atm.validator.TransferValidator;
import com.atm.validator.UserValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.atm.utils.Constant.loginAccount;

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
    private UserValidator userValidator;
    @Autowired
    private TransferValidator transferValidator;

    @GetMapping("/")
    public String loadLogin(@ModelAttribute("loginParameter") LoginParam loginParameter, BindingResult bindingResult){
        return "login";
    }

    @PostMapping({"/","/login"})
    public String login(@ModelAttribute("loginParameter") LoginParam loginParameter, ModelMap model, BindingResult bindingResult) {
        userValidator.validate(loginParameter, bindingResult);
        if (bindingResult.hasErrors())
            return "login";

        return toMainMenu(model);
    }

    @PostMapping({"/withdraw"})
    public String otherWithdraw(OtherAmountParam param, ModelMap model) {
        String error = validationService.withdrawalValidation(loginAccount.getAccountNo(), param.getAmount());
        if (error != null) {
            model.addAttribute("error", error);
            return "withdraw_other";
        }

        // process deduct
        Transaction trx = transactionService.withdrawProcess(loginAccount.getAccountNo(),param.getAmount());
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
        getPageMenu(model, "withdraw_menu");
        if (amount != null && amount.matches("-?\\d+(\\.\\d+)?")) {
            String error = validationService.withdrawalValidation(loginAccount.getAccountNo(),Double.valueOf(amount));
            if (error != null) {
                model.addAttribute("error", error);
                return "withdraw?amount=";
            }
            // process deduct
            Transaction trx = transactionService.withdrawProcess(loginAccount.getAccountNo(),Double.valueOf(amount));
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
        transferValidator.validate(param, bindingResult);
        if (bindingResult.hasErrors())
            return "transfer";

        Transaction trx = transactionService.transferProcess(loginAccount.getAccountNo(),param.getDstAccountNo(), param.getTrxAmount(), param.getReference());
        model.addAttribute("transaction_summary", trx);
        model.addAttribute("trxType", Constant.TRX_TYPE.TF);
        model.addAttribute("refNo", param.getReference());
        // add destination account balance
        return "receipt";
    }

    @GetMapping({"/history"})
    public String history(ModelMap model) {
        getPageMenu(model, "history_menu");
        return "history";
    }

    @PostMapping({"/history"})
    public String getHistory(TrxSearchParam param, ModelMap model){
        String startDate = Constant.dft.format(Constant.dft_notime.parse(param.getStartDate()+" 00:00:00"));
        String endDate = LocalDate.parse(Constant.dft.format(Constant.dft_notime.parse(param.getEndDate()+" 00:00:00")),Constant.dft).plusDays(1).toString();
        model.addAttribute("transaction_list",transactionService.getByDateRange(startDate, endDate));
        model.addAttribute("acct", loginAccount.getAccountNo());
        model.addAttribute("acctName", loginAccount.getName());
        model.addAttribute("balance", accountService.getAccountDetail(loginAccount.getAccountNo()).getBalance());
        return "history_list";
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

    @GetMapping({"/logout"})
    public String logout(@ModelAttribute("loginParameter") LoginParam loginParameter, BindingResult bindingResult){
        BeanUtils.copyProperties(new Account(), loginAccount);
        return "login";
    }

    @GetMapping({"/main"})
    public String main(ModelMap model){
        return toMainMenu(model);
    }
}
