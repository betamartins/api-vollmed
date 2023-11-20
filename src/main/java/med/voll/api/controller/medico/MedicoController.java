package med.voll.api.controller.medico;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.models.medico.*;
import med.voll.api.models.medico.dto.DadosAtualizacaoMedico;
import med.voll.api.models.medico.dto.DadosCadastroMedico;
import med.voll.api.models.medico.dto.DadosDetalheMedico;
import med.voll.api.models.medico.dto.DadosListagemMedico;
import med.voll.api.repository.medico.MedicoRepository;
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
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    
    @Autowired
    private MedicoRepository repository;

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DadosDetalheMedico> cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        Medico medico = new Medico(dados);
        this.repository.save(medico);

        URI uri = uriBuilder.path("/medicos/findById/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalheMedico(medico));
    }

    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DadosListagemMedico>> findAll(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        Page<DadosListagemMedico> listMedicos = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(listMedicos);
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosDetalheMedico> findById(@PathVariable(name = "id") Long id){
        Medico medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheMedico(medico));
    }

    @PutMapping(value = "/atualizarMedico", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DadosDetalheMedico> atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dados){
        Medico medico = this.repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalheMedico(medico));
    }

    @DeleteMapping(value = "/deletarMedico/{id}")
    @Transactional
    public ResponseEntity<?> deletarMedico(@PathVariable(name = "id") Long id){
        Medico medico = this.repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }
    
}
