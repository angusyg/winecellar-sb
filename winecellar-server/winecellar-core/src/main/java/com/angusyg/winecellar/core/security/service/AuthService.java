package com.angusyg.winecellar.core.security.service;

import com.angusyg.winecellar.core.model.exception.DuplicateDataException;
import com.angusyg.winecellar.core.security.dto.LoginDTO;
import com.angusyg.winecellar.core.security.dto.SignupDTO;
import com.angusyg.winecellar.core.security.exception.BadCredentialsPasswordException;
import com.angusyg.winecellar.core.security.exception.BadCredentialsUsernameException;
import com.angusyg.winecellar.core.security.jwt.dto.JwtTokenPayloadDTO;
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
   * @throws BadCredentialsUsernameException when username is not found
   * @throws BadCredentialsPasswordException when password is not valid
   */
  public String login(@NotNull LoginDTO loginDto) throws BadCredentialsUsernameException, BadCredentialsPasswordException {
    // Get user from repository by its username
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
   * @throws DuplicateDataException when given data (username or email) already exists in repository
   */
  public String signup(@NotNull SignupDTO signupDTO) throws DuplicateDataException {
    // Check if username already exists in repository
    if (authDAO.existsByUsername(signupDTO.getUsername())) {
      throw new DuplicateDataException(messageSourceAccessor.getMessage("auth.duplicatedata.username", new Object[]{signupDTO.getUsername()}));
    }
    // Check if email already exists in repository
    if (authDAO.existsByEmail(signupDTO.getEmail())) {
      throw new DuplicateDataException(messageSourceAccessor.getMessage("auth.duplicatedata.email", new Object[]{signupDTO.getEmail()}));
    }
    // Create user
    User user = new User(signupDTO.getUsername(), signupDTO.getPassword(), signupDTO.getEmail());
    // Persist user
    authDAO.save(user);
    // Generate JWT token for new user
    return generateToken(user.getId(), user.getUsername(), user.getRolesAsString());
  }

  /**
   * Generates a JWT Token from user infos
   *
   * @param id          user id
   * @param username    user name
   * @param authorities user authorities
   * @return JWT Token as string
   */
  private String generateToken(Long id, String username, String authorities) {
    JwtTokenPayloadDTO jwtTokenPayloadDTO = new JwtTokenPayloadDTO();
    jwtTokenPayloadDTO.setId(String.valueOf(id));
    jwtTokenPayloadDTO.setUsername(username);
    jwtTokenPayloadDTO.setAuthorities(authorities);
    return jwtUtils.generateToken(jwtTokenPayloadDTO);
  }
}
