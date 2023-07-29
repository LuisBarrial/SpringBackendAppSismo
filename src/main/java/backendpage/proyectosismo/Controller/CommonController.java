package backendpage.proyectosismo.Controller;

import backendpage.proyectosismo.Service.ICommonService;
import backendpage.proyectosismo.ServiceImpl.ICommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

public class CommonController<E, S extends ICommonService<E>> {

    @Autowired
    protected S service;

    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        Iterable<E> iterable = this.service.findAll();

        return new ResponseEntity<Iterable>(iterable, HttpStatus.OK);
    }

    @GetMapping("pageable")
    public ResponseEntity<?> findAllPageable(Pageable pageable){
       Collection collection= this.service.page(pageable).stream().toList();
        return new ResponseEntity<Collection>(collection, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody E body){
        E e = this.service.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        Optional<E> oEntity = this.service.findById(id);
        if(oEntity.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(oEntity);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        Optional<E> oEntity = this.service.findById(id);

        if(oEntity.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");

        return ResponseEntity.status(HttpStatus.OK).body(oEntity);
    }

    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody E entity){

        E entidad = this.service.update(entity);

        return ResponseEntity.status(HttpStatus.OK).body(entidad);
    }



}
