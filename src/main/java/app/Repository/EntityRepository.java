package app.Repository;

import java.util.List;

public interface EntityRepository<E extends Identifiable> {
    E save(E entity);
    E delete(E entity);
    E findById(Long id);
    List<E> findAll();
    List<E> findByQuery(String query, Object... parameters);
}
