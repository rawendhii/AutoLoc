
package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long idMaintenance;

     LocalDate dateDebut;

     LocalDate dateFin;

     String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicule vehicule;

}
