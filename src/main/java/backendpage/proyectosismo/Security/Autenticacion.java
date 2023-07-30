package backendpage.proyectosismo.Security;

import backendpage.proyectosismo.Model.Usuario;
import backendpage.proyectosismo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Autenticacion implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("hola desde el user");
        UserDetails usuario= usuarioService.findByLogin(username);
        System.out.println(usuario.getAuthorities());

        return usuario;
    }
}
