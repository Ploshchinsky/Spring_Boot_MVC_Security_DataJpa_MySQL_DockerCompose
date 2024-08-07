package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestUserEntity {
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
}
