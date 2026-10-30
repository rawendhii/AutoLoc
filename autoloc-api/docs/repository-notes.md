
# Atelier 3 — Spring Data JPA et Repository

## 1. Choix des interfaces Repository

Dans le projet AutoLoc, nous utilisons JpaRepository
pour accéder aux données des neuf entités.

JpaRepository permet les opérations CRUD, le tri,
la pagination et des méthodes JPA comme flush().

| Interface | Étend | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD des agences |
| IClientRepository | JpaRepository<Client, Long> | CRUD des clients |
| IContratRepository | JpaRepository<Contrat, Long> | CRUD des contrats et saveAndFlush() |
| IEmployeRepository | JpaRepository<Employe, Long> | CRUD des employés |
| IEquipementRepository | JpaRepository<Equipement, Long> | CRUD des équipements |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | CRUD des maintenances |
| IPaiementRepository | JpaRepository<Paiement, Long> | Consultation des paiements |
| IReservationRepository | JpaRepository<Reservation, Long> | CRUD des réservations |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD des véhicules, tri et pagination |

## 2. Analyse SonarQube for IDE

Nous avons utilisé SonarQube for IDE dans IntelliJ
pour rechercher des anomalies de qualité du code.

### Anomalies identifiées

| Fichier | Règle | Anomalie | Correction |
|---|---|---|---|
| Maintenance.java | java:S1128 | Import jakarta.persistence.* dupliqué | Import inutile supprimé et correction vérifiée |
| IVehiculeService.java | java:S1128 | Import inutilisé à la ligne 3 | Suppression de l'import signalé, à confirmer |
| VehiculeService.java | java:S1128 | Import inutilisé à la ligne 4 | Suppression de l'import signalé, à confirmer |

La règle java:S1128 recommande de supprimer
les imports inutilisés afin d'améliorer
la lisibilité et la maintenabilité du code.

## 3. Rappels sur Spring Data JPA

- Repository : interface marqueur.
- CrudRepository : opérations CRUD de base.
- ListCrudRepository : CRUD avec retour de List.
- PagingAndSortingRepository : tri et pagination.
- JpaRepository : CRUD, listes, pagination et flush.

## 4. Méthodes CRUD

- save() : enregistrer ou modifier une entité.
- findById() : rechercher une entité, retourne Optional.
- findAll() : récupérer les entités.
- deleteById() : supprimer par identifiant.
- count() : compter les entités.

## 5. Attention aux suppressions en lot

Les méthodes deleteAllInBatch() et
deleteAllByIdInBatch() contournent les cascades
JPA et orphanRemoval.

Elles doivent donc être utilisées avec prudence,
notamment pour les contrats et les paiements.

## Conclusion

La couche Repository d'AutoLoc utilise
JpaRepository pour les neuf entités.

L'analyse statique SonarQube for IDE a permis
d'identifier des imports inutilisés.
