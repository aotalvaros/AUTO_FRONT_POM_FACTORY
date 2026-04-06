package com.cyberguard.automation.steps;

import com.cyberguard.automation.pages.SidebarPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class SidebarStepDefinitions {

    @Managed
    WebDriver driver;

    SidebarPage sidebarPage;

    private String getBaseUrl() {
        return net.serenitybdd.model.environment.EnvironmentSpecificConfiguration
                .from(net.serenitybdd.model.environment.ConfiguredEnvironment.getEnvironmentVariables())
                .getProperty("webdriver.base.url");
    }

    @Cuando("el administrador navega al dashboard")
    public void adminNavigatesToDashboard() {
        sidebarPage = new SidebarPage();
        sidebarPage.setDriver(driver);
        driver.get(getBaseUrl() + "/dashboard");
        try { Thread.sleep(1200); } catch (InterruptedException ignored) {}
    }

    @Dado("que el administrador está en el dashboard con el menú lateral visible")
    public void adminIsOnDashboardWithSidebarVisible() {
        sidebarPage = new SidebarPage();
        sidebarPage.setDriver(driver);
        driver.get(getBaseUrl() + "/dashboard");
        try { Thread.sleep(1200); } catch (InterruptedException ignored) {}
        assertThat(sidebarPage.isSidebarVisible())
                .as("El sidebar debe estar visible en /dashboard antes de proceder")
                .isTrue();
    }

    @Entonces("el menú lateral está visible en la página")
    public void sidebarIsVisible() {
        assertThat(sidebarPage.isSidebarVisible())
                .as("El sidebar (nav.sidebar) debe estar visible en rutas autenticadas")
                .isTrue();
    }

    @Y("el menú lateral está expandido por defecto")
    public void sidebarIsExpandedByDefault() {
        assertThat(sidebarPage.isExpanded())
                .as("El sidebar debe estar expandido por defecto (sin clase 'sidebar--collapsed'). " +
                    "Si falla: localStorage puede tener un estado guardado de sesiones previas. " +
                    "Limpia localStorage y prueba de nuevo.")
                .isTrue();
    }

    @Entonces("el menú lateral contiene el ítem de navegación {string}")
    public void sidebarContainsNavItem(String itemText) {
        assertThat(sidebarPage.hasNavItemWithText(itemText))
                .as("El sidebar debe mostrar el ítem '" + itemText + "' para el usuario logueado. " +
                    "La visibilidad de 'Gestión Usuarios' es controlada por visibleItems() en el componente.")
                .isTrue();
    }

    @Y("el menú lateral muestra {int} ítems de navegación")
    public void sidebarShowsNavItemCount(int expectedCount) {
        assertThat(sidebarPage.getVisibleNavItemCount())
                .as("El sidebar debe mostrar exactamente " + expectedCount + " ítems para el rol admin. " +
                    "Admin: Dashboard, Reportar Amenaza, Incidentes, Gestión Usuarios (4 ítems). " +
                    "Si falla: verificar visibleItems() computed signal en SidebarComponent.")
                .isEqualTo(expectedCount);
    }

    @Cuando("hace clic en el botón de colapsar el menú")
    public void clickToggleSidebar() {
        sidebarPage.clickToggle();
        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
    }

    @Cuando("hace clic nuevamente en el botón de toggle del menú")
    public void clickToggleSidebarAgain() {
        sidebarPage.clickToggle();
        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
    }

    @Entonces("el menú lateral queda en estado colapsado")
    public void sidebarIsCollapsed() {
        assertThat(sidebarPage.isCollapsed())
                .as("El sidebar debe tener la clase CSS 'sidebar--collapsed' tras el toggle. " +
                    "Selectores verificados: nav.sidebar[class*='sidebar--collapsed']")
                .isTrue();
    }

    @Entonces("el menú lateral queda en estado expandido")
    public void sidebarIsExpanded() {
        assertThat(sidebarPage.isExpanded())
                .as("El sidebar NO debe tener la clase 'sidebar--collapsed' tras segundo toggle")
                .isTrue();
    }

    @Y("el texto de la marca \"CyberGuard\" no es visible")
    public void brandTextIsNotVisible() {
        assertThat(sidebarPage.isBrandTextVisible())
                .as("El texto '.brand-text' ('CyberGuard') no debe ser visible en modo colapsado. " +
                    "En el template: @if (!collapsed()) { <span class='brand-text'>CyberGuard</span> }")
                .isFalse();
    }

    @Y("el texto de la marca \"CyberGuard\" es visible")
    public void brandTextIsVisible() {
        assertThat(sidebarPage.isBrandTextVisible())
                .as("El texto '.brand-text' ('CyberGuard') debe ser visible cuando el sidebar está expandido")
                .isTrue();
    }

    @Y("el sistema guarda el estado en el almacenamiento local")
    public void systemSavesStateInLocalStorage() {
        String storedValue = sidebarPage.getLocalStorageCollapsedValue();
        assertThat(storedValue)
                .as("localStorage('sidebar-collapsed') debe tener un valor guardado. " +
                    "Si retorna 'null': el componente no persiste el estado (bug de HU-010).")
                .isNotEqualTo("null")
                .isNotEqualTo("error");
    }

    @Cuando("recarga la página del dashboard")
    public void reloadDashboard() {
        driver.navigate().refresh();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    @Entonces("el menú lateral sigue en estado colapsado")
    public void sidebarStillCollapsedAfterReload() {
        sidebarPage = new SidebarPage();
        sidebarPage.setDriver(driver);
        assertThat(sidebarPage.isCollapsed())
                .as("El sidebar debe seguir colapsado tras recargar — lee el estado de localStorage al inicializar")
                .isTrue();
    }

    @Cuando("navega a la sección de incidentes desde el menú lateral")
    public void navigateToIncidentFromSidebar() {
        sidebarPage.clickNavItem("Incidentes");
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
    }

    @Entonces("el ítem {string} del menú lateral aparece resaltado como activo")
    public void navItemIsHighlightedAsActive(String itemText) {
        String activeText = sidebarPage.getActiveNavItemText();
        assertThat(activeText)
                .as("El ítem '" + itemText + "' debe tener la clase 'sidebar__link--active' " +
                    "cuando se está en esa ruta. Valida routerLinkActive='sidebar__link--active'.")
                .contains(itemText);
    }

    @Entonces("el menú lateral no está presente en la pantalla de inicio de sesión")
    public void sidebarIsAbsentFromLoginPage() {
        sidebarPage = new SidebarPage();
        sidebarPage.setDriver(driver);
        assertThat(sidebarPage.isSidebarAbsentFromDom())
                .as("El sidebar (nav.sidebar) NO debe estar en el DOM en la ruta /autenticacion. " +
                    "app.html tiene: @if (showSidebar()) { <app-sidebar /> }. " +
                    "Si falla: showSidebar() está retornando true sin usuario autenticado (security issue).")
                .isTrue();
    }
}
