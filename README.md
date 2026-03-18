# AUTO_FRONT_POM_FACTORY - CyberGuard System

Automatización de pruebas Front-End para **CyberGuard System** utilizando el patrón **Page Object Model (POM)** con **Page Factory** (`@FindBy`) sobre el framework **Serenity BDD**.

---

## Descripción

Este proyecto valida el flujo de autenticación de la plataforma CyberGuard System mediante dos escenarios independientes:

| # | Escenario | Tipo |
|---|-----------|------|
| 1 | Acceso exitoso al sistema con credenciales válidas | Positivo |
| 2 | Rechazo de acceso con credenciales incorrectas | Negativo |

---

## Arquitectura

```
AUTO_FRONT_POM_FACTORY/
├── build.gradle
├── settings.gradle
├── gradlew
├── gradle/wrapper/
├── src/
│   ├── main/java/com/cyberguard/automation/
│   │   └── pages/
│   │       ├── LoginPage.java          <- @FindBy + PageObject
│   │       └── DashboardPage.java      <- PageObject
│   └── test/
│       ├── java/com/cyberguard/automation/
│       │   ├── runners/
│       │   │   └── LoginRunner.java    <- @Suite JUnit Platform
│       │   └── steps/
│       │       └── LoginStepDefinitions.java
│       └── resources/
│           ├── features/
│           │   └── login.feature
│           ├── serenity.conf           <- configuración del driver
│           └── cucumber.properties     <- glue + plugin Serenity
```

### Patrón utilizado

- **POM + Page Factory:** Cada página de la aplicación se modela como una clase Java que extiende `PageObject`. Los elementos se declaran con la anotación `@FindBy` de Serenity separando la lógica de localización de la lógica de interacción y de prueba.

---

## Stack Tecnológico

| Herramienta | Versión |
|-------------|---------|
| Java | 21 (OpenJDK) |
| Gradle | 8.12 |
| Serenity BDD | 4.2.12 |
| Serenity Gradle Plugin | 5.3.7 |
| Cucumber | 7.20.1 |
| WebDriver | Chrome (autodownload) |
| IDE | VS Code / IntelliJ IDEA |
| AI Assistant | GitHub Copilot |

---

## Prerequisitos

- **Java JDK 17+** instalado y configurado en `JAVA_HOME`
- **Google Chrome** instalado (el driver se descarga automáticamente)
- **CyberGuard System** corriendo localmente:
  ```bash
  cd cyberguard-system
  sudo docker compose up --build
  ```
  Verificar que el frontend esté disponible en `http://localhost:4200`

---

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone <repo-url>
   cd AUTO_FRONT_POM_FACTORY
   ```

2. Verificar que Gradle Wrapper esté disponible:
   ```bash
   ./gradlew --version
   ```

---

## Ejecución de Tests

### Ejecutar todos los tests y generar reporte
```bash
./gradlew clean test aggregate
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

### Feature: Autenticación de usuario en CyberGuard System

**Escenario 1 — Flujo positivo:**
> El usuario accede exitosamente al sistema con credenciales válidas y es redirigido al panel de administración.

**Escenario 2 — Flujo negativo:**
> El usuario ingresa una contraseña incorrecta y el sistema muestra un mensaje indicando que las credenciales son inválidas.

---

## Aplicación Bajo Prueba

| Servicio | URL |
|----------|-----|
| Frontend (Angular) | http://localhost:4200 |
| Backend API | http://localhost:3000 |

---

## Proyecto Relacionado

- [CyberGuard System](https://github.com/aotalvaros/cyberguard-system) — Aplicación bajo prueba

---

## Autor

**Andrés Otalvaro**

---

**Última actualización:** 17 de marzo de 2026