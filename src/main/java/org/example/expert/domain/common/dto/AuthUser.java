package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.security.UserRoleEnum;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final UserRoleEnum userRoleEnum;

    public AuthUser(Long id, String email, UserRoleEnum userRoleEnum) {
        this.id = id;
        this.email = email;
        this.userRoleEnum = userRoleEnum;
    }
}
