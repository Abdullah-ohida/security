package com.example.security.web.security;

import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Set;

import static com.example.security.web.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENTS(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

    private final Set<ApplicationUserPermission> permissions;



    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
