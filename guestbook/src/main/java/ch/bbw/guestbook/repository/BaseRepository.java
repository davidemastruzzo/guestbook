package ch.bbw.guestbook.repository;

import ch.bbw.guestbook.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Is the Default repository.
 *
 * @param <T> is the needed Object that has to extend a BaseEntity
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

  /**
   * Overrides the delete function.
   *
   * @param id is used for getting the right object from the DB,
   *           mark it as deleted and save it again.
   */
  default void delete(final Long id) {
    final T t = findOne(id);
    if (t != null) {
      t.markEntityDeleted();
      save(t);
    }
  }

  /**
   * Overrides the delete function.
   *
   * @param entity is used for getting the right object from the DB,
   *               mark it as deleted and save it again.
   */
  @Override
  default void delete(final T entity) {
    entity.markEntityDeleted();
    save(entity);
  }

  /**
   * Overrides the delete function.
   *
   * @param entities is a list of Entities which have to be deleted.
   */
  default void delete(final Iterable<? extends T> entities) {
    entities.forEach(entity -> {
      entity.markEntityDeleted();
      save(entity);
    });
  }

  /**
   * Overrides the method findOne. So that it will only return undeleted objects.
   *
   * @param id is needed to find the Item in the DB.
   * @return the Entity.
   */
  default T findOne(final Long id) {
    T obj = findOneByIdAndDeletedFalse(id);
    if (obj == null) {
      return null;
    } else {
      return obj.isDeleted() ? null : obj;
    }
  }

  /**
   * Returns all items which are not deleted.
   *
   * @return every item which is not deleted
   */
  List<T> findAllByDeletedFalse();

  /**
   * Returns all items which are not deleted.
   *
   * @return every item which is not deleted
   */
  @Deprecated
  List<T> findAll();
//  @Deprecated
//  default List<T> findAll()  {
//    return findAllByDeletedFalse();
//  }

  /**
   * Find an entity by its id but only if the object is not marked as deleted.
   *
   * @param id Id of the entitry to be loaded
   * @return Object or null if no such entity was found
   */
  T findOneByIdAndDeletedFalse(Long id);

  /**
   * Overrides the exists function so that it also checks if the entity is deleted.
   *
   * @param id to identify the object.
   * @return if it exists.
   */
  default boolean exists(final Long id) {
    T obj = findOne(id);
    return obj != null;
  }
}
