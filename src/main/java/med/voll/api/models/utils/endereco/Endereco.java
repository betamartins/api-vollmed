package med.voll.api.models.utils.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String bairro;
    private String uf;
    private String cep;
    
    public Endereco(DadosEndereco dados){
        this.logradouro = dados.logradouro();
        this.numero = dados.numero();
        this.complemento = dados.complemento();
        this.cidade = dados.cidade();
        this.bairro = dados.bairro();
        this.uf = dados.uf();
        this.cep = dados.cep();
    }

    public Endereco atualizarInformacoes(DadosEndereco dados) {
        this.logradouro = !dados.logradouro().isBlank() ? dados.logradouro() : this.logradouro;
        this.numero = !dados.numero().isBlank() ? dados.numero() : this.numero;
        this.complemento = !dados.complemento().isBlank() ? dados.complemento() : this.complemento;
        this.cidade = !dados.cidade().isBlank() ? dados.cidade() : this.cidade;
        this.bairro = !dados.bairro().isBlank() ? dados.bairro() : this.bairro;
        this.uf = !dados.uf().isBlank() ? dados.uf() : this.uf;
        this.cep = !dados.cep().isBlank() ? dados.cep() : this.cep;
        return this;
    }
}
