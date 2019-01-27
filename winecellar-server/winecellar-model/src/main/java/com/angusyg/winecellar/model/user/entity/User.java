package com.angusyg.winecellar.model.user.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    @OneToMany
    private Set<Role> roles;
}

