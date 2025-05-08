package com.kuklin.webrisetest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuklin.webrisetest.models.UserDto;
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


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

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
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.update(
                "INSERT INTO users (id, email) VALUES (?, ?)", 1L, "sap@mail.com");
    }

    @AfterAll
    void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUser() throws Exception {
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1");

        UserDto userDto = new UserDto()
                .setEmail("email@mail.com")
                .setFirstName("firstname")
                .setLastName("lastname");

        this.mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.email").value("email@mail.com")
                );
    }

    @Test
    @DisplayName("Получение пользователя")
    public void getUserById() throws Exception {
        this.mockMvc.perform(get("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value("1"),
                        jsonPath("$.email").value("sap@mail.com")
                );

    }

    @Test
    @DisplayName("Обновление пользователя")
    public void updateUser() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("email@mail.com");

        this.mockMvc.perform(patch("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                )
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value("1"),
                        jsonPath("$.email").value("email@mail.com")
                );
    }

    @Test
    @DisplayName("Обновление пользователя")
    public void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/api/v1/users/1"))
                .andExpectAll(status().isOk());
    }

}
