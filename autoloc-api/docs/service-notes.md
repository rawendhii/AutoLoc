
# Atelier 4 — Couche Service, IoC et injection de dépendances

## A. Comparaison des injections de dépendances

### 1. Injection par constructeur

Spring fournit les dépendances au moment de créer l'objet.

Exemple :

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
private final IClientRepository clientRepository;
}

Avantages :
- Les dépendances sont obligatoires.
- Les attributs peuvent être final.
- Le code est plus facile à tester.
- Les dépendances sont clairement visibles.

### 2. Injection par attribut (@Autowired)

Spring injecte directement la dépendance dans un attribut.

Exemple :

@Service
public class ClientServiceImpl implements IClientService {
@Autowired
private IClientRepository clientRepository;
}

Inconvénients :
- Les dépendances sont moins visibles.
- Les tests unitaires sont plus difficiles.
- L'attribut ne peut pas être final avec cette injection.

### 3. Choix retenu

Nous avons choisi l'injection par constructeur avec
@RequiredArgsConstructor et private final.

Cette méthode est recommandée pour avoir un code
plus clair, plus fiable et plus facile à tester.

Lors de l'expérience avec @Autowired, SonarQube
a signalé la règle java:S6813, qui déconseille
l'injection par attribut.

## B. Tableau récapitulatif des services

| Service | Méthodes | Validation métier |
|---|---|---|
| AgenceServiceImpl | CRUD complet | Nom obligatoire |
| EmployeServiceImpl | CRUD complet | Nom et rôle obligatoires |
| VehiculeService | CRUD complet | Tarif journalier positif ou nul, champs obligatoires |
| EquipementServiceImpl | CRUD complet | Libellé obligatoire |
| ClientServiceImpl | CRUD complet | Nom et email obligatoires |
| ReservationServiceImpl | CRUD complet | Date de fin >= date de début |
| ContratServiceImpl | CRUD complet | Montant total positif ou nul |
| PaiementServiceImpl | Lecture seule | findById() et findAll() uniquement |
| MaintenanceServiceImpl | CRUD complet | Date de fin >= date de début |

### Explication

CRUD signifie :
- Create : créer un objet.
- Read : consulter un objet.
- Update : modifier un objet.
- Delete : supprimer un objet.

Chaque service utilise un Repository injecté
par constructeur grâce à @RequiredArgsConstructor.

Les validations métier sont réalisées dans
la couche Service avant l'enregistrement en base.

Le service Paiement est différent :
il permet uniquement la consultation.

Pour Equipement, notre entité ne possède pas
d'attribut quantite. Nous validons donc le libelle.

Pour Maintenance, notre entité ne possède pas
d'attribut cout. Nous validons donc les dates.


## C. Erreurs du conteneur Spring IoC

### 1. Bean absent

Problème :
Spring ne trouve pas le service demandé.

Cause possible :
La classe ne possède pas l'annotation @Service
ou se trouve en dehors des packages analysés.

Solution :
Ajouter @Service et vérifier le package de la classe.

### 2. Plusieurs beans du même type

Problème :
Spring trouve plusieurs implémentations
d'une même interface et ne sait pas laquelle choisir.

Cause :
Deux classes implémentent la même interface
et sont déclarées comme beans Spring.

Solutions :
- @Primary : définir l'implémentation prioritaire.
- @Qualifier : sélectionner une implémentation précise.

### 3. Dépendance circulaire

Problème :
Le service A dépend du service B,
et le service B dépend du service A.

Exemple :
ServiceA → ServiceB → ServiceA

Cause :
Spring ne peut pas construire normalement
ces deux services avec une injection par constructeur.

Solution :
Réorganiser les responsabilités des services
pour supprimer la dépendance circulaire.

### Conclusion

Le conteneur IoC de Spring crée les services
et injecte automatiquement leurs dépendances.

Une mauvaise configuration peut provoquer
une erreur lors du démarrage de l'application.

## D. Observations SonarQube

### 1. Injection par attribut — java:S6813

