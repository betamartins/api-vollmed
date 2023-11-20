package med.voll.api.models.consultas;

public enum MotivoCancelamentoConsulta {

    PACIENTE_DESISTIU(0, "Paciente desistiu da consulta"),
    MEDICO_CANCELOU(1, "Médico cancelou a consulta"),
    OUTRAS(2,"Outros motivos");

    private Integer id;
    private String descricaoMotivoCancelamento;

    MotivoCancelamentoConsulta(Integer id, String descricaoMotivoCancelamento){
        this.id = id;
        this.descricaoMotivoCancelamento = descricaoMotivoCancelamento;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricaoMotivoCancelamento() {
        return descricaoMotivoCancelamento;
    }
}
