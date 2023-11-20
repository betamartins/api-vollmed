package med.voll.api.models.pacientes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.models.pacientes.dto.DadosAtualizacaoPaciente;
import med.voll.api.models.pacientes.dto.DadosCadastroPaciente;
import med.voll.api.models.utils.endereco.Endereco;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;

    public Paciente(DadosCadastroPaciente dados){
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }


    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        this.nome = dados.nome() != null && !dados.nome().isBlank() ? dados.nome() : this.nome;
        this.telefone = dados.telefone() != null && !dados.telefone().isBlank() ? dados.telefone() : this.telefone;
        this.endereco = dados.endereco() != null ? this.endereco.atualizarInformacoes(dados.endereco()) : this.endereco;
    }

    public void excluir(){
        this.ativo = false;
    }
}
