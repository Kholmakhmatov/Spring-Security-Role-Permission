package com.example.springsecurityrolepermission.component;

import com.example.springsecurityrolepermission.entity.Company;
import com.example.springsecurityrolepermission.entity.Role;
import com.example.springsecurityrolepermission.entity.User;
import com.example.springsecurityrolepermission.entity.enums.PermissionEnum;
import com.example.springsecurityrolepermission.entity.enums.RoleEnum;
import com.example.springsecurityrolepermission.repository.CompanyRepository;
import com.example.springsecurityrolepermission.repository.RoleRepository;
import com.example.springsecurityrolepermission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    private String mode;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final CompanyRepository companyRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always") && ddl.equals("create")) {
            Role admin = new Role();
            admin.setName(RoleEnum.ADMIN);
            admin.setPermissionEnums(Arrays.stream(PermissionEnum.values()).collect(Collectors.toSet()));
            Role user_role = new Role();
            user_role.setName(RoleEnum.USER);
            user_role.setPermissionEnums(new HashSet<>(Arrays.asList(
                    PermissionEnum.READ_EMPLOYEE,
                    PermissionEnum.READ_ALL_EMPLOYEE)));
            Role manager = new Role();
            manager.setName(RoleEnum.MANAGER);
            manager.setPermissionEnums(new HashSet<>(Arrays.asList(
                    PermissionEnum.READ_ALL_EMPLOYEE,
                    PermissionEnum.EDIT_EMPLOYEE,
                    PermissionEnum.READ_EMPLOYEE)));

            roleRepository.save(admin);
            roleRepository.save(user_role);
            roleRepository.save(manager);

            Company ECMA = new Company();
            ECMA.setName("ECMA");
            companyRepository.save(ECMA);

            Set<Role> roles = new HashSet<>();
            roles.add(admin);
            roles.add(user_role);
            roles.add(manager);

            User user = new User("Anvar", roles, "anvar", passwordEncoder.encode("123"), true);
            userRepository.save(user);
        }
    }
}
