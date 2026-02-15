package com.CLMTZ.Backend.controller.security;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.security.UserUserManagementDTO;
import com.CLMTZ.Backend.service.security.IUserUserManagementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security/user-user-managements")
@RequiredArgsConstructor
public class UserUserManagementController {

    private final IUserUserManagementService service;

    @GetMapping
    public ResponseEntity<List<UserUserManagementDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<UserUserManagementDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<UserUserManagementDTO> save(@RequestBody UserUserManagementDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<UserUserManagementDTO> update(@PathVariable Integer id, @RequestBody UserUserManagementDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }
}
