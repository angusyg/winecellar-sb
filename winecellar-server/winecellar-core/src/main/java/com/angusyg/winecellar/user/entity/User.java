package com.angusyg.winecellar.user.entity;

import com.angusyg.winecellar.user.config.Role;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User entity.
 *
 * @since 0.0.1
 */
@Data
@Entity(name = "USERS")
public class User implements UserDetails {
  // User technical id
  @Id
  @GeneratedValue
  protected Long id;

  // User name
  @NotNull
  @Column(nullable = false, unique = true)
  protected String username;

  // User password
  @NotNull
  @Column(nullable = false)
  protected String password;

  // User email address
  @NotNull
  @Email
  @Column(nullable = false, unique = true)
  protected String email;

  // User list of roles
  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @Cascade(value = CascadeType.REMOVE)
  @JoinTable(
      indexes = {@Index(name = "INDEX_USER_ROLE", columnList = "id")},
      name = "roles",
      joinColumns = @JoinColumn(name = "id")
  )
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  protected Set<Role> roles;

  private boolean accountNonExpired;

  private boolean accountNonLocked;

  private boolean credentialsNonExpired;

  private boolean enabled;

  /**
   * Creates an instance of {@link User} with default role (USER)
   */
  public User() {
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
    this.roles = new HashSet<>(Arrays.asList(Role.ROLE_USER));
  }

  /**
   * Creates on a instance of {@link User} with given username,
   * password and email and default role (USER).
   *
   * @param username user name
   * @param password user password
   * @param email    user email
   */
  public User(String username, String password, String email) {
    this();
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = new HashSet<>(Arrays.asList(Role.ROLE_USER));
  }

  /**
   * Creates on a instance of {@link User} with given username,
   * password and email and roles.
   *
   * @param username user name
   * @param password user password
   * @param email    user email
   * @param roles    user roles
   */
  public User(String username, String password, String email, HashSet<Role> roles) {
    this();
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles;
  }

  /**
   * Assigns roles to user from a comma delimited string of roles
   *
   * @param roles a comma delimited string of roles
   */
  public void setRoles(String roles) {
    this.roles = Arrays.stream(roles.split(","))
        .map((r) -> Enum.valueOf(Role.class, r))
        .collect(Collectors.toSet());
  }

  /**
   * Constructs a string of comma delimited string of roles from user roles.
   *
   * @return user roles as comma delimited string
   */
  public String getRolesAsString() {
    return StringUtils.collectionToCommaDelimitedString(
        this.roles.stream()
            .map(Enum::name)
            .collect(Collectors.toList()));
  }

  /**
   * Returns user authorities from user roles.
   *
   * @return a list of authorities for user
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(getRolesAsString());
  }
}

