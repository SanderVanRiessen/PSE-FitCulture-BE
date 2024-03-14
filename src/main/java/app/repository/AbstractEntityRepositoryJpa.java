package app.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractEntityRepositoryJpa<E extends Identifiable> implements EntityRepository<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractEntityRepositoryJpa() {
        this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public E save(E entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public E delete(E entity) {
        entityManager.remove(entity);
        return entity;
    }

    @Override
    public E findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<E> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    @Override
    public List<E> findByQuery(String query, Object... parameters) {
        return entityManager.createQuery(query, entityClass).getResultList();
    }
}
