package com.CLMTZ.Backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.security.Access;

public interface IAccessRepository extends JpaRepository<Access, Integer> {

}
