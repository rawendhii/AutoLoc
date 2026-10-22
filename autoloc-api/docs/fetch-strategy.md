
# Atelier 2 — Stratégies Fetch et Cascade

## Projet AutoLoc

Ce document présente les stratégies de chargement
(FetchType) et de propagation (CascadeType)
utilisées dans les associations JPA.

| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Les paiements dépendent du contrat. Supprimer le contrat supprime ses paiements. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule peut exister indépendamment de son agence. |
| Agence → Employe | LAZY | Aucune | Les employés ne doivent pas être supprimés automatiquement avec l'agence. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules. |
| Client → Reservation | LAZY | PERSIST | Enregistrer un nouveau client peut enregistrer ses nouvelles réservations associées. |
| Reservation → Vehicule | LAZY | Aucune | Supprimer une réservation ne doit pas supprimer son véhicule. |
| Reservation ↔ Contrat | LAZY | ALL côté Reservation | Les opérations sur une réservation sont propagées à son contrat. |
| Vehicule → Maintenance | LAZY | PERSIST | L'enregistrement d'un véhicule peut propager l'enregistrement de ses maintenances. |

## Pourquoi FetchType.LAZY ?

LAZY permet de charger les associations uniquement
lorsqu'elles sont nécessaires.

Cela évite les chargements inutiles et peut améliorer
les performances de l'application.

## Pourquoi utiliser CascadeType ?

CascadeType permet de propager certaines opérations
JPA entre les entités liées.

- PERSIST : propagation de l'enregistrement.
- MERGE : propagation de la fusion des modifications.
- REMOVE : propagation de la suppression.
- ALL : propagation de toutes les opérations.

## Pourquoi orphanRemoval ?

Dans la relation Contrat → Paiement,
orphanRemoval = true permet de supprimer un paiement
de la base lorsqu'il est retiré de la collection
de son contrat dans un contexte JPA approprié.

## Conclusion

Toutes les associations du modèle AutoLoc sont
configurées avec FetchType.LAZY.

Les cascades sont choisies selon les dépendances
entre les entités et les exigences de l'Atelier 2.
