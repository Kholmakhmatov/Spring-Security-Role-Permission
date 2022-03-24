package com.example.springsecurityrolepermission.entity;

import com.example.springsecurityrolepermission.entity.enums.PermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    @ManyToMany
    private Set<Role> roles;
    @Column(unique = true, nullable = false)
    private String userName;//phone login
    private String password;
    private boolean enabled = true; //tizimdan foydalanish huquqi
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        for (Role role : this.roles) {
            Set<PermissionEnum> permissionEnums = role.getPermissionEnums();
            for (PermissionEnum permissionEnum : permissionEnums) {
                authoritySet.add(new SimpleGrantedAuthority(permissionEnum.name()));
            }
        }
        return authoritySet;

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User(String fullName, Set<Role> roles, String userName, String password, boolean enabled) {
        this.fullName = fullName;
        this.roles = roles;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }
}
