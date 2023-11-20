package med.voll.api.service.consultas.validators.agendamento;

import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.consultas.ConsultasRepository;
import med.voll.api.utils.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoPossuiConsultaJaAgendadoNaDataHoraSelecionada implements ValidatorAgendamentoConsulta {

    @Autowired
    private ConsultasRepository consultasRepository;

    public void validator(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        if(dadosAgendamentoConsulta.medicoId() == null){
            return;
        }
        if(consultasRepository.existsByMedicoIdAndStatusConsultaTrueAndDataConsulta(dadosAgendamentoConsulta.medicoId(), dadosAgendamentoConsulta.dataConsulta())){
            throw new ValidacaoException("Médico selecionado já possui uma consulta agendada neste horário.");
        }

    }

}
