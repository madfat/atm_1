package com.atm.service;

import com.atm.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenuScreen(String screenOwner);
}
