package med.voll.api.controller.paciente;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.models.pacientes.Paciente;
import med.voll.api.models.pacientes.dto.DadosAtualizacaoPaciente;
import med.voll.api.models.pacientes.dto.DadosCadastroPaciente;
import med.voll.api.models.pacientes.dto.DadosDetalhePaciente;
import med.voll.api.models.pacientes.dto.DadosListagemPaciente;
import med.voll.api.repository.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping(value = "/cadastrarPaciente")
    @Transactional
    public ResponseEntity<DadosDetalhePaciente> cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder builder){
        Paciente paciente = new Paciente(dados);
        this.repository.save(paciente);
        URI uri = builder.path("/pacientes/findById/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhePaciente(paciente));

    }

    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DadosListagemPaciente>> findAll(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        Page<DadosListagemPaciente> listPacientes = this.repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(listPacientes);
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosDetalhePaciente> findById(@PathVariable(name = "id") Long id){
        DadosDetalhePaciente detalhePaciente = this.repository.findById(id).stream().map(DadosDetalhePaciente::new).toList().get(0);
        return ResponseEntity.ok(detalhePaciente);
    }

    @PutMapping(value = "/atualizarPaciente", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DadosDetalhePaciente> atualizarPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        Paciente paciente = this.repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhePaciente(paciente));
    }

    @DeleteMapping(value = "/deletarPaciente/{id}")
    @Transactional
    public ResponseEntity<?> deletarPaciente(@PathVariable(name = "id") Long id){
        Paciente paciente = this.repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }


}
