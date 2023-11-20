package med.voll.api.models.consultas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.models.consultas.dto.DadosCancelamentoConsulta;
import med.voll.api.models.medico.Medico;
import med.voll.api.models.pacientes.Paciente;

import java.time.LocalDateTime;

@Entity(name = "Consulta")
@Table(name = "consultas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICO_ID")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;

    @Column(name = "DATA_CONSULTA")
    private LocalDateTime dataConsulta;

    @Column(name = "STATUS_CONSULTA")
    private Boolean statusConsulta;

    @Column(name = "MOTIVO_CANCELAMENTO")
    private String motivoCancelamento;

    public Consulta(Medico medico, Paciente paciente, LocalDateTime dataConsulta){
        this.medico = medico;
        this.paciente = paciente;
        this.dataConsulta = dataConsulta;
        this.statusConsulta = true;
    }

    public void cancelarConsulta(DadosCancelamentoConsulta dadosCancelamentoConsulta){
        this.statusConsulta = false;
        this.motivoCancelamento = dadosCancelamentoConsulta.motivoCancelamento().getDescricaoMotivoCancelamento();
    }

}
