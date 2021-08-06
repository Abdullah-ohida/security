package com.example.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
//@Sql(scripts = "classpath:db/create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SecurityApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void applicationCanConnectToDatabaseTest(){
        assertThat(dataSource).isNotNull();

        try {
            Connection connection = dataSource.getConnection();
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("security");
        } catch (SQLException e) {
            log.info("Error occurred when connected to database --> {}", e.getMessage());
        }


    }

}
