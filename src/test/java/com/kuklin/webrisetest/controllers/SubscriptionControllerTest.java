package com.kuklin.webrisetest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuklin.webrisetest.models.Plan;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3");
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUpDatabase() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM subscriptions");
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.update(
                "INSERT INTO users (id, email) VALUES (?, ?)", 1L, "sap@mail.com");
    }

    @AfterAll
    void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Подписка пользователя")
    void subscribe() throws Exception {
        Plan plan = Plan.NETFLIX;

        this.mockMvc.perform(post("/api/v1/users/1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plan)))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.user.id").value(1L)
                );
    }

    @Test
    @DisplayName("Получение списка подписок пользователя")
    void getUserSubscriptionsById() throws Exception {
        jdbcTemplate.update(
                "INSERT INTO subscriptions (" +
                        "userId, serviceId, subscriptionStatus) " +
                        "VALUES (?, ?, ?)", 1L, 1L, "LASTS");

        this.mockMvc.perform(get("/api/v1/users/1/subscriptions"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].user.id").value(1L),
                        jsonPath("$[0].servicePlan.id").value(1L),
                        jsonPath("$[0].subscriptionStatus").value("LASTS")
                );

    }

    @Test
    @DisplayName("Остановка подписки")
    void stopSubscribeById() throws Exception {
        jdbcTemplate.update(
                "INSERT INTO subscriptions (" +
                        "id, userId, serviceId, subscriptionStatus) " +
                        "VALUES (?, ?, ?, ?)", 1L, 1L, 1L, "LASTS");

        this.mockMvc.perform(delete("/api/v1/subscriptions/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.user.id").value(1L),
                        jsonPath("$.subscriptionStatus").value("ENDED")
                );
    }
}
