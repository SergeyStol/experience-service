package by.javaguru.experienceservice.features.duties;

import by.javaguru.experienceservice.features.experience.Experience;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-13
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "duties")
@Getter
@Setter
public class Duty {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "uuid", unique = true, nullable = false)
   private UUID uuid;

   @Column(name = "name", nullable = false)
   private String name;

   @ManyToOne
   private Experience experience;

   @PrePersist
   void onSave() {
      if (uuid == null) {
         this.uuid = UUID.randomUUID();
      }
   }

   @Override
   public final boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
        .getHibernateLazyInitializer().getPersistentClass() : o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this)
        .getHibernateLazyInitializer().getPersistentClass() : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Duty duty = (Duty) o;
      return id != null && Objects.equals(id, duty.id);
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this)
        .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

   @Override
   public String toString() {
      return getClass().getSimpleName() + "(" +
             "id = " + id + ", " +
             "uuid = " + uuid + ", " +
             "name = " + name + ", " +
             "experience = " + experience + ")";
   }
}
