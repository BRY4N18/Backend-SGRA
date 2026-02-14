package com.CLMTZ.Backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.security.RoleManagementModule;

public interface IRoleManagementModuleRepository extends JpaRepository<RoleManagementModule, Integer> {

}
