package backendpage.proyectosismo.Controller;


import backendpage.proyectosismo.Model.DTO.AutenticationUserDTO;
import backendpage.proyectosismo.Model.DTO.DatosJWTToken;
import backendpage.proyectosismo.Model.Usuario;
import backendpage.proyectosismo.Service.TokenService;
import backendpage.proyectosismo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<String> isconected(){
        String isConected = "Estas conectado sss";
        return ResponseEntity.status(HttpStatus.OK).body(isConected);
    }

    @PostMapping
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody AutenticationUserDTO autenticationUserDTO){

      //  UserDetails usuario =  usuarioService.getUserAndAutorithies(autenticationUserDTO.login());

        Authentication authToken = new UsernamePasswordAuthenticationToken(autenticationUserDTO.login()
                ,autenticationUserDTO.clave());

        System.out.println(authToken);
        //System.out.println(usuario);

        System.out.println("antes de authmmanager");


        var usuarioAutenticado =authenticationManager.authenticate(authToken);

        System.out.println("despues de authmmanager");
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(jwtToken));


    }

}
