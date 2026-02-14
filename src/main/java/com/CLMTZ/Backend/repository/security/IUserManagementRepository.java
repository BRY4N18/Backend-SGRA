package com.CLMTZ.Backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.security.UserManagement;

public interface IUserManagementRepository extends JpaRepository<UserManagement, Integer> {

}
