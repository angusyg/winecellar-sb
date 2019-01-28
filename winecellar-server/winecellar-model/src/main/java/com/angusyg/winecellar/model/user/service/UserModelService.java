package com.angusyg.winecellar.model.user.service;

import com.angusyg.winecellar.model.core.service.ModelService;
import com.angusyg.winecellar.model.user.dao.UserDao;
import com.angusyg.winecellar.model.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User resource service.
 * It is based on {@link ModelService} for standard methods.
 *
 * @since 0.0.1
 */
@Service
public class UserModelService extends ModelService<User, Long> implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = ((UserDao) repository).findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with username %s", username));
    } else {
      return user;
    }
  }
}
