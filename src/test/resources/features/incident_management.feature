# language: es

Característica: Gestión de incidentes desde amenazas (HU-001)
  Como administrador o analista SOC de CyberGuard
  Quiero crear incidentes a partir de amenazas detectadas
  Para iniciar el proceso de respuesta a incidentes de seguridad

  Antecedentes:
    Dado que el administrador está autenticado en el sistema

  @positivo @smoke @HU-001
  Escenario: Administrador accede a la lista de incidentes
    Cuando el administrador navega a la sección de incidentes
    Entonces la página de incidentes está visible
    Y el botón "Crear Incidente" está disponible para el administrador

  @positivo @smoke @requiere-datos-previos @HU-001
  Escenario: Administrador crea incidente desde amenaza de severidad crítica
    Dado que el administrador está en la página de incidentes
    Y el botón "Crear Incidente" está disponible para el administrador
    Cuando abre el formulario de creación de incidente
    Y ingresa el ID de una amenaza crítica existente
    Y envía el formulario de incidente
    Entonces aparece un mensaje de confirmación de creación
    Y la tabla de incidentes muestra al menos un registro

  @negativo @sin-autorizacion @HU-001
  Escenario: Formulario de creación no visible para usuario sin permiso de crear
    Dado que un usuario con rol "incident_handler" está autenticado en el sistema
    Cuando navega a la sección de incidentes
    Entonces la página de incidentes está visible
    Y el botón "Crear Incidente" no está disponible en la página

  @negativo @validacion @HU-001
  Escenario: Error al intentar crear incidente con UUID de amenaza inexistente
    Dado que el administrador está en la página de incidentes
    Y el botón "Crear Incidente" está disponible para el administrador
    Cuando abre el formulario de creación de incidente
    Y ingresa el ID "00000000-0000-0000-0000-000000000000" como amenaza
    Y envía el formulario de incidente
    Entonces aparece un mensaje de error en la página de incidentes
    Y el formulario de incidente permanece visible

  @positivo @filtros @HU-001
  Escenario: Los filtros de estado y severidad están disponibles en la página
    Dado que el administrador está en la página de incidentes
    Entonces la barra de filtros está visible en la página de incidentes
