package com.johnsonlovecode.USSDCreationApp.repository;


import com.johnsonlovecode.USSDCreationApp.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    User user;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }
}
