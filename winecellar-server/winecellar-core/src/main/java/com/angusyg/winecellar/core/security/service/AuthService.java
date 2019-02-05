package com.angusyg.winecellar.core.security.service;

import com.angusyg.winecellar.core.model.exception.DuplicateDataException;
import com.angusyg.winecellar.core.security.dto.LoginDTO;
import com.angusyg.winecellar.core.security.dto.SignupDTO;
import com.angusyg.winecellar.core.security.exception.BadCredentialsPasswordException;
import com.angusyg.winecellar.core.security.exception.BadCredentialsUsernameException;
import com.angusyg.winecellar.core.security.jwt.JwtTokenPayload;
import com.angusyg.winecellar.core.security.jwt.util.JwtUtils;
import com.angusyg.winecellar.user.dao.UserDAO;
import com.angusyg.winecellar.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Authentication service.
 *
 * @since 0.0.1
 */
@Service
@Validated
public class AuthService {
  @Autowired
  private MessageSourceAccessor messageSourceAccessor;

  @Autowired
  private UserDAO authDAO;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  /**
   * Logs in a user with its username and password
   *
   * @param loginDto login infos (username and password)
   * @return JWT Token as string
   * @throws BadCredentialsUsernameException thrown when username is not found
   * @throws BadCredentialsPasswordException thrown when password is not valid
   */
  public String login(@NotNull LoginDTO loginDto) throws BadCredentialsUsernameException, BadCredentialsPasswordException {
    // Gets user from repository by its username
    User user = authDAO.findByUsername(loginDto.getUsername());
    if (user == null) {
      // No user found with username
      throw new BadCredentialsUsernameException(messageSourceAccessor.getMessage("auth.badcredentials.username", new Object[]{loginDto.getUsername()}));
    }
    if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      // Provided password matches user password, generates JWT token
      return generateToken(user.getId(), user.getUsername(), user.getRolesAsString());
    } else {
      // Bad password for user
      throw new BadCredentialsPasswordException(messageSourceAccessor.getMessage("auth.badcredentials.password"));
    }
  }

  /**
   * Signs up a new user.
   *
   * @param signupDTO new user infos
   * @return JWT Token as string
   * @throws DuplicateDataException thrown when given data (username or email) already exists in repository
   */
  public String signup(@NotNull SignupDTO signupDTO) throws DuplicateDataException {
    if (authDAO.existsByUsername(signupDTO.getUsername())) {
      throw new DuplicateDataException(messageSourceAccessor.getMessage("auth.duplicatedata.username", new Object[]{signupDTO.getUsername()}));
    }
    if (authDAO.existsByEmail(signupDTO.getEmail())) {
      throw new DuplicateDataException(messageSourceAccessor.getMessage("auth.duplicatedata.email", new Object[]{signupDTO.getEmail()}));
    }
    User user = new User(signupDTO.getUsername(), signupDTO.getPassword(), signupDTO.getEmail());
    authDAO.save(user);
    return generateToken(user.getId(), user.getUsername(), user.getRolesAsString());
  }

  /**
   * Generates a JWT Token from user infos
   *
   * @param id       user if
   * @param username user name
   * @param roles    user roles
   * @return JWT Token as string
   */
  private String generateToken(Long id, String username, String roles) {
    // Provided password matches user password, generates JWT token
    JwtTokenPayload jwtTokenPayload = new JwtTokenPayload();
    jwtTokenPayload.setId(String.valueOf(id));
    jwtTokenPayload.setUsername(username);
    jwtTokenPayload.setRoles(roles);
    return jwtUtils.generateToken(jwtTokenPayload);
  }
}
