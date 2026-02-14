package com.CLMTZ.Backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.security.UsersRoles;

public interface IUsersRolesRepository extends JpaRepository<UsersRoles, Integer> {

}
