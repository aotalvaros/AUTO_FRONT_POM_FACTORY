package com.cyberguard.automation.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.cyberguard.automation.TestData;

import static org.assertj.core.api.Assertions.assertThat;

public class PomHooks {

    private static final String BACKEND_URL = "http://localhost:3000";
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Before("@requiere-datos-previos")
    public void createCriticalThreatViaApi() throws Exception {
        String loginBody = "{\"username\":\"" + TestData.ADMIN_EMAIL + "\","
                + "\"password\":\"" + TestData.ADMIN_PASSWORD + "\"}";

        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(BACKEND_URL + "/api/auth/login"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(loginBody))
                .build();

        HttpResponse<String> loginResponse = HTTP.send(loginRequest,
                HttpResponse.BodyHandlers.ofString());

        assertThat(loginResponse.statusCode())
                .as("El login de admin debe retornar 200. Verificar: backend en :3000, " +
                    "credenciales válidas en Firebase. Response: " + loginResponse.body())
                .isEqualTo(200);

        String adminToken = extractJsonField(loginResponse.body(), "token");
        assertThat(adminToken)
                .as("La respuesta de login debe contener un campo 'token'")
                .isNotEmpty();

        ScenarioContext.get().setAdminApiToken(adminToken);

        String threatBody = "{"
                + "\"type\":\"malware\","
                + "\"severity\":\"critical\","
                + "\"sourceIp\":\"10.10.10.1\","
                + "\"description\":\"Amenaza crítica creada por POM automation suite\""
                + "}";

        HttpRequest threatRequest = HttpRequest.newBuilder()
                .uri(URI.create(BACKEND_URL + "/api/threats"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .POST(HttpRequest.BodyPublishers.ofString(threatBody))
                .build();

        HttpResponse<String> threatResponse = HTTP.send(threatRequest,
                HttpResponse.BodyHandlers.ofString());

        assertThat(threatResponse.statusCode())
                .as("La creación de amenaza debe retornar 201. " +
                    "Response: " + threatResponse.body())
                .isEqualTo(201);

        String threatId = extractJsonField(threatResponse.body(), "threatId");
        assertThat(threatId)
                .as("La respuesta de creación de amenaza debe contener un campo 'threatId'. " +
                    "Response: " + threatResponse.body())
                .isNotEmpty();

        ScenarioContext.get().setCriticalThreatId(threatId);
    }

    @After
    public void cleanupContext() {
        ScenarioContext.reset();
    }

    private String extractJsonField(String json, String fieldName) {
        String key = "\"" + fieldName + "\":\"";
        int start = json.indexOf(key);
        if (start == -1) {
            return "";
        }
        start += key.length();
        int end = json.indexOf("\"", start);
        if (end == -1) {
            return "";
        }
        return json.substring(start, end);
    }
}
