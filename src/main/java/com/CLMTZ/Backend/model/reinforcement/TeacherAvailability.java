package com.CLMTZ.Backend.model.reinforcement;

import com.CLMTZ.Backend.model.academic.Period;
import com.CLMTZ.Backend.model.academic.Teaching;
import com.CLMTZ.Backend.model.academic.TimeSlot;

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
@Table(name = "tbdisponibilidaddocente", schema = "reforzamiento")
public class TeacherAvailability {
    @Id
    @Column(name = "iddisponibilidaddocente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer teacherAvailabilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddocente", foreignKey = @ForeignKey(name = "fk_disponibilidad_docente"))
    private Teaching teacherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfranjahorario", foreignKey = @ForeignKey(name = "fk_disponibilidad_franja"))
    private TimeSlot timeSlotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idperiodo", foreignKey = @ForeignKey(name = "fk_disponibilidad_periodo"))
    private Period periodId;

    @Column(name = "diasemana", nullable = false, columnDefinition = "smallint")
    private Short dayOfWeek;

    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean state = true;
}