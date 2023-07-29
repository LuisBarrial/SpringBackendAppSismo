package backendpage.proyectosismo.Controller;

import backendpage.proyectosismo.Service.AlmacenService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    @Autowired
    private AlmacenService almacenService;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> obtenerRecurso(@PathVariable("filename") String filename, HttpServletRequest request) throws FileNotFoundException {
        Resource recurso = almacenService.cargarComoRecurso(filename);

        // Determinar el tipo de contenido de la imagen
        String contentType = null;
        ServletContext servletContext = request.getServletContext();

        try {
            contentType = servletContext.getMimeType(recurso.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // Manejar excepción
        }

        // Si no se pudo determinar el tipo de contenido, usar por defecto
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        // Devolver la imagen con el tipo de contenido correcto
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(recurso);
    }

}
