package com.CLMTZ.Backend.service.security;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Request.UserRoleManagementRequestDTO;

public interface IUserRoleManagementService {
    List<UserRoleManagementRequestDTO> findAll();
    UserRoleManagementRequestDTO findById(Integer id);
    UserRoleManagementRequestDTO save(UserRoleManagementRequestDTO dto);
    UserRoleManagementRequestDTO update(Integer id, UserRoleManagementRequestDTO dto);
    void deleteById(Integer id);
}
