package com.angusyg.winecellar.user.controller;

import com.angusyg.winecellar.core.model.controller.ModelController;
import com.angusyg.winecellar.user.entity.User;
import com.angusyg.winecellar.user.dto.UserDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends ModelController<User, Long, UserDto> {
}
