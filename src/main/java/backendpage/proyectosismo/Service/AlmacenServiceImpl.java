package backendpage.proyectosismo.Service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AlmacenServiceImpl implements AlmacenService{


    @Value("${storage.location}")
    private String storageLocation;

    @Override
    @PostConstruct
    public void iniciarAlmacenDeArchivos() {
        try {
            Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar la ubicacion del almanacen");
        }
    }

    @Override
    public String almacenarArchivo(MultipartFile archivo, String nombreFamilia) {

        String filename = archivo.getOriginalFilename().trim();
        String nameBase = FilenameUtils.getBaseName(filename);
        String ext = FilenameUtils.getExtension(filename);

        String nombreArchivo =nameBase+nombreFamilia+'.'+ext;
        if(archivo.isEmpty()){
            throw new RuntimeException("No se puede almacenar un archivo vacio");
        }
        try {
            InputStream inputStream =archivo.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("El archivo esta corrupto"+e.getMessage());
        }
        return nombreArchivo;

    }

    @Override
    public Path cargarArchivo(String nombreArchivo) {
        return Paths.get(storageLocation).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) throws FileNotFoundException {
        try {
            Path archivo = cargarArchivo(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());


            if (recurso.exists() || recurso.isReadable()){
                return recurso;
            }
            else {
                throw new FileNotFoundException("No se pudo encontrar el archivo "+nombreArchivo);
            }

        }catch (MalformedURLException | FileNotFoundException exception){
            throw new FileNotFoundException("No se pudo encontrar el archivo "+nombreArchivo);
        }
    }

    @Override
    public void eliminarArchivo(String nombreArchivo) {
        Path archivo = cargarArchivo(nombreArchivo);

        try {
            FileSystemUtils.deleteRecursively(archivo);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
