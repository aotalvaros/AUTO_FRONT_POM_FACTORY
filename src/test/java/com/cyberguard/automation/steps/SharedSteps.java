package com.cyberguard.automation.steps;

import com.cyberguard.automation.TestData;
import com.cyberguard.automation.pages.DashboardPage;
import com.cyberguard.automation.pages.LoginPage;
import io.cucumber.java.es.Dado;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedSteps {

    @Managed
    WebDriver driver;

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @Dado("que el administrador está autenticado en el sistema")
    public void adminIsAuthenticatedInSystem() {
        loginPage = new LoginPage();
        loginPage.setDriver(driver);
        dashboardPage = new DashboardPage();
        dashboardPage.setDriver(driver);

        loginPage.open();
        loginPage.enterUsername(TestData.ADMIN_EMAIL);
        loginPage.enterPassword(TestData.ADMIN_PASSWORD);
        loginPage.clickLogin();

        assertThat(dashboardPage.isDashboardDisplayed())
                .as("El administrador debe autenticarse y llegar al dashboard. " +
                    "Verificar: Firebase activo, credenciales correctas, backend corriendo en :3000")
                .isTrue();
    }

    @Dado("que el analista SOC está autenticado en el sistema")
    public void socAnalystIsAuthenticatedInSystem() {
        loginPage = new LoginPage();
        loginPage.setDriver(driver);
        dashboardPage = new DashboardPage();
        dashboardPage.setDriver(driver);

        loginPage.open();
        loginPage.enterUsername(TestData.SOC_ANALYST_EMAIL);
        loginPage.enterPassword(TestData.SOC_ANALYST_PASSWORD);
        loginPage.clickLogin();

        assertThat(dashboardPage.isDashboardDisplayed())
                .as("El analista SOC debe autenticarse y llegar al dashboard. " +
                    "Verificar: usuario soc@cyberguard.com creado en Firebase Console " +
                    "Y en tabla users de PostgreSQL con role=soc_analyst.")
                .isTrue();
    }

    @Dado("que el usuario no está autenticado en el sistema")
    public void userIsNotAuthenticated() {
        loginPage = new LoginPage();
        loginPage.setDriver(driver);
        loginPage.open();
    }

    @Dado("que un usuario con rol {string} está autenticado en el sistema")
    public void userWithRoleIsAuthenticated(String role) {
        loginPage = new LoginPage();
        loginPage.setDriver(driver);
        dashboardPage = new DashboardPage();
        dashboardPage.setDriver(driver);

        String email;
        String password;

        switch (role) {
            case "admin":
                email = TestData.ADMIN_EMAIL;
                password = TestData.ADMIN_PASSWORD;
                break;
            case "soc_analyst":
                email = TestData.SOC_ANALYST_EMAIL;
                password = TestData.SOC_ANALYST_PASSWORD;
                break;
            case "incident_handler":
                email = TestData.INCIDENT_HANDLER_EMAIL;
                password = TestData.INCIDENT_HANDLER_PASSWORD;
                break;
            default:
                email = role.replace("_", ".") + "@cyberguard.com";
                password = role.substring(0, 1).toUpperCase() + role.substring(1) + "Sofka123456";
                break;
        }

        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        loginPage.open();
        loginPage.enterUsername(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        assertThat(dashboardPage.isDashboardDisplayed())
                .as("El usuario con rol '" + role + "' debe autenticarse y llegar al dashboard. " +
                    "Verificar: cuenta Firebase creada para " + email)
                .isTrue();
    }
}
