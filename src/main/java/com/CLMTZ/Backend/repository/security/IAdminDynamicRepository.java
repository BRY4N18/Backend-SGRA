package com.CLMTZ.Backend.repository.security;

import com.CLMTZ.Backend.dto.security.Response.SpResponseDTO;

/**
 * Repositorio para operaciones de administración que requieren sesión dinámica.
 * Usa las credenciales del usuario logueado para ejecutar las operaciones en la BD.
 */
public interface IAdminDynamicRepository {

    // ==================== USUARIOS ====================
    /**
     * Actualiza un usuario de gestión.
     * Ejecuta: seguridad.sp_up_gusuario
     */
    //SpResponseDTO updateGUser(Integer userId, String user, String password, String roles);

    // ==================== ROLES ====================

    /**
     * Crea un rol de gestión.
     * Ejecuta: seguridad.sp_in_creargrol
     */
    SpResponseDTO createGRole(String role, String description);

    /**
     * Actualiza un rol de gestión.
     * Ejecuta: seguridad.sp_up_grol
     */
    SpResponseDTO updateGRole(Integer roleId, String role, String description);
}

