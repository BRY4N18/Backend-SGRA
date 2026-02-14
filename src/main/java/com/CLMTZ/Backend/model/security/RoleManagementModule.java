package com.CLMTZ.Backend.model.security;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbgestionrolesmodulos", schema = "seguridad")
public class RoleManagementModule {
    @Id
    @Column(name = "idgrolmodulo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer roleModuleGId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idgrol", foreignKey = @ForeignKey(name = "fk_gestionrolmodulo_gestionrol"))
    private RoleManagement roleManagement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idgmodulo", foreignKey = @ForeignKey(name = "fk_gestionrolmodulo_gestionmodulo"))
    private ModuleManagement moduleManagement;

    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean state = true;
}
