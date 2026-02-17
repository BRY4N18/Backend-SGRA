package com.CLMTZ.Backend.repository.security;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CLMTZ.Backend.model.security.UserManagement;
@Repository
public interface IUserManagementRepository extends JpaRepository<UserManagement, Integer> {
    @Procedure(procedureName = "seguridad.sp_in_creargusuario")
    Map<String, Object> createUserSp(
        @Param("p_gusuario") String usuario, 
        @Param("p_gcontrasena") String password
    );
}
