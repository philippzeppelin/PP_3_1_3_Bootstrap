package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;

public class RoleEntityDetails implements GrantedAuthority {
    private String roleName;

    public RoleEntityDetails(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}