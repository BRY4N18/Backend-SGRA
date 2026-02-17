package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.security.SpResponseDTO;
import com.CLMTZ.Backend.dto.security.UserManagementDTO;
import com.CLMTZ.Backend.model.security.UserManagement;
import com.CLMTZ.Backend.repository.security.IUserManagementRepository;
import com.CLMTZ.Backend.service.security.IUserManagementService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements IUserManagementService {

    private final IUserManagementRepository userRepository;

    @Override
    public List<UserManagementDTO> findAll() { return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public UserManagementDTO findById(Integer id) { return userRepository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("UserManagement not found with id: " + id)); }

    @Override
    public UserManagementDTO save(UserManagementDTO dto) {
        UserManagement e = new UserManagement();
        e.setUser(dto.getUser()); e.setPassword(dto.getPassword()); e.setState(dto.getState() != null ? dto.getState() : true);
        return toDTO(userRepository.save(e));
    }

    @Override
    public UserManagementDTO update(Integer id, UserManagementDTO dto) {
        UserManagement e = userRepository.findById(id).orElseThrow(() -> new RuntimeException("UserManagement not found with id: " + id));
        e.setUser(dto.getUser()); e.setPassword(dto.getPassword()); e.setState(dto.getState());
        return toDTO(userRepository.save(e));
    }

    @Override
    public void deleteById(Integer id) { userRepository.deleteById(id); }

    private UserManagementDTO toDTO(UserManagement e) {
        UserManagementDTO d = new UserManagementDTO();
        d.setUserGId(e.getUserGId()); d.setUser(e.getUser()); d.setPassword(e.getPassword()); d.setState(e.getState());
        return d;
    }

    @Override
    @Transactional
    public SpResponseDTO createUser(UserManagementDTO userRequest){

        SpResponseDTO responseDTO;

        try {
            Map<String, Object> responseSp = userRepository.createUserSp(userRequest.getUser(), userRequest.getPassword());

            String message = (String) responseSp.get("p_mensaje");
            Boolean success = (Boolean) responseSp.get("p_exito");

            responseDTO = new SpResponseDTO(message, success);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new SpResponseDTO("Error al ejecutar el SP: " + e.getMessage(), false);  
        }
        return responseDTO;
    }
}
