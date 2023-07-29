package backendpage.proyectosismo.Model.DAO;

import backendpage.proyectosismo.Model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliaDAO extends JpaRepository<Familia,Long> {

    @Query(value = "SELECT nombre, COUNT(*) AS cantidad FROM Familia GROUP BY nombre;",nativeQuery = true)
    List<Object[]> findDataStadistics();


}
