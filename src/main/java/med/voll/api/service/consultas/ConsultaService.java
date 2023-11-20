package med.voll.api.service.consultas;

import med.voll.api.models.consultas.Consulta;
import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.models.consultas.dto.DadosCancelamentoConsulta;
import med.voll.api.models.consultas.dto.DadosDetalhamentoRetornoConsulta;
import med.voll.api.models.consultas.dto.DadosRetornoCancelamentoConsulta;
import med.voll.api.models.medico.Medico;
import med.voll.api.models.pacientes.Paciente;
import med.voll.api.repository.consultas.ConsultasRepository;
import med.voll.api.repository.medico.MedicoRepository;
import med.voll.api.repository.paciente.PacienteRepository;
import med.voll.api.service.consultas.validators.agendamento.ValidatorAgendamentoConsulta;
import med.voll.api.service.consultas.validators.cancelamento.ValidatorCancelamentoConsulta;
import med.voll.api.utils.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidatorAgendamentoConsulta> agendamentoConsultasValidators;

    @Autowired
    private List<ValidatorCancelamentoConsulta> cancelamentoConsultasValidators;

    @Transactional
    public DadosDetalhamentoRetornoConsulta agendar(DadosAgendamentoConsulta dadosAgendamento){

        this.checkMedicoExist(dadosAgendamento.medicoId());
        this.checkPacienteExist(dadosAgendamento.pacienteId());

        this.agendamentoConsultasValidators.forEach((x) -> x.validator(dadosAgendamento));

        Medico medico = obterMedicoConsulta(dadosAgendamento);
        Paciente paciente = pacienteRepository.findById(dadosAgendamento.pacienteId()).get();
        Consulta consulta = new Consulta(medico, paciente, dadosAgendamento.dataConsulta());
        consultasRepository.save(consulta);
        return new DadosDetalhamentoRetornoConsulta(
                consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getMedico().getNome(),
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome(),
                consulta.getDataConsulta()
        );
    }

    @Transactional
    public DadosRetornoCancelamentoConsulta cancelar(DadosCancelamentoConsulta dadosCancelamentoConsulta){
        if (consultasRepository.existsByIdAndStatusConsultaTrue(dadosCancelamentoConsulta.idConsulta())){
            Consulta consulta = consultasRepository.findById(dadosCancelamentoConsulta.idConsulta()).get();

            this.cancelamentoConsultasValidators.forEach((x) -> x.validar(consulta));
            consulta.cancelarConsulta(dadosCancelamentoConsulta);
        } else {
            throw new ValidacaoException("Consulta não existe no sistema, por favor verifique.");
        }
        return new DadosRetornoCancelamentoConsulta(dadosCancelamentoConsulta.idConsulta(), dadosCancelamentoConsulta.motivoCancelamento().getDescricaoMotivoCancelamento());
    }

    private Medico obterMedicoConsulta(DadosAgendamentoConsulta dados) {
        if(dados.medicoId() != null){
            return medicoRepository.getReferenceById(dados.medicoId());
        }
        if(dados.especialidade() == null){
            throw new ValidacaoException("A especialidade deve ser preenchida quando o médico não foi selecionado.");
        }

        Medico medico = medicoRepository.getMedicoAleatorioPorEspecialidade(dados.especialidade(), dados.dataConsulta());

        if(medico == null){
            throw new ValidacaoException("Não existe médicos disponiveis para a data e hora selecionada.");
        }

        return medico;

    }

    private void checkMedicoExist(Long idMedico){
        if(idMedico != null && !medicoRepository.existsById(idMedico)){
            throw new ValidacaoException("Médico passado não existe no sistema.");
        }
    }

    private void checkPacienteExist(Long idPaciente){
        if(!pacienteRepository.existsById(idPaciente)){
            throw new ValidacaoException("Paciente passado não existe no sistema");
        }
    }



}
