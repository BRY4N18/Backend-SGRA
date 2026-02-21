package com.CLMTZ.Backend.repository.security.impl;

import com.CLMTZ.Backend.config.DynamicDataSourceService;
import com.CLMTZ.Backend.dto.security.Response.SpResponseDTO;
import com.CLMTZ.Backend.repository.security.IAdminDynamicRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AdminDynamicRepositoryImpl implements IAdminDynamicRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final DynamicDataSourceService dynamicDataSourceService;

    // ==================== USUARIOS ====================
    // @Override
    // public SpResponseDTO updateGUser(Integer userId, String user, String password, String roles) {
    //     return executeStoredProcedure("seguridad", "sp_up_gusuario",
    //             new MapSqlParameterSource()
    //                     .addValue("p_idgusuario", userId)
    //                     .addValue("p_gusuario", user)
    //                     .addValue("p_gcontrasena", password));
    // }

    // ==================== ROLES ====================

    @Override
    public SpResponseDTO createGRole(String role, String description) {
        return executeStoredProcedure("seguridad", "sp_in_creargrol",
                new MapSqlParameterSource()
                        .addValue("p_grol", role)
                        .addValue("p_descripcion", description));
    }

    @Override
    public SpResponseDTO updateGRole(Integer roleId, String role, String description) {
        return executeStoredProcedure("seguridad", "sp_up_grol",
                new MapSqlParameterSource()
                        .addValue("p_idgrol", roleId)
                        .addValue("p_grol", role)
                        .addValue("p_descripcion", description));
    }

    // ==================== UTILIDADES ====================

    private SpResponseDTO executeStoredProcedure(String schema, String procedureName, MapSqlParameterSource params) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dynamicDataSourceService.getDataSource())
                    .withSchemaName(schema)
                    .withProcedureName(procedureName);

            Map<String, Object> result = jdbcCall.execute(params);

            String message = (String) result.get("p_mensaje");
            Boolean success = (Boolean) result.get("p_exito");

            return new SpResponseDTO(message, success);
        } catch (Exception e) {
            return new SpResponseDTO("Error: " + e.getMessage(), false);
        }
    }
}





