package backendpage.proyectosismo.Model.DAO;

import backendpage.proyectosismo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario,Long> {

    UserDetails findByLogin(String username);


    @Query(value = "Select id from Usuario where login = :username",nativeQuery = true)
    Long findIdByLogin(@Param("username") String username);

}
