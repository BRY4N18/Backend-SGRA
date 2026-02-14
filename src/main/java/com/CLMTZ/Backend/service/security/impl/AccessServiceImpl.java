package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.AccessDTO;
import com.CLMTZ.Backend.model.security.Access;
import com.CLMTZ.Backend.repository.security.IAccessRepository;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.service.security.IAccessService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements IAccessService {

    private final IAccessRepository repository;
    private final IUserRepository userRepository;

    @Override
    public List<AccessDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public AccessDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("Access not found with id: " + id)); }

    @Override
    public AccessDTO save(AccessDTO dto) {
        Access e = new Access();
        e.setUsername(dto.getUsername()); e.setPassword(dto.getPassword()); e.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getUserId() != null) e.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public AccessDTO update(Integer id, AccessDTO dto) {
        Access e = repository.findById(id).orElseThrow(() -> new RuntimeException("Access not found with id: " + id));
        e.setUsername(dto.getUsername()); e.setPassword(dto.getPassword()); e.setState(dto.getState());
        if (dto.getUserId() != null) e.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private AccessDTO toDTO(Access e) {
        AccessDTO d = new AccessDTO();
        d.setAccessId(e.getAccessId()); d.setUsername(e.getUsername()); d.setPassword(e.getPassword()); d.setState(e.getState());
        d.setUserId(e.getUser() != null ? e.getUser().getUserId() : null);
        return d;
    }
}
