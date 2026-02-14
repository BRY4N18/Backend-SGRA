package com.CLMTZ.Backend.model.security;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbgestionmodulos", schema = "seguridad")
public class ModuleManagement {
    @Id
    @Column(name = "idgmodulo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer roleGId;
    
    @Column(name = "gmodulo",length = 150, nullable = false)
    private String moduleG;

    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean state = true;

    @OneToMany(mappedBy = "moduleManagement", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RoleManagementModule> roleManagementModule;
}
