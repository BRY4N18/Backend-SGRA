package com.CLMTZ.Backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.security.RoleManagement;

public interface IRoleManagementRepository extends JpaRepository<RoleManagement, Integer> {

}
