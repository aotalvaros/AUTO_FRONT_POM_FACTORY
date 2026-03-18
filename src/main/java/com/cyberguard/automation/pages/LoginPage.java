package com.cyberguard.automation.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

@DefaultUrl("/login")
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
        $(By.id("username")).waitUntilVisible().type(username);
    }

    public void enterPassword(String password) {
        $(By.id("password")).waitUntilVisible().type(password);
    }

    public void clickLogin() {
        $(By.cssSelector("button[type='submit']")).waitUntilClickable().click();
    }

    public String getErrorMessage() {
        return $(By.cssSelector(".alert-error")).waitUntilVisible().getText();
    }
}
