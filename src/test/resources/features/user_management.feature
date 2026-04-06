# language: es

Característica: Gestión de usuarios por el administrador (HU-008)
  Como administrador del sistema CyberGuard
  Quiero crear, activar, desactivar y actualizar usuarios
  Para controlar quién puede acceder a la plataforma de ciberseguridad

  Antecedentes:
    Dado que el administrador está autenticado en el sistema

  @positivo @smoke @HU-008
  Escenario: Administrador accede a la página de gestión de usuarios
    Cuando el administrador navega a la sección de gestión de usuarios
    Entonces la página muestra el título de gestión de usuarios
    Y la tabla de usuarios está visible

  @positivo @smoke @HU-008
  Escenario: Administrador crea un nuevo usuario con datos válidos
    Dado que el administrador está en la página de gestión de usuarios
    Cuando hace clic en el botón de nuevo usuario
    Y completa el formulario con datos de un nuevo usuario de prueba y rol "soc_analyst"
    Y envía el formulario de creación
    Entonces aparece un mensaje de éxito en la página
    Y el nuevo usuario de prueba aparece en la tabla de usuarios

  @negativo @duplicado @HU-008
  Escenario: Rechazo de email duplicado en la creación de usuario
    Dado que el administrador está en la página de gestión de usuarios
    Cuando hace clic en el botón de nuevo usuario
    Y completa el formulario con email "admin@cyberguard.com", nombre completo "Admin Duplicado", username "admin.dup" y rol "soc_analyst"
    Y envía el formulario de creación
    Entonces aparece un mensaje de error en la página
    Y el formulario de creación permanece visible

  @negativo @proteccion-propia-cuenta @HU-008
  Escenario: El administrador no ve botones de acción para su propia cuenta
    Dado que el administrador está en la página de gestión de usuarios
    Entonces la fila del usuario "admin" no muestra botones de Editar ni Desactivar
    Y la fila del usuario "admin" muestra el badge "Tu cuenta"

  @positivo @toggle-status @HU-008
  Escenario: Administrador desactiva y reactiva un usuario existente
    Dado que el administrador está en la página de gestión de usuarios
    Y el usuario "soc" está visible en la tabla con estado "ACTIVO"
    Cuando hace clic en el botón de toggle de estado para el usuario "soc"
    Entonces el badge de estado del usuario "soc" muestra "INACTIVO"
    Cuando hace clic en el botón de toggle de estado para el usuario "soc"
    Entonces el badge de estado del usuario "soc" muestra "ACTIVO"
