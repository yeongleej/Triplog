package com.example.travel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        properties = {
                "spring.config.location=classpath:application.properties"
        }
)
@Slf4j
class TravelApplicationTests {

    @Test
    void contextLoads() {
    }

}
