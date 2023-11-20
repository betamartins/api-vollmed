package med.voll.api.repository.medico;

import med.voll.api.models.consultas.Consulta;
import med.voll.api.models.medico.Especialidade;
import med.voll.api.models.medico.Medico;
import med.voll.api.models.medico.dto.DadosCadastroMedico;
import med.voll.api.models.pacientes.Paciente;
import med.voll.api.models.pacientes.dto.DadosCadastroPaciente;
import med.voll.api.models.utils.endereco.DadosEndereco;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Testa medico que já possui consulta agendada. Deve retornar null")
    void getMedicoAleatorioPorEspecialidadeCenario1() {

        LocalDateTime data = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        var medico = this.cadastraMedico("Medico", "medico@teste.com", "1112341234", "123456", Especialidade.CARDIOLOGIA);
        var paciente = this.cadastraPaciente("Paciente", "paciente@teste.com", "1112341234", "12345678901");
        agendarConsulta(medico, paciente, data);

        var medicoLivre = medicoRepository.getMedicoAleatorioPorEspecialidade(Especialidade.CARDIOLOGIA, data);

        Assertions.assertThat(medicoLivre).isNull();

    }

    @Test
    @DisplayName("Testa medico que não possui consulta agendada. Deve retornar o medico cadastrado")
    void getMedicoAleatorioPorEspecialidadeCenario2() {

        LocalDateTime data = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        var medico = this.cadastraMedico("Medico", "medico@teste.com", "1112341234", "123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = medicoRepository.getMedicoAleatorioPorEspecialidade(Especialidade.CARDIOLOGIA, data);

        Assertions.assertThat(medicoLivre).isEqualTo(medico);

    }

    private Consulta agendarConsulta(Medico medico, Paciente paciente, LocalDateTime dataConsulta){
        Consulta consulta = new Consulta(medico, paciente, dataConsulta);
        em.persist(consulta);
        return consulta;
    }

    private Medico cadastraMedico(String nome, String email, String telefone, String crm, Especialidade especialidade){
        Medico medico = new Medico(dadosCadastroMedico(nome, email, telefone, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastraPaciente(String nome, String email, String telefone, String cpf){
        Paciente paciente = new Paciente(dadosCadastroPaciente(nome, email, telefone, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosCadastroMedico(String nome, String email, String telefone, String crm, Especialidade especialidade){
        return new DadosCadastroMedico(nome, email, telefone, crm, especialidade, dadosEndereco());
    }

    private DadosCadastroPaciente dadosCadastroPaciente(String nome, String email, String telefone, String cpf){
        return new DadosCadastroPaciente(nome, email, telefone, cpf, dadosEndereco());
    }

    private DadosEndereco dadosEndereco(){
        return new DadosEndereco(
                "Rua XPTO",
                "10",
                null,
                "Bebedouro",
                "XPTO",
                "SP",
                "14700000");
    }

}