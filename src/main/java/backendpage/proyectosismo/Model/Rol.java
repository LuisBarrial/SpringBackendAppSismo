package backendpage.proyectosismo.Model;

import jakarta.persistence.*;

@Entity
@Table(name="rol")
public class Rol {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idRol;

    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    @Column(name="nombre",nullable = false, length=50,unique=true)
    private String nombre;

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
