package med.voll.api.service.consultas.validators.agendamento;

import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.medico.MedicoRepository;
import med.voll.api.utils.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoAtivo implements ValidatorAgendamentoConsulta {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validator(DadosAgendamentoConsulta dadosAgendamentoConsulta){

        if(dadosAgendamentoConsulta.medicoId() == null){
            return;
        }

        if(!medicoRepository.checkMedicoAtivo(dadosAgendamentoConsulta.medicoId())){
            throw new ValidacaoException("Agendamento permitido apenas para médicos ativos no sistema.");
        }
    }
}
