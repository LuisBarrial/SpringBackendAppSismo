package backendpage.proyectosismo.Controller;

import backendpage.proyectosismo.Security.ScopedBeans;
import backendpage.proyectosismo.Service.BlackListingService;
import backendpage.proyectosismo.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Set;

@RequestMapping("/logout")
@RestController
public class LogoutController {


    @Autowired
    private BlackListingService blackListingService;

    @Autowired
    private ScopedBeans scopedBeans;

    @PostMapping
    public ResponseEntity<String> quitarAuth(){
        blackListingService.blackListServiceJwt(scopedBeans.getJwt());
        SecurityContextHolder.clearContext(); // Eliminar la información de autenticación
        return ResponseEntity.status(HttpStatus.OK).body("Desconectado");

    }

}
