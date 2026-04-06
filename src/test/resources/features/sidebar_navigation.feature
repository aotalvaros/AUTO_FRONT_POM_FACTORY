# language: es

Característica: Menú lateral colapsable con visibilidad por rol (HU-010)
  Como usuario autenticado de CyberGuard
  Quiero un menú lateral que se colapse y muestre solo las opciones de mi rol
  Para navegar eficientemente sin distracciones visuales

  Antecedentes:
    Dado que el administrador está autenticado en el sistema

  @positivo @smoke @HU-010
  Escenario: Sidebar visible para usuario autenticado en el dashboard
    Cuando el administrador navega al dashboard
    Entonces el menú lateral está visible en la página
    Y el menú lateral está expandido por defecto

  @positivo @visibilidad-rol @HU-010
  Escenario: Sidebar muestra ítem "Gestión Usuarios" para el rol administrador
    Cuando el administrador navega al dashboard
    Entonces el menú lateral contiene el ítem de navegación "Gestión Usuarios"
    Y el menú lateral muestra 4 ítems de navegación

  @positivo @toggle-colapsar @HU-010
  Escenario: Administrador puede colapsar el menú lateral
    Dado que el administrador está en el dashboard con el menú lateral visible
    Cuando hace clic en el botón de colapsar el menú
    Entonces el menú lateral queda en estado colapsado
    Y el texto de la marca "CyberGuard" no es visible

  @positivo @toggle-expandir @HU-010
  Escenario: Administrador puede expandir el menú lateral colapsado
    Dado que el administrador está en el dashboard con el menú lateral visible
    Cuando hace clic en el botón de colapsar el menú
    Y hace clic nuevamente en el botón de toggle del menú
    Entonces el menú lateral queda en estado expandido
    Y el texto de la marca "CyberGuard" es visible

  @positivo @persistencia @HU-010
  Escenario: El estado colapsado persiste al recargar la página
    Dado que el administrador está en el dashboard con el menú lateral visible
    Cuando hace clic en el botón de colapsar el menú
    Y el menú lateral queda en estado colapsado
    Y el sistema guarda el estado en el almacenamiento local
    Cuando recarga la página del dashboard
    Entonces el menú lateral sigue en estado colapsado

  @positivo @ruteo-activo @HU-010
  Escenario: El ítem activo del menú se resalta al navegar
    Dado que el administrador está en el dashboard con el menú lateral visible
    Cuando navega a la sección de incidentes desde el menú lateral
    Entonces el ítem "Incidentes" del menú lateral aparece resaltado como activo
