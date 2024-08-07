package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TestUserEntity {
    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1l);
        user.setUsername("adminName");
        user.setPassword("adminPass");
        user.setEmail("admin@gmail.com");
        user.setRegistrationDate(LocalDateTime.now());
    }

    @Test
    public void testGetReflectionMethods_UserEntity_Methods() throws Exception {
        String[] expected = {"id", "username", "password", "email", "todoList", "registrationDate", "lastVisit"};

        Class<?> clazz = User.class;
        Field[] fields = clazz.getDeclaredFields();

        String[] actual = new String[fields.length];

        for (int i = 0; i < actual.length; i++) {
            actual[i] = fields[i].getName();
        }

        Assertions.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void testUpdateFieldsWithReflection_CorrectFields_UpdatedEntity() throws Exception {
        String expected = new ObjectMapper().writeValueAsString(user);
        System.out.println(expected);
    }
}
