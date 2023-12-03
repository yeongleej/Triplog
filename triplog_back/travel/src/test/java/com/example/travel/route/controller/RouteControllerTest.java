package com.example.travel.route.controller;

import com.example.travel.route.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
        properties = {
                "spring.config.location=classpath:application.properties"
        },
        classes = {RouteController.class}
)
@Slf4j
@ComponentScan(basePackages = {"com.example"})
@Transactional
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RouteService routeService;
    @Test
    void findAllRoute() throws Exception {
        mockMvc.perform(get("/route"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void findRouteById() {
    }

    @Test
    void findRouteByUser() {
    }

    @Test
    void findRouteBySpot() {
    }

    @Test
    void findRouteByKeywords() {
    }

    @Test
    void addRoute() {
    }

    @Test
    void routeModify() {
    }

    @Test
    void routeDelete() {
    }
}