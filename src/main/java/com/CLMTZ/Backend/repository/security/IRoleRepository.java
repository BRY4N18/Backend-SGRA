package com.CLMTZ.Backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.security.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

}
