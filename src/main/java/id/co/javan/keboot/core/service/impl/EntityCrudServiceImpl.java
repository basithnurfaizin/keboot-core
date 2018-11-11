package id.co.javan.keboot.core.service.impl;

import id.co.javan.keboot.core.form.EntityCrudForm;
import id.co.javan.keboot.core.repository.EntityCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * created by idoej
 * This class used as a shortcut for many service that has generic list, get, save, update and delete methods
 *
 * @param <T> entity class
 * @param <ID> entity's id
 * @param <FORM> entity's crud form
 */
public abstract class EntityCrudServiceImpl<T, ID, FORM extends EntityCrudForm<T>, R extends EntityCrudRepository<T, ID>> {

    protected R repository;

    public Page<T> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<T> search(Pageable pageable, Object... filters) {
        return repository.findAll(getSearchFilter(filters), pageable);
    }

    public T get(ID id) {
        return repository.findById(id).get();
    }

    public T create(FORM form) {
        T t = form.toCreateEntity();
        t = repository.save(t);
        return t;
    }

    public T update(ID id, FORM form) {
        T t = get(id);
        form.toUpdateEntity(t);
        repository.save(t);
        return t;
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public abstract Specification<T> getSearchFilter(Object... filters);
}
