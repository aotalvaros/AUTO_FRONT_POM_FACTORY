package com.cyberguard.automation.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

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
        usernameField.waitUntilVisible();
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.waitUntilVisible();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.waitUntilClickable().click();
    }

    public String getErrorMessage() {
        return errorMessage.waitUntilVisible().getText();
    }
}
