package backendpage.proyectosismo.Security;

import backendpage.proyectosismo.Security.Filter.FilterAutenticathion;
import backendpage.proyectosismo.Security.Filter.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private FilterAutenticathion filterAutenticathion;

   @Autowired
   private JWTInterceptor jwtInterceptor;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        return http.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
                .sessionManagement(mg-> mg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout->logout.disable()) // Deshabilitar el manejo predeterminado de logout
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST,"/usuario","/login").permitAll()
                                .requestMatchers(HttpMethod.GET,"/login","/assets/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterAfter(jwtInterceptor, UsernamePasswordAuthenticationFilter.class) // Agregar jwtInterceptor después de cualquier filtro de autenticación
                .addFilterBefore(filterAutenticathion,UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
