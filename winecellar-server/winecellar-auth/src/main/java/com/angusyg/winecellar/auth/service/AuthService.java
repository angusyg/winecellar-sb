package com.angusyg.winecellar.auth.service;

import com.angusyg.winecellar.auth.dto.LoginDTO;
import com.angusyg.winecellar.auth.exception.BadCredentialsException;
import com.angusyg.winecellar.core.security.jwt.JwtTokenPayload;
import com.angusyg.winecellar.core.security.jwt.util.JwtUtils;
import com.angusyg.winecellar.model.user.dao.UserDao;
import com.angusyg.winecellar.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authentication service.
 *
 * @since 0.0.1
 */
@Service
public class AuthService {
  @Autowired
  private MessageSourceAccessor messageSourceAccessor;

  @Autowired
  private UserDao userDao;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  /**
   * Logs in a user with its username and password
   *
   * @param loginDto login infos (username and password)
   * @return JWT Token as string
   */
  public String login(LoginDTO loginDto) throws BadCredentialsException {
    // Gets user from repository by its username
    User user = userDao.findByUsername(loginDto.getUsername());
    if (user == null) {
      // No user found with username
      throw new BadCredentialsException(messageSourceAccessor.getMessage("auth.badcredentials.username", new Object[] {loginDto.getUsername()}));
    }
    if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      // Provided password matches user password, generates JWT token
      JwtTokenPayload jwtTokenPayload = new JwtTokenPayload();
      jwtTokenPayload.setId(String.valueOf(user.getId()));
      jwtTokenPayload.setUsername(user.getUsername());
      jwtTokenPayload.setRoles(user.getRolesString());
      return jwtUtils.generateToken(jwtTokenPayload);
    } else {
      // Bad password for user
      throw new BadCredentialsException(messageSourceAccessor.getMessage("auth.badcredentials.password"));
    }
  }
}
