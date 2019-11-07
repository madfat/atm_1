package com.atm.repository;

import com.atm.model.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<Menu,String> {
    List<Menu> findByScreenOwner(String screenOwner);
    Menu findByScreenOwnerAndSequence(String screenOwner, Integer sequence);
    Menu findByScreenOwnerAndStatus(String screenOwner, Boolean status);
}
