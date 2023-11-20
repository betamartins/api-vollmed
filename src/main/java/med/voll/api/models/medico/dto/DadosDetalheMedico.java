package med.voll.api.models.medico.dto;

import med.voll.api.models.medico.Especialidade;
import med.voll.api.models.medico.Medico;
import med.voll.api.models.utils.endereco.DadosEndereco;

public record DadosDetalheMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        DadosEndereco dadosEndereco
) {

    public DadosDetalheMedico(Medico medico){
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                new DadosEndereco(
                        medico.getEndereco().getLogradouro(),
                        medico.getEndereco().getNumero(),
                        medico.getEndereco().getComplemento(),
                        medico.getEndereco().getCidade(),
                        medico.getEndereco().getBairro(),
                        medico.getEndereco().getUf(),
                        medico.getEndereco().getCep()
                )
        );
    }

}
