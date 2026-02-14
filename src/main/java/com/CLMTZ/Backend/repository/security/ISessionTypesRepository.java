package com.CLMTZ.Backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.security.SessionTypes;

public interface ISessionTypesRepository extends JpaRepository<SessionTypes, Integer> {

}
