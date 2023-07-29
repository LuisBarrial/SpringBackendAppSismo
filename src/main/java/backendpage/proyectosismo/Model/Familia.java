package backendpage.proyectosismo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Entity
@Table(name = "familia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Familia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String imageUrl;

    private String nombre;

    private String numeroEmergencia;

    @Transient
    @JsonIgnore
    private MultipartFile img;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;








}
