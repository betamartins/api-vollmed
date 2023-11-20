package med.voll.api.service.consultas.validators.agendamento;

import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.paciente.PacienteRepository;
import med.voll.api.utils.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivo implements ValidatorAgendamentoConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validator(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        if(!pacienteRepository.checkPacienteAtivo(dadosAgendamentoConsulta.pacienteId())){
            throw new ValidacaoException("Agendamento permitido apenas para pacientes ativos no sistema.");
        }
    }

}
