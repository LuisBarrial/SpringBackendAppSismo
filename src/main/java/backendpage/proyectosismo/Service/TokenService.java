package backendpage.proyectosismo.Service;

import backendpage.proyectosismo.Model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String secret;

    public static String id;

    public String generarToken(Usuario usuario){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("phuyu")
                    .withSubject(usuario.getLogin())
                    .withClaim("id",usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
            id= String.valueOf(usuario.getId());
            return token;
        } catch (JWTCreationException exception){

            throw new RuntimeException();
            // Invalid Signing configuration / Couldn't convert Claims.
        }
    }


    public String getSubject(String token){

        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("phuyu")
                    .build();
                    // reusable verifier instance
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){

            System.out.println(exception.toString());

            // Invalid signature/claims
        }
        if(decodedJWT== null){
            throw new RuntimeException();
        }

        return decodedJWT.getSubject();

    }

    public String obtenerId(String token){
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("phuyu")
                    .build();
            // reusable verifier instance
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){

            System.out.println(exception.toString());

            // Invalid signature/claims
        }
        if(decodedJWT== null){
            throw new RuntimeException();
        }

        return decodedJWT.getClaim("id").asString();


    }

    public Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
