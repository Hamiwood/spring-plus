package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.security.UserRoleEnum;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRoleEnum;

    public User(String email, String username, String password, UserRoleEnum userRoleEnum) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRoleEnum = userRoleEnum;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRoleEnum userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }
}
