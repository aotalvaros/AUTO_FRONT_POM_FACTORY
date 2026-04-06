package com.cyberguard.automation;

public class TestData {

    private static final String RUN_SUFFIX = String.valueOf(System.currentTimeMillis());

    public static final String NEW_USER_EMAIL = "pom.test." + RUN_SUFFIX + "@cyberguard.com";
    public static final String NEW_USER_FULL_NAME = "POM Test User";
    public static final String NEW_USER_USERNAME = "pom.test." + RUN_SUFFIX;
    public static final String NEW_USER_ROLE = "soc_analyst";

    public static final String ADMIN_EMAIL = "admin@cyberguard.com";
    public static final String ADMIN_PASSWORD = "AdminSofka123456";
    public static final String ADMIN_USERNAME = "admin";

    public static final String SOC_ANALYST_EMAIL = "soc@cyberguard.com";
    public static final String SOC_ANALYST_PASSWORD = "SocSofka123456";

    public static final String INCIDENT_HANDLER_EMAIL = "incident.handler@cyberguard.com";
    public static final String INCIDENT_HANDLER_PASSWORD = "HandlerSofka123456";

    public static final String DUPLICATE_EMAIL = "admin@cyberguard.com";

    public static final String NON_EXISTENT_THREAT_UUID = "00000000-0000-0000-0000-000000000000";

    private TestData() {}
}
