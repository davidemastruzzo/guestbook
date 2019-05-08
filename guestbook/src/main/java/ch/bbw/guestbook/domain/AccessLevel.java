package ch.bbw.guestbook.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A DailyMenu is the description of a MenuItem.
 * example: if MenuItem = "Menu 1" then DailyMenu = "Lasagna"
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "access_level")
class AccessLevel extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

}
