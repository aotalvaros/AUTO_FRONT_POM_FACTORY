package com.cyberguard.automation.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SidebarPage extends PageObject {

    @FindBy(css = "nav.sidebar")
    private WebElementFacade sidebarNav;

    @FindBy(css = "button.sidebar__toggle")
    private WebElementFacade toggleButton;

    @FindBy(css = ".brand-text")
    private WebElementFacade brandText;

    @FindBy(css = "ul.sidebar__nav a.sidebar__link")
    private List<WebElementFacade> navLinks;

    @FindBy(css = "ul.sidebar__nav a.sidebar__link--active")
    private WebElementFacade activeNavLink;

    public boolean isSidebarVisible() {
        try {
            return sidebarNav.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCollapsed() {
        try {
            String classes = sidebarNav.waitUntilPresent().getAttribute("class");
            return classes != null && classes.contains("sidebar--collapsed");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExpanded() {
        return !isCollapsed();
    }

    public void clickToggle() {
        toggleButton.waitUntilClickable().click();
    }

    public int getVisibleNavItemCount() {
        return navLinks.size();
    }

    public boolean hasNavItemWithText(String text) {
        List<WebElement> labels = getDriver().findElements(By.cssSelector("ul.sidebar__nav .nav-label"));
        return labels.stream().anyMatch(el -> el.getText().equalsIgnoreCase(text));
    }

    public boolean isBrandTextVisible() {
        List<WebElement> brand = getDriver().findElements(By.cssSelector(".brand-text"));
        return !brand.isEmpty() && brand.get(0).isDisplayed();
    }

    public void clickNavItem(String text) {
        List<WebElement> labels = getDriver().findElements(By.cssSelector("ul.sidebar__nav .sidebar__link"));
        for (WebElement link : labels) {
            if (link.getText().contains(text)) {
                link.click();
                return;
            }
        }
        throw new RuntimeException("No se encontró ítem de navegación: " + text);
    }

    public String getActiveNavItemText() {
        try {
            WebElement label = activeNavLink.waitUntilVisible()
                    .findElement(By.cssSelector(".nav-label"));
            return label.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isSidebarAbsentFromDom() {
        List<WebElement> sidebars = getDriver().findElements(By.cssSelector("nav.sidebar"));
        return sidebars.isEmpty();
    }

    public String getLocalStorageCollapsedValue() {
        try {
            Object result = evaluateJavascript("return localStorage.getItem('cyberguard_sidebar_collapsed');");
            return result != null ? result.toString() : "null";
        } catch (Exception e) {
            return "error";
        }
    }
}
