package backendpage.proyectosismo.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICommonService<E> {

    public List<E> findAll();

    public Optional<E> findById(Long id);

    public E save(E entity);

    public void deleteById(Long id);

    public E update(E entity);

    public Page<E> page(Pageable pageable);


}
