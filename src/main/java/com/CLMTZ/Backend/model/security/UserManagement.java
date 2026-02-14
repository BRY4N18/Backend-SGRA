package com.CLMTZ.Backend.model.security;

import java.util.List;

import com.CLMTZ.Backend.model.general.User;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbgestionusuarios", schema = "seguridad")
public class UserManagement {
    @Id
    @Column(name = "idgusuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer userGId;

    @Column(name = "usuario",length = 100, nullable = false)
    private String user;

    @Column(name = "contrasena", nullable = false, columnDefinition = "TEXT")
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", foreignKey = @ForeignKey(name = "fk_gestionusuario_usuario"))
    private User userId;

    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean state = true;

    @OneToMany(mappedBy = "userManagement", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserRoleManagement> userRoleManagements;
}
