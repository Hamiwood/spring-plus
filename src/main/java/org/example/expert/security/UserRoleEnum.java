package org.example.expert.security;

import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.enums.UserRole;

import java.util.Arrays;

public enum UserRoleEnum {
    USER("ROLE_USER"),  // 사용자 권한
    ADMIN("ROLE_ADMIN");  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static UserRoleEnum of(String role) {
        return Arrays.stream(UserRoleEnum.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UserRole"));
    }
}
