package com.angusyg.winecellar.model.user.dao;

import com.angusyg.winecellar.model.user.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User resource repository.
 *
 * @since 0.0.1
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {
  /**
   * Find a user by its username.
   *
   * @param username the username to look at
   * @return the user found with given username
   */
  User findByUsername(String username);
}
