package io.test.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleUser {
    ROLE_USER("user"),ROLE_ADMIN("admin"),ROLE_MANAGER("manager");
    private final String role;

}
