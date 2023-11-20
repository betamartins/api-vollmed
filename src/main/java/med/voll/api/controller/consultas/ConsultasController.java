package med.voll.api.controller.consultas;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.models.consultas.dto.DadosAgendamentoConsulta;
import med.voll.api.models.consultas.dto.DadosCancelamentoConsulta;
import med.voll.api.models.consultas.dto.DadosDetalhamentoRetornoConsulta;
import med.voll.api.models.consultas.dto.DadosRetornoCancelamentoConsulta;
import med.voll.api.service.consultas.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultasController {

    @Autowired
    ConsultaService consultaService;

    @PostMapping(value = "/agendar")
    public ResponseEntity<?> agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta){

        DadosDetalhamentoRetornoConsulta retornoConsulta = consultaService.agendar(dadosAgendamentoConsulta);

        return ResponseEntity.ok(retornoConsulta);
    }

    @PostMapping(value = "/cancelar")
    public ResponseEntity<?> cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamentoConsulta){

        DadosRetornoCancelamentoConsulta dadosRetornoCancelamentoConsulta = consultaService.cancelar(dadosCancelamentoConsulta);

        return ResponseEntity.ok(dadosCancelamentoConsulta);
    }

}
