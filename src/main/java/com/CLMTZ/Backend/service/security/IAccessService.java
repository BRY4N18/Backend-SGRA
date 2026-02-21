package com.CLMTZ.Backend.service.security;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Request.AccessRequestDTO;

public interface IAccessService {
    List<AccessRequestDTO> findAll();
    AccessRequestDTO findById(Integer id);
    AccessRequestDTO save(AccessRequestDTO dto);
    AccessRequestDTO update(Integer id, AccessRequestDTO dto);
    void deleteById(Integer id);
}