Fichier : ContratServiceImpl.java

Problème :
Nous avons remplacé temporairement l'injection
par constructeur par une injection @Autowired
directement sur un attribut.

SonarQube a signalé la règle java:S6813.

Correction :
Nous avons rétabli l'injection par constructeur
avec @RequiredArgsConstructor et private final.

Résultat :
Le problème a été corrigé.

### 2. Exposition directe d'une entité JPA — java:S4684

Fichier : VehiculeController.java

Problème :
Le contrôleur utilise directement une entité JPA
comme paramètre de @RequestBody.

SonarQube a signalé un risque lié à cette pratique.

Solution envisagée :
Utiliser un DTO pour contrôler les données reçues.

État :
Problème identifié, correction reportée
à l'atelier consacré à la couche Web.

### 3. Imports inutilisés

Fichiers concernés lors de l'Atelier 3 :
- Maintenance.java
- IVehiculeService.java
- VehiculeService.java

Problème :
Certains imports étaient présents mais
n'étaient pas utilisés dans le code.

Correction :
Nous avons supprimé les imports inutilisés.

Résultat :
Le code est plus propre et plus lisible.

### Remarque sur java:S112

Nous avons également expérimenté l'utilisation
d'une exception générique RuntimeException.

La règle java:S112 concerne les exceptions génériques.

Cependant, aucune alerte n'a été observée
pendant notre vérification.

Nous avons rétabli ResourceNotFoundException
pour conserver une exception métier spécifique.

### Conclusion

SonarQube permet de détecter des problèmes
de maintenabilité, de sécurité et de qualité.

Les observations 2 et 3 proviennent également
des travaux précédents sur AutoLoc.


## E. Questions de compréhension

### 1. Qui crée les objets Service dans Spring ?

C'est le conteneur IoC de Spring.

Grâce à l'annotation @Service, Spring détecte
la classe, crée son objet et gère ses dépendances.

### 2. Pourquoi injecter une interface dans le Controller ?

Pour réduire le couplage entre le Controller
et l'implémentation du Service.

Exemple :
private final IVehiculeService vehiculeService;

On peut changer l'implémentation du Service
sans modifier le Controller.

### 3. Pourquoi les Services sont-ils généralement Singleton ?

Par défaut, Spring crée une seule instance
de chaque Service et la partage.

Les Services doivent donc être stateless :
ils ne doivent pas conserver les données
propres à un utilisateur ou à une requête
dans des attributs modifiables.

### 4. Pourquoi rechercher un objet avant update() ?

Pour vérifier que l'objet existe déjà.

Cela permet aussi de modifier seulement
les attributs nécessaires sans changer
son identifiant ou ses associations JPA.

Exemple :
Client existant = findById(id);
existant.setNom(client.getNom());
return clientRepository.save(existant);

### 5. Quelle est la différence entre @Component et @Bean ?

@Component :
Annotation placée sur une classe.
Spring la détecte automatiquement.

@Bean :
Annotation placée sur une méthode
dans une classe de configuration.
Cette méthode fournit un objet à Spring.

@Service est une spécialisation de @Component.

### 6. Quelle est la différence entre @Primary et @Qualifier ?

@Primary :
Indique le bean préféré lorsqu'il existe
plusieurs implémentations du même type.

@Qualifier :
Permet de sélectionner explicitement
le bean à injecter.

### 7. Pourquoi le service Paiement est-il en lecture seule ?

C'est une contrainte demandée par l'Atelier 4.

IPaiementService contient uniquement :
- findById(Long id)
- findAll()

Il ne propose pas create(), update()
ou deleteById().

Cela empêche les utilisateurs de cette interface
d'effectuer directement ces opérations
à travers le service Paiement.

### Conclusion générale

Dans cet atelier, nous avons créé neuf Services
Spring Boot et appliqué l'injection par constructeur.

Nous avons séparé la logique métier de l'accès
aux données et ajouté des validations adaptées
aux différentes entités.

Nous avons également étudié les erreurs du
conteneur IoC et les observations SonarQube.
