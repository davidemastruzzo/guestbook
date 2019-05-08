package ch.bbw.guestbook.domain;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.SEQUENCE;


/**
 * Basic Entity. Provides common methods for other entities.
 */
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Persistable<Long> {

  /**
   * Creates the Field 'deleted' on the database.
   */
  @Column(name = "deleted", nullable = false)
  @ColumnDefault("false")
  private boolean deleted = false;

  /**
   * Marks entity as deleted but does not delete it.
   */
  public void markEntityDeleted() {
    deleted = true;
  }

  /**
   * Represents the identifier (id) of every entity.
   */

  @Id
  @SequenceGenerator(name = "GLOBAL_SEQ", sequenceName = "GLOBAL_SEQ", initialValue = 1000)
  @GeneratedValue(strategy = SEQUENCE, generator = "GLOBAL_SEQ")
  @Nullable
  private Long id;

  /**
   * Sets the id of the entity.
   *
   * @param id the id to set
   */
  private void setId(@Nullable Long id) {
    this.id = id;
  }

  /**
   * Must be {@link Transient} in mealOrder to ensure that no JPA provider complains because of a missing setter.
   *
   * @see org.springframework.data.domain.Persistable#isNew()
   */
  @Transient // DATAJPA-622
  public boolean isNew() {
    return null == getId();
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (null == obj) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (!getClass().equals(ClassUtils.getUserClass(obj))) {
      return false;
    }

    BaseEntity that = (BaseEntity) obj;

    return null != this.getId() && this.getId().equals(that.getId());
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashCode = 17;

    hashCode += null == getId() ? 0 : getId().hashCode() * 31;

    return hashCode;
  }
}
