package med.voll.api.models.pacientes.dto;

import med.voll.api.models.pacientes.Paciente;
import med.voll.api.models.utils.endereco.DadosEndereco;

public record DadosDetalhePaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco
) {
    public DadosDetalhePaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                new DadosEndereco(
                        paciente.getEndereco().getLogradouro(),
                        paciente.getEndereco().getNumero(),
                        paciente.getEndereco().getComplemento(),
                        paciente.getEndereco().getCidade(),
                        paciente.getEndereco().getBairro(),
                        paciente.getEndereco().getUf(),
                        paciente.getEndereco().getCep()
                )
        );
    }
}
