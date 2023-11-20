package med.voll.api.models.consultas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.models.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
    Long medicoId,
    @NotNull Long pacienteId,
    @NotNull @Future LocalDateTime dataConsulta,
    Especialidade especialidade
) {



}
