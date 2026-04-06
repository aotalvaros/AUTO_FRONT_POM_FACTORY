# AUTO_FRONT_POM_FACTORY - CyberGuard System

Automatización de pruebas Front-End para **CyberGuard System** utilizando el patrón **Page Object Model (POM)** con **Page Factory** (`@FindBy`) sobre el framework **Serenity BDD**.

---

## Descripción

Este proyecto valida el comportamiento observable desde el navegador de tres historias de usuario del sistema CyberGuard, cubriendo **16 escenarios ejecutables**.

| HU | Feature | Escenarios | Tipo |
|----|---------|------------|------|
| HU-008 | Gestión de Usuarios | 5 | 3 positivos, 2 negativos |
| HU-001 | Gestión de Incidentes | 5 | 2 positivos, 3 negativos |
| HU-010 | Sidebar Colapsable | 6 | 6 positivos |

---

## Arquitectura

```
AUTO_FRONT_POM_FACTORY/
├── build.gradle
├── settings.gradle
├── serenity.conf
├── TEST_PLAN_POM_FACTORY.md
└── src/
    ├── main/java/com/cyberguard/automation/
    │   └── pages/
    │       ├── LoginPage.java              ← @DefaultUrl(/autenticacion) + @FindBy
    │       ├── DashboardPage.java          ← @DefaultUrl(/dashboard) + @FindBy
    │       ├── UserManagementPage.java     ← @DefaultUrl(/users) + @FindBy (HU-008)
    │       ├── IncidentPage.java           ← @DefaultUrl(/incidents) + @FindBy (HU-001)
    │       └── SidebarPage.java            ← Componente global + @FindBy (HU-010)
    └── test/
        ├── java/com/cyberguard/automation/
        │   ├── TestData.java               ← Constantes + datos dinámicos (RUN_SUFFIX)
        │   ├── runners/
        │   │   ├── UserManagementRunner.java    ← @Suite — HU-008
        │   │   ├── IncidentRunner.java          ← @Suite — HU-001
        │   │   └── SidebarRunner.java           ← @Suite — HU-010
        │   └── steps/
        │       ├── SharedSteps.java                  ← Pasos de autenticación compartidos
        │       ├── UserManagementStepDefinitions.java
        │       ├── IncidentStepDefinitions.java
        │       ├── SidebarStepDefinitions.java
        │       ├── ScenarioContext.java              ← ThreadLocal: estado entre hooks y steps
        │       └── PomHooks.java                     ← @Before/@After — setup de datos vía API
        └── resources/
            ├── features/
            │   ├── user_management.feature       ← HU-008 (5 escenarios)
            │   ├── incident_management.feature   ← HU-001 (5 escenarios)
            │   └── sidebar_navigation.feature    ← HU-010 (6 escenarios)
            ├── serenity.conf        ← configuración del driver y URL base
            └── cucumber.properties  ← glue + plugin Serenity
```

### Patrón utilizado

- **POM + Page Factory:** Cada página de la aplicación se modela como una clase Java que extiende `PageObject`. Los elementos se declaran con la anotación `@FindBy` de Serenity BDD, que mapea automáticamente los selectores CSS/XPath a variables inyectadas. Los métodos usan directamente estos campos inyectados, separando claramente la lógica de localización (en `@FindBy`) de la lógica de interacción (en los métodos).

### Gestión de datos de prueba

- **Idempotencia:** `TestData.java` genera emails únicos por ejecución (`RUN_SUFFIX = System.currentTimeMillis()`), evitando errores 409 Conflict en re-ejecuciones.
- **Setup vía API:** `PomHooks.java` crea amenazas críticas vía `POST /api/threats` antes de escenarios `@requiere-datos-previos`. El UUID se comparte con los steps mediante `ScenarioContext` (ThreadLocal).
- **Base URL centralizada:** todos los steps leen `webdriver.base.url` desde `serenity.conf` en lugar de URLs hardcodeadas.

---

## Stack Tecnológico

| Herramienta | Versión |
|-------------|---------|
| Java | 17 (OpenJDK) |
| Gradle | 8.12 |
| Serenity BDD | 4.2.12 |
| Serenity Gradle Plugin | 5.3.7 |
| Cucumber | 7.20.1 |
| JUnit Platform | 1.11.4 |
| AssertJ | 3.27.3 |
| WebDriver | Chrome (autodownload) |
| IDE | VS Code / IntelliJ IDEA |
| AI Assistant | GitHub Copilot |

---

## Repositorio bajo prueba

