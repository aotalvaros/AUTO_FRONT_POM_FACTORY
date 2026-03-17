# AUTO_FRONT_POM_FACTORY - CyberGuard System

Automatización de pruebas Front-End para **CyberGuard System** utilizando el patrón **Page Object Model (POM)** con **Page Factory** (`@FindBy`) sobre el framework **Serenity BDD**.

---

##  Descripción

Este proyecto valida el flujo de autenticación de la plataforma CyberGuard System mediante dos escenarios independientes:

| # | Escenario | Tipo |
|---|-----------|------|
| 1 | Acceso exitoso al sistema con credenciales válidas | Positivo |
| 2 | Rechazo de acceso con credenciales incorrectas |  Negativo |

---

##  Arquitectura

```
AUTO_FRONT_POM_FACTORY/
├── build.gradle
├── gradle/
│   └── wrapper/
├── serenity.conf
├── README.md
├── src/
│   ├── main/java/com/cyberguard/automation/
│   │   └── pages/
│   │       ├── LoginPage.java
│   │       └── DashboardPage.java
│   └── test/
│       ├── java/com/cyberguard/automation/
│       │   ├── runners/
│       │   │   └── LoginRunner.java
│       │   └── steps/
│       │       └── LoginStepDefinitions.java
│       └── resources/
│           └── features/
│               └── login.feature
```

### Patrón utilizado

- **POM + Page Factory:** Cada página de la aplicación se representa como una clase Java con elementos localizados mediante la anotación `@FindBy`, separando la lógica de interacción de la lógica de prueba.

---

## Stack Tecnológico

| Herramienta | Versión |
|-------------|---------|
| Java | 17+ |
| Gradle | 8.x |
| Serenity BDD | 4.0.30+ |
| Cucumber | 7.15+ |
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
  docker compose up --build "o con" sudo docker compose up --build
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

### Ejecutar todos los tests
```bash
./gradlew clean test aggregate
```

### Ver reporte Serenity
```bash
xdg-open target/site/serenity/index.html
```

> En macOS usar `open` en lugar de `xdg-open`.

---

## Reportes

Tras la ejecución, Serenity BDD genera un reporte HTML detallado en:

```
target/site/serenity/index.html
```

El reporte incluye:
- Resultado de cada escenario (passed / failed)
- Capturas de pantalla por paso
- Tiempo de ejecución
- Detalle de interacciones con la UI

---

## Escenarios de Prueba

### Feature: Autenticación de usuario en CyberGuard System

**Escenario 1 — Flujo positivo:**
> El usuario accede exitosamente al sistema con credenciales válidas y es redirigido al panel de administración.

**Escenario 2 — Flujo negativo:**
> El usuario ingresa credenciales incorrectas y el sistema muestra un mensaje indicando que las credenciales son inválidas.

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

## 👤 Autor

**Andrés Otalvaro**

---

**Última actualización:** 16 de marzo de 2026