package com.angusyg.winecellar.user.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String name;
}
