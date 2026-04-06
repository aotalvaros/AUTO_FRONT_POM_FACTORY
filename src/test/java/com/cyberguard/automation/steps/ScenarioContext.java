package com.cyberguard.automation.steps;

public class ScenarioContext {

    private static final ThreadLocal<ScenarioContext> INSTANCE =
            ThreadLocal.withInitial(ScenarioContext::new);

    private String criticalThreatId;
    private String adminApiToken;

    private ScenarioContext() {}

    public static ScenarioContext get() {
        return INSTANCE.get();
    }

    public static void reset() {
        INSTANCE.remove();
    }

    public String getCriticalThreatId() {
        return criticalThreatId;
    }

    public void setCriticalThreatId(String criticalThreatId) {
        this.criticalThreatId = criticalThreatId;
    }

    public String getAdminApiToken() {
        return adminApiToken;
    }

    public void setAdminApiToken(String adminApiToken) {
        this.adminApiToken = adminApiToken;
    }
}
