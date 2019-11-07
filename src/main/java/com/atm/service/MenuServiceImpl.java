package com.atm.service;

import com.atm.model.Menu;
import com.atm.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Override
    public List<Menu> getMenuScreen(String screenOwner) {
        return menuRepository.findByScreenOwner(screenOwner);
    }
}
