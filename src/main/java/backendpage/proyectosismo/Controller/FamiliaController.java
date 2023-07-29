package backendpage.proyectosismo.Controller;


import backendpage.proyectosismo.Model.DTO.FamiliaDTO;
import backendpage.proyectosismo.Model.DTO.UsuarioDTOGet;
import backendpage.proyectosismo.Model.Familia;
import backendpage.proyectosismo.Model.Usuario;
import backendpage.proyectosismo.Service.AlmacenService;
import backendpage.proyectosismo.Service.FamiliaService;
import backendpage.proyectosismo.Service.UsuarioService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/familia",produces = MediaType.APPLICATION_JSON_VALUE)
public class FamiliaController extends CommonController<Familia, FamiliaService>{


    @Autowired
    private AlmacenService almacenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE}, headers = "X-Prioridad=alta")
    public ResponseEntity<FamiliaDTO> save(@RequestPart("image") MultipartFile image, @RequestPart("data")  Familia body){


        Familia familia = body;
        String nombreUsuario = familia.getUsuario().getNombre();
        String rutaImg = almacenService.almacenarArchivo(image,body.getNombre());
        Long id = usuarioService.findIdByLogin(nombreUsuario);
        Optional<Usuario> usuario = usuarioService.findById(id);
        if(usuario.isEmpty()){
            throw new RuntimeException("El usuario esta vacio");
        }
        familia.setUsuario(usuario.get());
        familia.setImageUrl(rutaImg);
        this.service.save(familia);

        FamiliaDTO familiaDTO = new FamiliaDTO(familia);

        return ResponseEntity.ok().body(familiaDTO);
    }


    @Override
    @GetMapping("list")
    public ResponseEntity<FamiliaDTO> findAll(){
        List<Familia> familias = this.service.findAll();
        List<FamiliaDTO> familiaDTOS = familias.stream().map(FamiliaDTO::new).collect(Collectors.toList());

        return new ResponseEntity(familiaDTOS, HttpStatus.OK);
    }

    @Override
    @GetMapping("pageable")
    public ResponseEntity<Page> findAllPageable(Pageable pageable){
        Page<Familia> familias =this.service.page(pageable);
        Page <FamiliaDTO> familiaDTOS = familias.map(FamiliaDTO::new);

        return new ResponseEntity(familiaDTOS, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        Optional<Familia> oEntity = this.service.findById(id);
        if(oEntity.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no encontrado");
        this.service.deleteById(id);
        this.almacenService.eliminarArchivo(oEntity.get().getImageUrl());
        return ResponseEntity.status(HttpStatus.OK).body(oEntity);
    }


    @GetMapping("admin/datastadistics")
    public ResponseEntity<List<Object[]>> getData(){
        List<Object[]> oEntity = this.service.obtenerData();
        return ResponseEntity.status(HttpStatus.OK).body(oEntity);
    }


}
