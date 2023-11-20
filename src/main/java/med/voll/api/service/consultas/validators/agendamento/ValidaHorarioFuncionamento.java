package med.voll.api.service.consultas.validators.agendamento;

import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.utils.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidaHorarioFuncionamento implements ValidatorAgendamentoConsulta {

    public void validator(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        LocalDateTime dataConsulta = dadosAgendamentoConsulta.dataConsulta();

        boolean checkSunday = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean checkForaHorarioFuncionamento = dataConsulta.getHour() < 7 || dataConsulta.getHour() > 18;

        if(checkSunday || checkForaHorarioFuncionamento){
            throw new ValidacaoException("Hora enviada para a consulta esta fora do horário de funcionamento.");
        }
    }
}
