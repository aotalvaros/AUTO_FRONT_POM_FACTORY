package com.cyberguard.automation.steps;

import com.cyberguard.automation.pages.IncidentPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class IncidentStepDefinitions {

    @Managed
    WebDriver driver;

    IncidentPage incidentPage;

    @Cuando("el administrador navega a la sección de incidentes")
    public void adminNavigatesToIncidents() {
        incidentPage = new IncidentPage();
        incidentPage.setDriver(driver);
        incidentPage.open();
        incidentPage.waitForPage();
    }

    @Cuando("navega a la sección de incidentes")
    public void navigatesToIncidents() {
        incidentPage = new IncidentPage();
        incidentPage.setDriver(driver);
        incidentPage.open();
        incidentPage.waitForPage();
    }

    @Dado("que el administrador está en la página de incidentes")
    public void adminIsOnIncidentsPage() {
        incidentPage = new IncidentPage();
        incidentPage.setDriver(driver);
        incidentPage.open();
        incidentPage.waitForPage();
    }

    @Entonces("la página de incidentes está visible")
    public void incidentsPageIsVisible() {
        assertThat(incidentPage.isPageDisplayed())
                .as("La página de Incidentes debe estar visible (URL contiene /incidents)")
                .isTrue();
    }

    @Y("el botón \"Crear Incidente\" está disponible para el administrador")
    public void createIncidentButtonIsAvailable() {
        assertThat(incidentPage.isCreateButtonVisible())
                .as("El botón 'Crear Incidente' debe estar visible para el rol admin. " +
                    "Si falla: verificar que canCreate() retorna true para el usuario logueado.")
                .isTrue();
    }

    @Y("el botón \"Crear Incidente\" no está disponible en la página")
    public void createIncidentButtonIsNotAvailable() {
        assertThat(incidentPage.isCreateButtonVisible())
                .as("El botón 'Crear Incidente' NO debe estar visible para roles sin permiso. " +
                    "Si falla: canCreate() está retornando true para un rol no autorizado (security bug).")
                .isFalse();
    }

    @Cuando("abre el formulario de creación de incidente")
    public void openCreateIncidentForm() {
        incidentPage.clickCreateIncidentButton();
        assertThat(incidentPage.isCreateFormVisible())
                .as("El formulario de creación de incidente debe abrirse al hacer clic en el botón")
                .isTrue();
    }

    @Y("ingresa el ID de una amenaza crítica existente")
    public void enterCriticalThreatId() {
        String threatId = ScenarioContext.get().getCriticalThreatId();
        assertThat(threatId)
                .as("El UUID de la amenaza crítica debe estar en ScenarioContext. " +
                    "Verificar que PomHooks.createCriticalThreatViaApi() se ejecutó correctamente. " +
                    "El escenario debe estar anotado con @requiere-datos-previos.")
                .isNotEmpty();
        incidentPage.enterThreatId(threatId);
    }

    @Y("ingresa el ID {string} como amenaza")
    public void enterSpecificThreatId(String threatId) {
        incidentPage.enterThreatId(threatId);
    }

    @Y("envía el formulario de incidente")
    public void submitIncidentForm() {
        incidentPage.submitCreateForm();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    @Entonces("aparece un mensaje de confirmación de creación")
    public void creationConfirmationMessageAppears() {
        assertThat(incidentPage.isSuccessAlertVisible())
                .as("Debe aparecer un mensaje de éxito tras crear el incidente")
                .isTrue();
    }

    @Y("la tabla de incidentes muestra al menos un registro")
    public void incidentTableHasAtLeastOneRecord() {
        assertThat(incidentPage.getVisibleIncidentCount())
                .as("La tabla de incidentes debe tener al menos 1 registro")
                .isGreaterThanOrEqualTo(1);
    }

    @Entonces("aparece un mensaje de error en la página de incidentes")
    public void errorMessageAppearsOnIncidentPage() {
        assertThat(incidentPage.isErrorAlertVisible())
                .as("Debe aparecer un mensaje de error (.alert.alert-error) tras intentar " +
                    "crear un incidente con un UUID de amenaza no encontrado")
                .isTrue();
    }

    @Y("el formulario de incidente permanece visible")
    public void incidentFormRemainsVisible() {
        assertThat(incidentPage.isCreateFormVisible())
                .as("El formulario debe permanecer visible tras un error para que el usuario pueda corregir el ID")
                .isTrue();
    }

    @Entonces("la barra de filtros está visible en la página de incidentes")
    public void filterBarIsVisible() {
        assertThat(driver.findElements(
                org.openqa.selenium.By.cssSelector(".filters-bar")).size())
                .as("La barra de filtros (.filters-bar) debe estar visible en la página de incidentes")
                .isGreaterThan(0);
    }

}
