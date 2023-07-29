package backendpage.proyectosismo.Controller;

import backendpage.proyectosismo.Model.DAO.UsuarioDAO;
import backendpage.proyectosismo.Model.DTO.FamiliaDTO;
import backendpage.proyectosismo.Model.DTO.UsuarioDTO;
import backendpage.proyectosismo.Model.DTO.UsuarioDTOGet;
import backendpage.proyectosismo.Model.Familia;
import backendpage.proyectosismo.Model.Usuario;
import backendpage.proyectosismo.Service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuario",produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController extends CommonController<Usuario, UsuarioService>{


    @Override
    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody Usuario body){
        Usuario e = this.service.save(body);
        UsuarioDTO bodyDTO = new UsuarioDTO(e);
        return ResponseEntity.ok(bodyDTO);
    }

    @Override
    @GetMapping("list")
    public ResponseEntity<List<UsuarioDTOGet>> findAll(){
       List<Usuario> usuario= this.service.findAll();
       List<UsuarioDTOGet> usuarioDTOS = usuario.stream().map(UsuarioDTOGet::new).collect(Collectors.toList());
       return ResponseEntity.ok(usuarioDTOS);
    }

    @Override
    @GetMapping("pageable")
    public ResponseEntity<Page> findAllPageable(Pageable pageable){
        Page<Usuario> usuario= this.service.page(pageable);
        Page<UsuarioDTOGet> usuarioDTOS = usuario.map(UsuarioDTOGet::new);
        return ResponseEntity.ok(usuarioDTOS);
    }

    @GetMapping
    public ResponseEntity<List<FamiliaDTO>> findByLogin(@Param("login") String login){

       Long id = this.service.findIdByLogin(login);
       Usuario usuario = this.service.findById(id).orElse(null);

       List<FamiliaDTO> familiaDTOS = usuario.getFamilias().stream().map(FamiliaDTO::new).collect(Collectors.toList());

       return ResponseEntity.status(HttpStatus.OK).body(familiaDTOS);
    }
    @GetMapping("/api/data")
    public ResponseEntity<UsuarioDTOGet> findByLoginUsuario(@Param("login") String login){

        Long id = this.service.findIdByLogin(login);
        Usuario usuario = this.service.findById(id).orElse(null);

        UsuarioDTOGet usuarioDTOGet = new UsuarioDTOGet(usuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTOGet);
    }
}
