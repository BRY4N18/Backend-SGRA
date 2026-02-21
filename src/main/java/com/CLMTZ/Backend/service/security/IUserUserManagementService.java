package com.CLMTZ.Backend.service.security;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Request.UserUserManagementRequestDTO;

public interface IUserUserManagementService {
    List<UserUserManagementRequestDTO> findAll();
    UserUserManagementRequestDTO findById(Integer id);
    UserUserManagementRequestDTO save(UserUserManagementRequestDTO dto);
    UserUserManagementRequestDTO update(Integer id, UserUserManagementRequestDTO dto);
    void deleteById(Integer id);
}
