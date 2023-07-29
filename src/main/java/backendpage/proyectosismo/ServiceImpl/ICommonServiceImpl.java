package backendpage.proyectosismo.ServiceImpl;

import backendpage.proyectosismo.Service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ICommonServiceImpl<E, R extends JpaRepository<E,Long>> implements ICommonService<E> {

    @Autowired
    protected R repository;

    @Override
    @Transactional
    public List<E> findAll() {
       return this.repository.findAll();
    }

    @Override
    @Transactional
    public Optional<E> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        return this.repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public E update(E entity) {
        return this.repository.save(entity);
    }

    @Override
    @Transactional
    public Page<E> page(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
