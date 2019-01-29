package com.angusyg.winecellar.model.user.entity;

import com.angusyg.winecellar.model.user.config.Role;
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
  private Long id;

  // User name
  @NotNull
  @Column(nullable = false, unique = true)
  private String username;

  // User password
  @NotNull
  @Column(nullable = false)
  private String password;

  // User email address
  @NotNull
  @Email
  @Column(nullable = false, unique = true)
  private String email;

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
  private Set<Role> roles;

  private boolean accountNonExpired;

  private boolean accountNonLocked;

  private boolean credentialsNonExpired;

  private boolean enabled;

  public User() {
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
    this.roles = new HashSet<>(Arrays.asList(Role.USER));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(getRolesString());
  }

  public void setRoles(String roles) {
    this.roles = Arrays.stream(roles.split(","))
        .map((r) -> Enum.valueOf(Role.class, r))
        .collect(Collectors.toSet());
  }

  public String getRolesString() {
    return StringUtils.collectionToCommaDelimitedString(
        this.roles.stream()
            .map(Enum::name)
            .collect(Collectors.toList()));
  }
}

