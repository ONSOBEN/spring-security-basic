package co.istad.springsecuritybasic.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Table(name = "users_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
private String id;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean isDisabled;
    private boolean isAccountLocked;
    private boolean isAccountExpired;
    private boolean isCredentialsExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

}
