package med.voll.api.service.consultas.validators.cancelamento;

import med.voll.api.models.consultas.Consulta;
import med.voll.api.repository.consultas.ConsultasRepository;
import med.voll.api.utils.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ValidaAntecedenciaCancelamentoConsulta implements ValidatorCancelamentoConsulta{

    @Autowired
    private ConsultasRepository consultasRepository;

    @Override
    public void validar(Consulta consulta) {

        LocalDateTime dateNow = LocalDateTime.now();
        Long diferenca = ChronoUnit.DAYS.between(dateNow, consulta.getDataConsulta());
        if(diferenca < 1){
            throw new ValidacaoException("Uma consulta só pode ser cancelada até 24Hrs antes da mesma.");
        }


    }
}
