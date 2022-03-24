package com.example.springsecurityrolepermission.repository;

import com.example.springsecurityrolepermission.entity.Role;
import com.example.springsecurityrolepermission.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
