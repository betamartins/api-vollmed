package med.voll.api.models.consultas.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.models.consultas.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoRetornoConsulta(
        Long consultaId,
        Long medicoId,
        String nomeMedico,
        Long pacienteId,
        String nomePaciente,
        LocalDateTime dataConsulta
    ) {

    public DadosDetalhamentoRetornoConsulta(Consulta consulta){
        this(
            consulta.getId(),
            consulta.getMedico().getId(),
            consulta.getMedico().getNome(),
            consulta.getPaciente().getId(),
            consulta.getPaciente().getNome(),
            consulta.getDataConsulta()
        );
    }

}
