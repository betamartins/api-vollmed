package med.voll.api.service.consultas.validators.agendamento;

import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.consultas.ConsultasRepository;
import med.voll.api.utils.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ValidaConsultaNoMesmoDia implements ValidatorAgendamentoConsulta {

    @Autowired
    private ConsultasRepository consultasRepository;

    public void validator(DadosAgendamentoConsulta dadosAgendamentoConsulta){

        LocalDateTime dataInicial = dadosAgendamentoConsulta.dataConsulta().withHour(7);
        LocalDateTime dataFinal = dadosAgendamentoConsulta.dataConsulta().withHour(18);

        if(consultasRepository.existsByPacienteIdAndStatusConsultaTrueAndDataConsultaBetween(dadosAgendamentoConsulta.pacienteId(), dataInicial, dataFinal)){
            throw new ValidacaoException("Paciente já possui consulta marcada para o dia selecionado.");
        }
    }
}
