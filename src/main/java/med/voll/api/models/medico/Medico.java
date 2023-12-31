package med.voll.api.models.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.models.medico.dto.DadosAtualizacaoMedico;
import med.voll.api.models.medico.dto.DadosCadastroMedico;
import med.voll.api.models.utils.endereco.Endereco;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Medico {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;
    
    public Medico(DadosCadastroMedico dados){
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.telefone = dados.telefone();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados){
        this.nome = dados.nome() != null && !dados.nome().isBlank() ? dados.nome() : this.nome;
        this.telefone = dados.telefone() != null && !dados.telefone().isBlank() ? dados.telefone() : this.telefone;
        this.endereco = dados.endereco() != null ? this.endereco.atualizarInformacoes(dados.endereco()) : this.endereco;
    }

    public void excluir(){
        this.ativo = false;
    }
    
}
