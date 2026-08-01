# Task Manager API

API REST de gestion de tâches et de projets, développée avec Spring Boot. Projet personnel construit de façon incrémentale (V1 → V6) pour démontrer une maîtrise pratique du développement backend Java : authentification sécurisée, gestion fine des permissions, pagination, tests et conteneurisation.

## Stack technique

- **Langage** : Java 21
- **Framework** : Spring Boot 4.1 (Spring Framework 7)
- **Sécurité** : Spring Security + JWT (authentification stateless)
- **Persistance** : Spring Data JPA / Hibernate
- **Base de données** : PostgreSQL
- **Documentation API** : springdoc-openapi (Swagger UI)
- **Tests** : JUnit 5 + Mockito
- **Conteneurisation** : Docker + Docker Compose
- **Build** : Maven

## Fonctionnalités

### Authentification & Sécurité
- Inscription / connexion avec mots de passe hashés (BCrypt)
- Authentification JWT stateless
- Rôles **USER** / **ADMIN** avec autorisations basées sur les rôles (`@PreAuthorize`)
- Isolation stricte des ressources : chaque utilisateur ne peut voir, modifier ou supprimer que ses propres projets et tâches

### Gestion des projets
- CRUD complet
- Chaque projet appartient à un utilisateur

### Gestion des tâches
- CRUD complet, rattachées à un projet
- Statuts (`TODO`, `IN_PROGRESS`, `DONE`) et priorités (`LOW`, `MEDIUM`, `HIGH`)
- Endpoint dédié pour la mise à jour du statut (`PATCH /task/{id}/status`)
- **Pagination** et **filtres** combinables (par statut et priorité) via l'API Specification de Spring Data JPA

### Qualité de code
- Séparation stricte entités JPA / DTOs (aucune donnée sensible exposée dans les réponses API)
- Gestion d'erreurs centralisée (`@RestControllerAdvice`) avec codes HTTP cohérents (400, 401, 403, 404, 500)
- Validation des entrées (Bean Validation)
- Tests unitaires sur la logique métier critique (autorisation, CRUD)

## Documentation de l'API

Une fois l'application lancée, la documentation interactive Swagger est disponible sur :

```
http://localhost:8081/swagger-ui.html
```

## Lancer le projet

### Avec Docker (recommandé)

Prérequis : [Docker Desktop](https://www.docker.com/products/docker-desktop/) installé et lancé.

```bash
git clone https://github.com/JMM-AT/task-manager-api.git
cd task-manager-api
docker-compose up -d --build
```

L'API est alors accessible sur `http://localhost:8081`, avec une base PostgreSQL provisionnée automatiquement.

Pour arrêter :
```bash
docker-compose down
```

Pour arrêter et supprimer les données de la base :
```bash
docker-compose down -v
```

### Sans Docker (développement local)

Prérequis : Java 21, Maven, une instance PostgreSQL locale.

1. Créer une base de données PostgreSQL
2. Configurer `src/main/resources/application.properties` avec vos identifiants
3. Lancer :
```bash
./mvnw spring-boot:run
```

## Endpoints principaux

| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| POST | `/auth/register` | Inscription | Public |
| POST | `/auth/login` | Connexion (renvoie un JWT) | Public |
| GET | `/project` | Liste des projets de l'utilisateur connecté | Authentifié |
| POST | `/project` | Créer un projet | Authentifié |
| GET | `/project/{id}/tasks` | Tâches d'un projet (paginé, filtrable) | Propriétaire |
| POST | `/project/{id}/task` | Créer une tâche dans un projet | Propriétaire |
| PATCH | `/task/{id}/status` | Modifier le statut d'une tâche | Propriétaire |
| GET | `/admin/users` | Liste de tous les utilisateurs | Admin |

La liste complète et interactive est disponible via Swagger UI.

## Exemple de requête paginée et filtrée

```
GET /project/1/tasks?status=TODO&priorite=HIGH&page=0&size=10&sort=createdAt,desc
```

## Tests

```bash
./mvnw test
```

Couvre notamment :
- La récupération d'une tâche (cas existant / introuvable)
- La vérification d'autorisation (un utilisateur ne peut pas accéder aux ressources d'un autre)

## Roadmap

- [x] V1 — CRUD basique
- [x] V2 — Validation des entrées + gestion d'erreurs centralisée
- [x] V3 — Authentification JWT
- [x] V4 — Projets, isolation de sécurité par utilisateur
- [x] DTOs — séparation entités / API
- [x] V5 — Pagination, filtres, rôles USER/ADMIN
- [x] Documentation Swagger
- [x] Tests unitaires (JUnit/Mockito)
- [x] Conteneurisation Docker

### Pistes d'évolution futures
- Authentification OAuth2 (Google/GitHub)
- Assignation de tâches à d'autres membres d'un projet (nécessite une notion de membres de projet, au-delà du simple propriétaire)
- CI/CD (GitHub Actions)
- Déploiement cloud (AWS)

## Sécurité

- Mots de passe hashés avec BCrypt, jamais stockés en clair
- Authentification stateless via JWT (pas de session serveur)
- Chaque requête sur les ressources protégées vérifie que l'utilisateur connecté est bien le propriétaire de la ressource demandée
- Les messages d'erreur internes (SQL, stack traces) ne sont jamais exposés au client