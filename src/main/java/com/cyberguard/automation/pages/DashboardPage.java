package com.cyberguard.automation.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@DefaultUrl("/dashboard")
public class DashboardPage extends PageObject {

    @FindBy(css = "header.dashboard-header")
    private WebElementFacade dashboardHeader;

    @FindBy(css = "h1")
    private WebElementFacade dashboardTitle;

    public boolean isDashboardDisplayed() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        $(By.cssSelector("header.dashboard-header")).waitUntilVisible();
        return getDriver().getCurrentUrl().contains("/dashboard")
                && $(By.cssSelector("header.dashboard-header")).isCurrentlyVisible();
    }

    public String getDashboardTitle() {
        return $(By.cssSelector("h1")).waitUntilVisible().getText();
    }
}
