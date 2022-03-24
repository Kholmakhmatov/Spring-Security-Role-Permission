package com.example.springsecurityrolepermission.repository;

import com.example.springsecurityrolepermission.entity.Company;
import com.example.springsecurityrolepermission.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
}
