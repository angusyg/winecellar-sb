package com.angusyg.winecellar.user.service;

import com.angusyg.winecellar.core.model.service.ModelService;
import com.angusyg.winecellar.user.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * User resource service.
 * It is based on {@link ModelService} for standard methods.
 *
 * @since 0.0.1
 */
@Service
public class UserModelService extends ModelService<User, Long> {
  @PostConstruct
  private void init() {
    User u = new User();
    u.setEmail("mail@email.com");
    u.setUsername("admin");
    u.setPassword("$2a$10$Qb2co9k7ADlXaOHdenIPbuOBVdJStqsgF3bR5k7yzYqFMgnHMIxDW");
    u.setRoles("USER,ADMIN");
    repository.save(u);
  }
}
