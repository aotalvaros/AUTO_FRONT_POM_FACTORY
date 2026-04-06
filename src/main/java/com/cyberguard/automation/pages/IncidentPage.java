package com.cyberguard.automation.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@DefaultUrl("/incidents")
public class IncidentPage extends PageObject {

    @FindBy(css = "h1")
    private WebElementFacade pageTitle;

    @FindBy(css = ".toolbar button.btn-primary")
    private WebElementFacade createIncidentButton;

    @FindBy(css = ".toolbar h2")
    private WebElementFacade incidentCountLabel;

    @FindBy(css = ".form-card")
    private WebElementFacade createFormCard;

    @FindBy(css = "input[placeholder*='3f2504e0']")
    private WebElementFacade threatIdField;

    @FindBy(css = ".form-actions button.btn-primary")
    private WebElementFacade submitCreateButton;

    @FindBy(css = ".form-actions button.btn-secondary")
    private WebElementFacade cancelCreateButton;

    @FindBy(css = ".form-hint")
    private WebElementFacade formHintText;

    @FindBy(css = ".filters-bar .filters-form select:nth-of-type(1)")
    private WebElementFacade statusFilterSelect;

    @FindBy(css = ".filters-bar .filters-form select:nth-of-type(2)")
    private WebElementFacade severityFilterSelect;

    @FindBy(css = ".filters-bar .btn-secondary:first-of-type")
    private WebElementFacade filterButton;

    @FindBy(css = ".filters-bar .btn-secondary:last-of-type")
    private WebElementFacade clearFiltersButton;

    @FindBy(css = ".alert.alert-success")
    private WebElementFacade successAlert;

    @FindBy(css = ".alert.alert-error")
    private WebElementFacade errorAlert;

    @FindBy(css = ".data-table tbody tr")
    private List<WebElementFacade> incidentRows;

    @FindBy(css = ".empty-state")
    private WebElementFacade emptyState;

    public void waitForPage() {
        waitForCondition().until(driver -> driver.getCurrentUrl().contains("/incidents"));
        pageTitle.waitUntilVisible();
    }

    public boolean isPageDisplayed() {
        return getDriver().getCurrentUrl().contains("/incidents")
                && pageTitle.isCurrentlyVisible();
    }

    public boolean isCreateButtonVisible() {
        try {
            return createIncidentButton.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCreateIncidentButton() {
        createIncidentButton.waitUntilClickable().click();
    }

    public boolean isCreateFormVisible() {
        try {
            return createFormCard.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterThreatId(String threatId) {
        threatIdField.waitUntilVisible();
        threatIdField.clear();
        threatIdField.sendKeys(threatId);
    }

    public void submitCreateForm() {
        submitCreateButton.waitUntilClickable().click();
    }

    public void cancelCreateForm() {
        cancelCreateButton.waitUntilClickable().click();
    }

    public void createIncidentFromThreat(String threatId) {
        clickCreateIncidentButton();
        enterThreatId(threatId);
        submitCreateForm();
    }

    public String getSuccessMessage() {
        return successAlert.waitUntilVisible().getText();
    }

    public String getErrorMessage() {
        return errorAlert.waitUntilVisible().getText();
    }

    public boolean isSuccessAlertVisible() {
        try {
            return successAlert.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorAlertVisible() {
        try {
            return errorAlert.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public int getVisibleIncidentCount() {
        List<WebElement> rows = getDriver().findElements(By.cssSelector(".data-table tbody tr"));
        long count = rows.stream().filter(row -> {
            List<WebElement> cells = row.findElements(By.cssSelector("td"));
            return cells.size() > 1; 
        }).count();
        return (int) count;
    }

    public boolean isIncidentWithTitleVisible(String title) {
        List<WebElement> rows = getDriver().findElements(
                By.cssSelector(".data-table tbody tr td:nth-child(2)"));
        return rows.stream().anyMatch(td -> td.getText().contains(title));
    }

    public boolean isEmptyStateVisible() {
        try {
            return emptyState.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFormHintText() {
        return formHintText.waitUntilVisible().getText();
    }

    public void filterByStatus(String status) {
        statusFilterSelect.waitUntilVisible().selectByValue(status);
    }

    public void filterBySeverity(String severity) {
        severityFilterSelect.waitUntilVisible().selectByValue(severity);
    }

    public void applyFilters() {
        filterButton.waitUntilClickable().click();
    }

    public void clearFilters() {
        clearFiltersButton.waitUntilClickable().click();
    }

    public int getTotalIncidentCount() {
        try {
            String text = incidentCountLabel.getText(); // "Incidentes (N)"
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }
}
