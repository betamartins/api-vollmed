package med.voll.api.service.consultas.validators.agendamento;

import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.utils.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidaAntecedenciaAgendamento implements ValidatorAgendamentoConsulta {

    public void validator(DadosAgendamentoConsulta dadosAgendamentoConsulta){

        LocalDateTime dataConsulta = dadosAgendamentoConsulta.dataConsulta();
        LocalDateTime now = LocalDateTime.now();

        Long diferenca = Duration.between(now, dataConsulta).toMinutes();

        if(diferenca < 30){
            throw new ValidacaoException("A consulta deve ser agendada com uma antecedência maior que 30 minutos.");
        }

    }

}
