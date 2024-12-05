package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.duties.Duty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-13
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "experiences")
@Getter
@Setter
public class Experience {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "uuid", unique = true)
   private UUID uuid;

   @Column(name = "period_from")
   private LocalDate periodFrom;

   @Column(name = "period_to")
   private LocalDate periodTo;

   @Column(name = "present_time")
   private LocalDate presentTime;

   @Column(name = "industry")
   private Long industryId;

   @Column(name = "company")
   private String company;

   @Column(name = "position", nullable = false)
   private String position;

   @Column(name = "achievements")
   private String achievements;

   @Column(name = "link")
   private String link;

   @OneToMany(
     mappedBy = "experience",
     cascade = CascadeType.ALL,
     orphanRemoval = true,
     fetch = FetchType.EAGER
   )
   @JsonManagedReference
   private Set<Duty> duties = new LinkedHashSet<>();

   @PrePersist
   void prePersist() {
      if (uuid == null) {
         uuid = UUID.randomUUID();
      }
   }

   @Override
   public final boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Experience that = (Experience) o;
      return getId() != null && Objects.equals(getId(), that.getId());
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

   @Override
   public String toString() {
      return getClass().getSimpleName() + "(" +
             "id = " + id + ", " +
             "uuid = " + uuid + ", " +
             "periodFrom = " + periodFrom + ", " +
             "periodTo = " + periodTo + ", " +
             "presentTime = " + presentTime + ", " +
             "industryId = " + industryId + ", " +
             "company = " + company + ", " +
             "position = " + position + ", " +
             "achievements = " + achievements + ", " +
             "link = " + link + ")";
   }
}
