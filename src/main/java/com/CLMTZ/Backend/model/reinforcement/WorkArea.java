package com.CLMTZ.Backend.model.reinforcement;

import java.util.List;

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
@Table(name = "tbareastrabajos", schema = "reforzamiento")
public class WorkArea {
    @Id
    @Column(name = "idareatrabajo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer workAreaId;

    @Column(name = "areatrabajo", length = 25, nullable = false)
    private String workArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipoareatrabajo", nullable = false, foreignKey = @ForeignKey(name = "fk_areatrabajo_tipo"))
    private WorkAreaTypes workAreaTypeId;

    @Column (name = "capacidad", nullable = false)
    private Integer capacity;

    @Column(name = "disponibilidad", nullable = false, columnDefinition = "char(1) default 'D' check(Disponibilidad in ('O','D'))")
    private Character availability = 'D';

    @OneToMany(mappedBy = "workAreaId", fetch = FetchType.LAZY)
    private List<OnSiteReinforcement> onSiteReinforcements;
}