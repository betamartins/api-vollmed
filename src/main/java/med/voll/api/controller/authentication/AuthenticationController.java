package med.voll.api.controller.authentication;

import jakarta.validation.Valid;
import med.voll.api.models.usuario.Usuario;
import med.voll.api.models.usuario.dto.DadosAutenticacao;
import med.voll.api.security.TokenJwtService;
import med.voll.api.security.dto.DadosTokenJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenJwtService tokenJwtService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid  DadosAutenticacao dados){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        Authentication auth = authenticationManager.authenticate(token);
        String tokenJwt = this.tokenJwtService.createToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJwt(tokenJwt));
    }

}
