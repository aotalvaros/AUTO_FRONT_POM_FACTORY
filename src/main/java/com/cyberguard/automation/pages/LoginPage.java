package com.cyberguard.automation.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

@DefaultUrl("/autenticacion")
public class LoginPage extends PageObject {

    @FindBy(id = "username")
    private WebElementFacade usernameField;

    @FindBy(id = "password")
    private WebElementFacade passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElementFacade loginButton;

    @FindBy(css = ".alert-error")
    private WebElementFacade errorMessage;

    public void enterUsername(String username) {
        WebElementFacade field = $(By.id("username")).waitUntilVisible();
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElementFacade field = $(By.id("password")).waitUntilVisible();
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        $(By.cssSelector("button[type='submit']")).waitUntilClickable().click();
    }

    public String getErrorMessage() {
        return $(By.cssSelector(".alert-error")).waitUntilVisible().getText();
    }
}
