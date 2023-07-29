package backendpage.proyectosismo.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface AlmacenService {

        public void iniciarAlmacenDeArchivos();

        public String almacenarArchivo(MultipartFile archivo, String nombreFamilia);

        public Path cargarArchivo(String nombreArchivo);

        public Resource cargarComoRecurso(String nombreArchivo) throws FileNotFoundException;

        public void eliminarArchivo(String nombreArchivo);
}
