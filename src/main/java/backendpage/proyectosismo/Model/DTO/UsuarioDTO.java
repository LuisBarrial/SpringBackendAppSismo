package backendpage.proyectosismo.Model.DTO;

import backendpage.proyectosismo.Model.Usuario;

public record UsuarioDTO(String login, String nombre) {
    public UsuarioDTO(Usuario usuario){
        this(usuario.getLogin(), usuario.getUsername());
    }
}
