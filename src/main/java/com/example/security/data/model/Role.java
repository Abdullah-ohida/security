package com.example.security.data.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Role {
    @Id
    private Long id;

    @NotNull
    private RoleName roleName;
}
