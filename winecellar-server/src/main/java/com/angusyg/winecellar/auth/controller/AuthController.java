package com.angusyg.winecellar.auth.controller;

import com.angusyg.winecellar.core.web.controller.ApiController;
import com.angusyg.winecellar.user.dao.UserDao;
import com.angusyg.winecellar.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController extends ApiController {
  @Autowired
  private UserDao userDao;

  @GetMapping("/signup")
  @Transactional
  public Iterable<User> signup() {
    return userDao.findAll();
//
//        return "Signed Up!!";
  }
}
