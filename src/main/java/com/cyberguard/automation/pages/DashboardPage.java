package com.cyberguard.automation.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("/dashboard")
public class DashboardPage extends PageObject {

    @FindBy(css = "header.dashboard-header")
    private WebElementFacade dashboardHeader;

    @FindBy(css = "h1")
    private WebElementFacade dashboardTitle;

    public boolean isDashboardDisplayed() {
        waitForCondition().until(driver -> driver.getCurrentUrl().contains("/dashboard"));
        dashboardHeader.waitUntilVisible();
        return getDriver().getCurrentUrl().contains("/dashboard")
                && dashboardHeader.isCurrentlyVisible();
    }

    public String getDashboardTitle() {
        return dashboardTitle.waitUntilVisible().getText();
    }
}
