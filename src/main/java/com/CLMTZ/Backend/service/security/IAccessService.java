package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.AccessDTO;

public interface IAccessService {
    List<AccessDTO> findAll();
    AccessDTO findById(Integer id);
    AccessDTO save(AccessDTO dto);
    AccessDTO update(Integer id, AccessDTO dto);
    void deleteById(Integer id);
}
