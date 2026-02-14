package com.CLMTZ.Backend.model.security;

import java.util.List;

import com.CLMTZ.Backend.model.reinforcement.ReinforcementRequest;
import com.CLMTZ.Backend.model.reinforcement.ScheduledReinforcement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbtipossesiones", schema = "seguridad")
public class SessionTypes {
    @Id
    @Column(name = "idtiposesion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer sesionTypesId;

    @Column(name = "tiposesion",length = 12,nullable = false, unique = true)
    private String sesionType;

    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean state = true;

    @OneToMany(mappedBy = "sessionTypeId", fetch = FetchType.LAZY)
    private List<ReinforcementRequest> reinforcementRequests;

    @OneToMany(mappedBy = "sessionTypeId", fetch = FetchType.LAZY)
    private List<ScheduledReinforcement> scheduledReinforcements;
}
