package med.voll.api.models.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank @Email String login,
        @NotBlank String password
) {
}
