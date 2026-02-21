package com.CLMTZ.Backend.model.academic;

import java.util.List;

import com.CLMTZ.Backend.model.reinforcement.ReinforcementRequest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tbtemarios", schema = "academico")
@Entity
public class Syllabi {
    @Id
    @Column(name = "idtemario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer syllabiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idasignatura", foreignKey = @ForeignKey(name = "fk_temario_asignatura"))
    private Subject subjectId;

    @Column(name = "nombretemario", nullable = false)
    private String nameSyllabi;

    @Column(name = "unidad", nullable = false, columnDefinition = "smallint")
    private Integer unit;

    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean state = true;

    @OneToMany(mappedBy = "topicId", fetch = FetchType.LAZY)
    private List<ReinforcementRequest> reinforcementRequests;

    public void setCarrera(String cellValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCarrera'");
    }

    public void setModalidad(String cellValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setModalidad'");
    }

    public void setAsignatura(String cellValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAsignatura'");
    }

    public void setPeriodo(String cellValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPeriodo'");
    }

    public void setParalelo(String cellValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setParalelo'");
    }
}
