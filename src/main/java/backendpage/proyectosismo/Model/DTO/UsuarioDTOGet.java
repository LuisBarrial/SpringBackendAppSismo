package backendpage.proyectosismo.Model.DTO;

import backendpage.proyectosismo.Model.Rol;
import backendpage.proyectosismo.Model.Usuario;

import java.util.List;

public record UsuarioDTOGet(Long id, String login, String nombre, String numero, List<Rol> roles) {

    public UsuarioDTOGet(Usuario usuario){
        this(usuario.getId(),usuario.getLogin(), usuario.getNombre(),usuario.getNumero(),usuario.getListaRole()
        );
    }
}
