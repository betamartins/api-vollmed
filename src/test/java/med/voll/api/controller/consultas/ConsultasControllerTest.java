package med.voll.api.controller.consultas;

import med.voll.api.models.consultas.MotivoCancelamentoConsulta;
import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.models.consultas.dto.DadosCancelamentoConsulta;
import med.voll.api.models.consultas.dto.DadosDetalhamentoRetornoConsulta;
import med.voll.api.models.consultas.dto.DadosRetornoCancelamentoConsulta;
import med.voll.api.models.medico.Especialidade;
import med.voll.api.service.consultas.ConsultaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoRetornoConsulta> dadosDetalhamentoRetornoConsultaJson;

    @Autowired
    private JacksonTester<DadosCancelamentoConsulta> dadosCancelamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosRetornoCancelamentoConsulta> dadosRetornoCancelamentoConsultaJson;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Teste agendamento passando dados inválidos, deve retornar erro 400")
    @WithMockUser
    void agendarConsultaCenario1() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/consultas/agendar"))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Teste agendamento passando dados válidos, deve retornar erro 200")
    @WithMockUser
    void agendarConsultaCenario2() throws Exception {

        var data = LocalDateTime.now().plusHours(2);
        var dadosDetalhamentoRetorno = new DadosDetalhamentoRetornoConsulta(
                null,
                1L,
                "Medico",
                1L,
                "Paciente",
                data
        );

        Mockito.when(consultaService.agendar(Mockito.any())).thenReturn(dadosDetalhamentoRetorno);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/consultas/agendar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    dadosAgendamentoConsultaJson.write(new DadosAgendamentoConsulta(1L, 1L, data, Especialidade.CARDIOLOGIA)).getJson()
                )
            )
            .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonResponseEsperado = dadosDetalhamentoRetornoConsultaJson.write(dadosDetalhamentoRetorno).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonResponseEsperado);

    }

    @Test
    @DisplayName("Teste cancelamento passando dados inválidos, deve retornar erro 400")
    @WithMockUser
    void cancelarConsultaCenario1() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/consultas/cancelar"))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Teste agendamento passando dados válidos, deve retornar erro 200")
    @WithMockUser
    void cancelarConsultaCenario2() throws Exception {

        var dadosRetornoCancelamento = new DadosRetornoCancelamentoConsulta(
                1L,
                MotivoCancelamentoConsulta.PACIENTE_DESISTIU.name()
        );

        Mockito.when(consultaService.cancelar(Mockito.any())).thenReturn(dadosRetornoCancelamento);

        var response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/consultas/cancelar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        dadosCancelamentoConsultaJson.write(new DadosCancelamentoConsulta(1L, MotivoCancelamentoConsulta.PACIENTE_DESISTIU)).getJson()
                                )
                        )
                        .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonResponseEsperado = dadosRetornoCancelamentoConsultaJson.write(dadosRetornoCancelamento).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonResponseEsperado);

    }

}