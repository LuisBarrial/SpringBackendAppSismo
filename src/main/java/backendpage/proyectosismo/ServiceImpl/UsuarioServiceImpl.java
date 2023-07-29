package backendpage.proyectosismo.ServiceImpl;

import backendpage.proyectosismo.Model.DAO.RolDAO;
import backendpage.proyectosismo.Model.DAO.UsuarioDAO;
import backendpage.proyectosismo.Model.Rol;
import backendpage.proyectosismo.Model.Usuario;
import backendpage.proyectosismo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl extends ICommonServiceImpl<Usuario, UsuarioDAO> implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RolDAO rolDAO;

    @Override
    public UserDetails findByLogin(String username) {
        return usuarioDAO.findByLogin(username);

    }

    @Override
    @Transactional(readOnly = true)
    public Long findIdByLogin(String username) {
        return usuarioDAO.findIdByLogin(username);
    }

    @Override
    @Transactional
    public Usuario save(Usuario entity) {
        List<Rol> roles =rolDAO.findAll();
        entity.setClave(passwordEncoder.encode(entity.getClave()));

        Usuario user = new Usuario(entity.getLogin(),entity.getClave(),entity.getNumero(),entity.getNombre(),Arrays.asList(roles.get(1)));
        return this.repository.save(user);
    }

    @Override
    public UserDetails getUserAndAutorithies(String username){

        Usuario usuario = (Usuario) usuarioDAO.findByLogin(username);


        if (usuario==null){
            System.out.println("hay yn error aca");
            throw new UsernameNotFoundException("Usuario o Password Invalidos");
        }

    return new User(usuario.getNombre(),usuario.getClave(),mapearAutoridadesRoles(usuario.getListaRole()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(List<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).toList();
    }

}
