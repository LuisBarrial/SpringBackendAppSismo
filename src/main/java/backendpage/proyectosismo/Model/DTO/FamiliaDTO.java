package backendpage.proyectosismo.Model.DTO;

import backendpage.proyectosismo.Model.Familia;

public record FamiliaDTO(Long id,String nombre, String numero,String croquisURL) {

    public FamiliaDTO(Familia familia){
        this(familia.getId(),familia.getNombre(),familia.getNumeroEmergencia(), familia.getImageUrl());
    }

}
