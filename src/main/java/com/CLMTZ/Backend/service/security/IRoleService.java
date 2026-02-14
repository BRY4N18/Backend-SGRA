package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.RoleDTO;

public interface IRoleService {
    List<RoleDTO> findAll();
    RoleDTO findById(Integer id);
    RoleDTO save(RoleDTO dto);
    RoleDTO update(Integer id, RoleDTO dto);
    void deleteById(Integer id);
}
