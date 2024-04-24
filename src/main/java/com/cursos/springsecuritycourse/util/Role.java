package com.cursos.springsecuritycourse.util;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role {

    CUSTOMER(List.of(Permission.READ_ALL_PRODUCTS)),
    ADMINISTRATOR(List.of(Permission.SAVE_ONE_PRODUCT, Permission.READ_ALL_PRODUCTS));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
