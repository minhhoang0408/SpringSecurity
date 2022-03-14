package com.example.demo.Permission;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.Permission.Permission.*;

public enum Roles {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_WRITE, STUDENT_WRITE, STUDENT_READ));

    private final Set<Permission> permissions;

    Roles(Set<Permission> role) {
        this.permissions = role;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return simpleGrantedAuthorities;
    }
}
