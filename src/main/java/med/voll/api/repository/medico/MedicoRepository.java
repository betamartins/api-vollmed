package med.voll.api.repository.medico;

import med.voll.api.models.medico.Especialidade;
import med.voll.api.models.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select m from Medico m
            where
            m.ativo = true
            and m.id not in (
                select c.medico.id from Consulta c where c.dataConsulta = :dataConsulta
            )
            and m.especialidade = :especialidade
            order by rand()
            limit 1
            """)
    Medico getMedicoAleatorioPorEspecialidade(Especialidade especialidade, LocalDateTime dataConsulta);

    @Query("select m.ativo from Medico m where m.id = :id")
    Boolean checkMedicoAtivo(Long id);

}
