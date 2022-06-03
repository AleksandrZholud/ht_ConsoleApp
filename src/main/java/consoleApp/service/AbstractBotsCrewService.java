package consoleApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBotsCrewService<E, Z, T extends JpaRepository<E, Z>> {

    @Autowired
    protected T repository;

    public Optional<E> findById(Z id) {
        return repository.findById(id);
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public E save(E entity) {
        entity = repository.save(entity);
        return entity;
    }

    public void delete(E entity) {
        repository.delete(entity);
    }

    public List<E> saveAll(Collection<E> list) {
        return repository.saveAll(list);
    }

    public void deleteAll(Iterable<E> massDeletionIterable) {
        this.repository.deleteAll(massDeletionIterable);
    }

    public void deleteById(Z id) {
        this.repository.deleteById(id);
    }

    public E getOne(Z id) {
        return repository.getOne(id);
    }
}