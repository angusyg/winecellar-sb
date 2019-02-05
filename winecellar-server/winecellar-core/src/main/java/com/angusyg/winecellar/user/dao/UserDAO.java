package com.angusyg.winecellar.user.dao;

import com.angusyg.winecellar.user.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User resource repository.
 *
 * @since 0.0.1
 */
public interface UserDAO extends PagingAndSortingRepository<User, Long> {
  /**
   * Finds a user by its username.
   *
   * @param username the username to look for
   * @return the user found with given username
   */
  User findByUsername(String username);

  /**
   * Checks if a username already exists
   *
   * @param username username to look for
   * @return true if username already exists, false otherwise
   */
  boolean existsByUsername(String username);

  /**
   * Checks if an email already exists
   *
   * @param email email to look for
   * @return true if email already exists, false otherwise
   */
  boolean existsByEmail(String email);
}
