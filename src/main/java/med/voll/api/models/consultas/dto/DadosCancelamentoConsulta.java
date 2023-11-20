package med.voll.api.models.consultas.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.models.consultas.MotivoCancelamentoConsulta;

public record DadosCancelamentoConsulta(
        @NotNull Long idConsulta,
        @NotNull MotivoCancelamentoConsulta motivoCancelamento
) {
}
