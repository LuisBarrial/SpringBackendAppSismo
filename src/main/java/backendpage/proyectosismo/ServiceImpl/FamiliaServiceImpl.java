package backendpage.proyectosismo.ServiceImpl;

import backendpage.proyectosismo.Model.DAO.FamiliaDAO;
import backendpage.proyectosismo.Model.Familia;
import backendpage.proyectosismo.Service.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FamiliaServiceImpl extends ICommonServiceImpl<Familia, FamiliaDAO> implements FamiliaService {

    @Autowired
    private FamiliaDAO familiaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenerData() {
       return familiaDAO.findDataStadistics();
    }
}
