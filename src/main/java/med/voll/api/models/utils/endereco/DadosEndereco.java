package med.voll.api.models.utils.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
    @NotBlank String logradouro, 
    String numero, 
    String complemento, 
    @NotBlank String cidade, 
    @NotBlank String bairro, 
    @NotBlank String uf, 
    @NotBlank @Pattern(regexp = "\\d{8}") String cep
) {
}
