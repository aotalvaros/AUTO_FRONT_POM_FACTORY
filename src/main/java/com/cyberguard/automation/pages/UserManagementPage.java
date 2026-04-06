package com.cyberguard.automation.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import java.util.List;

@DefaultUrl("/users")
public class UserManagementPage extends PageObject {

    @FindBy(css = "h1")
    private WebElementFacade pageTitle;

    @FindBy(css = ".toolbar button.btn-primary")
    private WebElementFacade newUserButton;

    @FindBy(css = ".toolbar h2")
    private WebElementFacade userCountLabel;

    @FindBy(css = ".form-card")
    private WebElementFacade createFormCard;

    @FindBy(css = "input[placeholder='usuario@example.com']")
    private WebElementFacade emailField;

    @FindBy(css = "input[placeholder='Ana María Torres']")
    private WebElementFacade fullNameField;

    @FindBy(css = "input[placeholder='ana.torres']")
    private WebElementFacade usernameField;

    @FindBy(css = ".form-card select")
    private WebElementFacade roleSelect;

    @FindBy(css = ".form-actions button.btn-primary")
    private WebElementFacade submitCreateButton;

    @FindBy(css = ".form-actions button.btn-secondary")
    private WebElementFacade cancelCreateButton;

    @FindBy(css = ".alert.alert-success")
    private WebElementFacade successAlert;

    @FindBy(css = ".alert.alert-error")
    private WebElementFacade errorAlert;

    @FindBy(css = ".data-table tbody tr")
    private List<WebElementFacade> userRows;

    @FindBy(css = ".empty-state")
    private WebElementFacade emptyState;

    public void waitForPage() {
        waitForCondition().until(driver -> driver.getCurrentUrl().contains("/users"));
        pageTitle.waitUntilVisible();
        waitForCondition().until(driver ->
            !driver.findElements(By.cssSelector(".table-container")).isEmpty()
        );
    }

    public void waitForTableReload() {
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        waitForCondition().until(driver ->
            !driver.findElements(By.cssSelector(".table-container")).isEmpty()
        );
    }

    public boolean isPageDisplayed() {
        return getDriver().getCurrentUrl().contains("/users")
                && pageTitle.isCurrentlyVisible();
    }

    public void clickNewUserButton() {
        newUserButton.waitUntilClickable().click();
    }

    public boolean isCreateFormVisible() {
        return createFormCard.isCurrentlyVisible();
    }

    public void fillEmail(String email) {
        emailField.waitUntilVisible();
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void fillFullName(String fullName) {
        fullNameField.waitUntilVisible();
        fullNameField.clear();
        fullNameField.sendKeys(fullName);
    }

    public void fillUsername(String username) {
        usernameField.waitUntilVisible();
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void selectRole(String roleValue) {
        roleSelect.waitUntilVisible().selectByValue(roleValue);
    }

    public void submitCreateForm() {
        submitCreateButton.waitUntilClickable().click();
    }

    public void cancelCreateForm() {
        cancelCreateButton.waitUntilClickable().click();
    }

    public void fillAndSubmitCreateForm(String email, String fullName, String username, String role) {
        fillEmail(email);
        fillFullName(fullName);
        fillUsername(username);
        selectRole(role);
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

    public boolean isUserInTable(String username) {
        List<WebElement> rows = getDriver().findElements(By.cssSelector(".data-table tbody tr td:first-child"));
        return rows.stream().anyMatch(td -> td.getText().equalsIgnoreCase(username));
    }

    public String getUserStatusBadge(String username) {
        List<WebElement> rows = getDriver().findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            String usernameCell = row.findElement(By.cssSelector("td:first-child")).getText();
            if (usernameCell.equalsIgnoreCase(username)) {
                WebElement badge = row.findElement(By.cssSelector("td .badge.badge-active, td .badge.badge-inactive"));
                return badge.getText().trim();
            }
        }
        throw new RuntimeException("Usuario no encontrado en tabla: " + username);
    }

    public boolean hasActionButtonsForUser(String username) {
        List<WebElement> rows = getDriver().findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            String usernameCell = row.findElement(By.cssSelector("td:first-child")).getText();
            if (usernameCell.equalsIgnoreCase(username)) {
                List<WebElement> editBtns = row.findElements(By.cssSelector(".btn-sm.btn-edit"));
                return !editBtns.isEmpty();
            }
        }
        return false;
    }

    public boolean hasTuCuentaBadge(String username) {
        List<WebElement> rows = getDriver().findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            String usernameCell = row.findElement(By.cssSelector("td:first-child")).getText();
            if (usernameCell.equalsIgnoreCase(username)) {
                List<WebElement> badge = row.findElements(By.cssSelector(".badge.badge-info"));
                return !badge.isEmpty();
            }
        }
        return false;
    }

    public void clickToggleStatusForUser(String username) {
        List<WebElement> rows = getDriver().findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            String usernameCell = row.findElement(By.cssSelector("td:first-child")).getText();
            if (usernameCell.equalsIgnoreCase(username)) {
                WebElement toggleBtn = row.findElement(
                        By.cssSelector(".btn-sm.btn-deactivate, .btn-sm.btn-activate"));
                toggleBtn.click();

                try {
                    Alert confirm = getDriver().switchTo().alert();
                    confirm.accept();
                } catch (NoAlertPresentException ignored) {

                }
                return;
            }
        }
        throw new RuntimeException("No se encontró botón de toggle para usuario: " + username);
    }

    public void clickEditForUser(String username) {
        List<WebElement> rows = getDriver().findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            String usernameCell = row.findElement(By.cssSelector("td:first-child")).getText();
            if (usernameCell.equalsIgnoreCase(username)) {
                WebElement editBtn = row.findElement(By.cssSelector(".btn-sm.btn-edit"));
                editBtn.click();
                return;
            }
        }
        throw new RuntimeException("No se encontró botón Editar para usuario: " + username);
    }

    public int getUserCount() {
        try {
            String text = userCountLabel.getText(); // "Usuarios (N)"
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }
}
