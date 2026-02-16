package com.CLMTZ.Backend.controller.academic;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.academic.CoordinationDTO;
import com.CLMTZ.Backend.dto.academic.StudentLoadDTO;
import com.CLMTZ.Backend.service.academic.ICoordinationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/academic/coordinations")
@RequiredArgsConstructor
public class CoordinationController {

    private final ICoordinationService service;

    @GetMapping
    public ResponseEntity<List<CoordinationDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<CoordinationDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<CoordinationDTO> save(@RequestBody CoordinationDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<CoordinationDTO> update(@PathVariable Integer id, @RequestBody CoordinationDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/upload-students")
    public ResponseEntity<List<String>> uploadStudents(@RequestBody List<StudentLoadDTO> dtos) {
        return ResponseEntity.ok(service.uploadStudents(dtos));
    }
}
