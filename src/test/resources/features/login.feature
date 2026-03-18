# language: es

Característica: Autenticación de usuario en CyberGuard System

  Escenario: Acceso exitoso al sistema con credenciales válidas
    Dado que el usuario se encuentra en la página de inicio de sesión
    Cuando ingresa las credenciales válidas del administrador
    Y confirma el inicio de sesión
    Entonces el sistema lo redirige al panel de administración

  Escenario: Rechazo de acceso con credenciales incorrectas
    Dado que el usuario se encuentra en la página de inicio de sesión
    Cuando ingresa una contraseña incorrecta
    Y confirma el inicio de sesión
    Entonces el sistema muestra un mensaje de credenciales inválidas