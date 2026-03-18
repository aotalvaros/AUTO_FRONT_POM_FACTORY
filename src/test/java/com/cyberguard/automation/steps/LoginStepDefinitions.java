package com.cyberguard.automation.steps;

import com.cyberguard.automation.pages.DashboardPage;
import com.cyberguard.automation.pages.LoginPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginStepDefinitions {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Dado("que el usuario se encuentra en la página de inicio de sesión")
    public void userNavigatesToLoginPage() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        loginPage.open();
    }

    @Cuando("ingresa las credenciales válidas del administrador")
    public void enterValidAdminCredentials() {
        loginPage.enterUsername("admin@cyberguard.com");
        loginPage.enterPassword("AdminSofka123456");
    }

    @Cuando("ingresa una contraseña incorrecta")
    public void enterIncorrectPassword() {
        loginPage.enterUsername("admin@cyberguard.com");
        loginPage.enterPassword("WrongPassword123!");
    }

    @Y("confirma el inicio de sesión")
    public void confirmLogin() {
        loginPage.clickLogin();
    }

    @Entonces("el sistema lo redirige al panel de administración")
    public void systemRedirectsToDashboard() {
        assertThat(dashboardPage.isDashboardDisplayed()).isTrue();
    }

    @Entonces("el sistema muestra un mensaje de credenciales inválidas")
    public void systemShowsInvalidCredentialsMessage() {
        String errorText = loginPage.getErrorMessage();
        assertThat(errorText).isNotEmpty();
    }
}
