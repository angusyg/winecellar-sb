package com.angusyg.winecellar.model.user.controller;

import com.angusyg.winecellar.model.core.controller.ModelController;
import com.angusyg.winecellar.model.user.entity.User;
import com.angusyg.winecellar.model.user.dto.UserDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User resource controller.
 * It is based on {@link ModelController} for standard methods.
 *
 * @since 0.0.1
 */
@RestController
@RequestMapping("/users")
public class UserController extends ModelController<User, Long, UserDto> {}
