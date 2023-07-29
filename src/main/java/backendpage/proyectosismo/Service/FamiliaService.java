package backendpage.proyectosismo.Service;

import backendpage.proyectosismo.Model.Familia;

import java.util.List;
import java.util.Objects;

public interface FamiliaService extends ICommonService<Familia>{
    List<Object[]> obtenerData();
}