Este proyecto automatiza pruebas sobre **CyberGuard System**:
> [https://github.com/aotalvaros/cyberguard-system](https://github.com/aotalvaros/cyberguard-system)

---

## Prerequisitos

- **Java JDK 17+** instalado y configurado en `JAVA_HOME`
- **Google Chrome** instalado (el driver se descarga automáticamente)
- **CyberGuard System** clonado y corriendo localmente:
  ```bash
  git clone https://github.com/aotalvaros/cyberguard-system.git
  cd cyberguard-system
  sudo docker compose up --build
  ```
  Verificar que el frontend esté disponible en `http://localhost:4200`

> **Nota:** Si Chrome no resuelve `localhost` correctamente, actualiza `webdriver.base.url`
> en `src/test/resources/serenity.conf` a `http://127.0.0.1:4200`.

### Credenciales de prueba requeridas

| Cuenta | Email | Password | Rol |
|--------|-------|----------|-----|
| Administrador | `admin@cyberguard.com` | `AdminSofka123456` | `admin` |
| Analista SOC | `soc@cyberguard.com` | `SocSofka123456` | `soc_analyst` |
| Incident Handler | `incident.handler@cyberguard.com` | `HandlerSofka123456` | `incident_handler` |

Todas las cuentas deben existir en **Firebase Console** y en la tabla `users` de PostgreSQL.

---

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/aotalvaros/AUTO_FRONT_POM_FACTORY.git
   cd AUTO_FRONT_POM_FACTORY
   ```

2. Verificar que Gradle Wrapper esté disponible:
   ```bash
   ./gradlew --version
   ```

---

## Ejecución de Tests

### Ejecutar toda la suite y generar reporte
```bash
./gradlew clean test aggregate
```

### Ejecutar por historia de usuario
```bash
# Solo HU-008 — Gestión de Usuarios
./gradlew test -Dcucumber.filter.tags="@HU-008" aggregate

# Solo HU-001 — Incidentes
./gradlew test -Dcucumber.filter.tags="@HU-001" aggregate

# Solo HU-010 — Sidebar
./gradlew test -Dcucumber.filter.tags="@HU-010" aggregate
```

### Ejecutar solo smoke tests
```bash
./gradlew test -Dcucumber.filter.tags="@smoke" aggregate
```

### Excluir escenarios con gap de Firebase
```bash
./gradlew test -Dcucumber.filter.tags="not @pendiente-firebase" aggregate
```

### Abrir el reporte Serenity (Linux)
```bash
xdg-open target/site/serenity/index.html
```

---

## Reportes

Tras la ejecución, Serenity BDD genera un reporte HTML detallado en:

```
target/site/serenity/index.html
```

El reporte incluye:
- Resultado de cada escenario (passed / failed / error)
- Capturas de pantalla por paso
- Tiempo de ejecución por escenario y paso
- Detalle completo de interacciones con la UI

---

## Escenarios de Prueba

### HU-008 — Gestión de Usuarios (`user_management.feature`)

| # | Escenario | Etiquetas | Tipo |
|---|-----------|-----------|------|
| 1 | Administrador accede a la página de gestión de usuarios | `@positivo @smoke` | Positivo |
| 2 | Administrador crea un nuevo usuario con datos válidos | `@positivo @smoke` | Positivo |
| 3 | Rechazo de email duplicado en la creación de usuario | `@negativo @duplicado` | Negativo |
| 4 | El administrador no ve botones de acción para su propia cuenta | `@negativo @proteccion-propia-cuenta` | Negativo |
| 5 | Administrador desactiva y reactiva un usuario existente | `@positivo @toggle-status` | Positivo |

### HU-001 — Gestión de Incidentes (`incident_management.feature`)

| # | Escenario | Etiquetas | Tipo |
|---|-----------|-----------|------|
| 1 | Administrador accede a la lista de incidentes | `@positivo @smoke` | Positivo |
| 2 | Crea incidente desde amenaza de severidad crítica | `@positivo @smoke @requiere-datos-previos` | Positivo |
| 3 | Formulario no visible para usuario sin permiso | `@negativo @sin-autorizacion` | Negativo |
| 4 | Error al intentar crear incidente con UUID inexistente | `@negativo @validacion` | Negativo |
| 5 | Los filtros de estado y severidad están disponibles | `@positivo @filtros` | Positivo |

### HU-010 — Sidebar Colapsable (`sidebar_navigation.feature`)

| # | Escenario | Etiquetas | Tipo |
|---|-----------|-----------|------|
| 1 | Sidebar visible para usuario autenticado en el dashboard | `@positivo @smoke` | Positivo |
| 2 | Sidebar muestra ítem "Gestión Usuarios" para el rol admin | `@positivo @visibilidad-rol` | Positivo |
| 3 | Administrador puede colapsar el menú lateral | `@positivo @toggle-colapsar` | Positivo |
| 4 | Administrador puede expandir el menú colapsado | `@positivo @toggle-expandir` | Positivo |
| 5 | El estado colapsado persiste al recargar la página | `@positivo @persistencia` | Positivo |
| 6 | El ítem activo del menú se resalta al navegar | `@positivo @ruteo-activo` | Positivo |

---

## Escenarios Excluidos (TEST_PLAN §8)

Se excluyeron 2 escenarios de tipo `@sin-autenticacion` por conflicto con el bloque `Antecedentes` (que autentica al admin antes de cada escenario). La cobertura se delega a tests unitarios Vitest: `auth.guard.spec.ts` y `no-auth.guard.spec.ts`.

El escenario CP-INC-03 (`@sin-autorizacion`) fue implementado y es ejecutable con la cuenta `incident.handler@cyberguard.com`.

---

## Aplicación Bajo Prueba

| Servicio | URL |
|----------|-----|
| Frontend (Angular) | http://localhost:4200 |
| Backend API | http://localhost:3000 |

---

## Plan de Pruebas

Ver [TEST_PLAN_POM_FACTORY.md](TEST_PLAN_POM_FACTORY.md) para el plan completo con:
- Estrategia de datos de prueba y hooks
- Selectores verificados por Page Object
- Riesgos y limitaciones documentadas
- Trazabilidad Spec → Escenarios
- Principios ISTQB aplicados

---

## Proyecto Relacionado

- [CyberGuard System](https://github.com/aotalvaros/cyberguard-system) — Aplicación bajo prueba

---

## Autor

**Andrés Otalvaro**

---

**Última actualización:** 6 de abril de 2026