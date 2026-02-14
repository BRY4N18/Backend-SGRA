package com.CLMTZ.Backend.controller.security;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.security.ModuleManagementDTO;
import com.CLMTZ.Backend.service.security.IModuleManagementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security/module-managements")
@RequiredArgsConstructor
public class ModuleManagementController {

    private final IModuleManagementService service;

    @GetMapping
    public ResponseEntity<List<ModuleManagementDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleManagementDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<ModuleManagementDTO> save(@RequestBody ModuleManagementDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleManagementDTO> update(@PathVariable Integer id, @RequestBody ModuleManagementDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }
}
