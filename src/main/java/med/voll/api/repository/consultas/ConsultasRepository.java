package med.voll.api.repository.consultas;

import med.voll.api.models.consultas.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultasRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByIdAndStatusConsultaTrue(Long idConsulta);

    Boolean existsByPacienteIdAndStatusConsultaTrueAndDataConsultaBetween(Long pacienteId, LocalDateTime dataInicio, LocalDateTime dataFinal);

    Boolean existsByMedicoIdAndStatusConsultaTrueAndDataConsulta(Long medicoId, LocalDateTime dataConsulta);

}
