package com.cyberguard.automation.steps;

import com.cyberguard.automation.TestData;
import com.cyberguard.automation.pages.UserManagementPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class UserManagementStepDefinitions {

    @Managed
    WebDriver driver;

    UserManagementPage userManagementPage;

    @Cuando("el administrador navega a la sección de gestión de usuarios")
    public void adminNavigatesToUserManagement() {
        userManagementPage = new UserManagementPage();
        userManagementPage.setDriver(driver);
        userManagementPage.open();
        userManagementPage.waitForPage();
    }

    @Dado("que el administrador está en la página de gestión de usuarios")
    public void adminIsOnUserManagementPage() {
        userManagementPage = new UserManagementPage();
        userManagementPage.setDriver(driver);
        userManagementPage.open();
        userManagementPage.waitForPage();
    }

    @Entonces("la página muestra el título de gestión de usuarios")
    public void pageTitleIsVisible() {
        assertThat(userManagementPage.isPageDisplayed())
                .as("La página de Gestión de Usuarios debe estar visible")
                .isTrue();
    }

    @Y("la tabla de usuarios está visible")
    public void usersTableIsVisible() {
        assertThat(driver.findElements(
                org.openqa.selenium.By.cssSelector(".data-table")).size())
                .as("La tabla .data-table debe estar presente en la página")
                .isGreaterThan(0);
    }

    @Cuando("hace clic en el botón de nuevo usuario")
    public void clickNewUserButton() {
        userManagementPage.clickNewUserButton();
    }

    @Y("completa el formulario con datos de un nuevo usuario de prueba y rol {string}")
    public void fillCreateFormWithTestData(String role) {
        assertThat(userManagementPage.isCreateFormVisible())
                .as("El formulario de creación debe estar visible antes de llenarlo")
                .isTrue();
        userManagementPage.fillEmail(TestData.NEW_USER_EMAIL);
        userManagementPage.fillFullName(TestData.NEW_USER_FULL_NAME);
        userManagementPage.fillUsername(TestData.NEW_USER_USERNAME);
        userManagementPage.selectRole(role);
    }

    @Y("completa el formulario con email {string}, nombre completo {string}, username {string} y rol {string}")
    public void fillCreateForm(String email, String fullName, String username, String role) {
        assertThat(userManagementPage.isCreateFormVisible())
                .as("El formulario de creación debe estar visible antes de llenarlo")
                .isTrue();
        userManagementPage.fillEmail(email);
        userManagementPage.fillFullName(fullName);
        userManagementPage.fillUsername(username);
        userManagementPage.selectRole(role);
    }

    @Y("envía el formulario de creación")
    public void submitCreateForm() {
        userManagementPage.submitCreateForm();
    }

    @Entonces("aparece un mensaje de éxito en la página")
    public void successMessageIsVisible() {
        assertThat(userManagementPage.isSuccessAlertVisible())
                .as("Debe aparecer un mensaje de éxito (.alert.alert-success)")
                .isTrue();
    }

    @Y("el nuevo usuario de prueba aparece en la tabla de usuarios")
    public void newTestUserAppearsInTable() {
        assertThat(userManagementPage.isUserInTable(TestData.NEW_USER_USERNAME))
                .as("El usuario '" + TestData.NEW_USER_USERNAME + "' debe aparecer en la tabla " +
                    "tras creación exitosa. Si falla: la UI no recarga la lista o la creación falló silenciosamente.")
                .isTrue();
    }

    @Y("el nuevo usuario aparece en la tabla de usuarios")
    public void newUserAppearsInTable() {
        assertThat(userManagementPage.getUserCount())
                .as("Debe haber al menos 2 usuarios en la tabla tras crear uno nuevo")
                .isGreaterThanOrEqualTo(2);
    }

    @Entonces("aparece un mensaje de error en la página")
    public void errorMessageIsVisible() {
        assertThat(userManagementPage.isErrorAlertVisible())
                .as("Debe aparecer un mensaje de error (.alert.alert-error)")
                .isTrue();
    }

    @Y("el formulario de creación permanece visible")
    public void createFormRemainsVisible() {
        assertThat(userManagementPage.isCreateFormVisible())
                .as("El formulario de creación debe permanecer visible tras un error")
                .isTrue();
    }

    @Entonces("la fila del usuario {string} no muestra botones de Editar ni Desactivar")
    public void ownUserRowHasNoActionButtons(String username) {
        assertThat(userManagementPage.hasActionButtonsForUser(username))
                .as("La fila del usuario '" + username + "' no debe mostrar botones de acción " +
                    "(es la cuenta propia del admin logueado)")
                .isFalse();
    }

    @Y("la fila del usuario {string} muestra el badge {string}")
    public void userRowShowsBadge(String username, String expectedBadge) {
        assertThat(userManagementPage.hasTuCuentaBadge(username))
                .as("La fila del usuario '" + username + "' debe mostrar el badge 'Tu cuenta'")
                .isTrue();
    }

    @Y("el usuario {string} está visible en la tabla con estado {string}")
    public void userIsVisibleWithStatus(String username, String expectedStatus) {
        assertThat(userManagementPage.isUserInTable(username))
                .as("El usuario '" + username + "' debe estar en la tabla")
                .isTrue();
        assertThat(userManagementPage.getUserStatusBadge(username))
                .as("El badge de estado del usuario '" + username + "' debe ser '" + expectedStatus + "'")
                .isEqualTo(expectedStatus);
    }

    @Cuando("hace clic en el botón de toggle de estado para el usuario {string}")
    public void clickToggleStatusForUser(String username) {
        userManagementPage.clickToggleStatusForUser(username);
        userManagementPage.waitForTableReload();
    }

    @Entonces("el badge de estado del usuario {string} muestra {string}")
    public void userStatusBadgeShows(String username, String expectedStatus) {
        assertThat(userManagementPage.getUserStatusBadge(username))
                .as("El badge de estado del usuario '" + username + "' debe mostrar '" + expectedStatus + "'")
                .isEqualTo(expectedStatus);
    }

    @Cuando("intenta navegar directamente a {string}")
    public void navigateDirectlyToPath(String path) {
        String baseUrl = net.serenitybdd.model.environment.EnvironmentSpecificConfiguration
                .from(net.serenitybdd.model.environment.ConfiguredEnvironment.getEnvironmentVariables())
                .getProperty("webdriver.base.url");
        driver.get(baseUrl + path);
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    @Entonces("el sistema lo redirige a la página de inicio de sesión")
    public void systemRedirectsToLogin() {
        assertThat(driver.getCurrentUrl())
                .as("El guard debe redirigir a /autenticacion cuando el usuario no está autenticado")
                .contains("/autenticacion");
    }
}
