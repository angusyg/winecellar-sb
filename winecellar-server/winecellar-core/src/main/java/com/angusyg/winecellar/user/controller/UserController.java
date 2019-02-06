package com.angusyg.winecellar.user.controller;

import com.angusyg.winecellar.core.model.controller.ModelController;
import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import com.angusyg.winecellar.user.dto.UserDTO;
import com.angusyg.winecellar.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * User resource controller.
 * It is based on {@link ModelController} for standard methods.
 *
 * @since 0.0.1
 */
@RestController
@RequestMapping("/users")
public class UserController extends ModelController<User, Long, UserDTO> {
  @Override
  @GetMapping
  @Secured("ROLE_ADMIN")
  public Collection<UserDTO> findAll(Pageable pageable, Limiteable limit, Sort sort) {
    return super.findAll(pageable, limit, sort);
  }

}
