package backendpage.proyectosismo.Service;

import backendpage.proyectosismo.Model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService extends ICommonService<Usuario>{
    UserDetails findByLogin(String username);

    Long findIdByLogin(String username);

    public UserDetails getUserAndAutorithies(String username);
}
