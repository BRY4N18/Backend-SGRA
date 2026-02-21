package com.CLMTZ.Backend.service.security;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Request.RoleRequestDTO;

public interface IRoleService {
    List<RoleRequestDTO> findAll();
    RoleRequestDTO findById(Integer id);
    RoleRequestDTO save(RoleRequestDTO dto);
    RoleRequestDTO update(Integer id, RoleRequestDTO dto);
    void deleteById(Integer id);
}
