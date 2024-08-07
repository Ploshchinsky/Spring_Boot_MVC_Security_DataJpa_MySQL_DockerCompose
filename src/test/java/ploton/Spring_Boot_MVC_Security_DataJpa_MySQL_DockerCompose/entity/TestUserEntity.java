package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.BadUpdateFieldException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestUserEntity {
    User user;
    ObjectMapper mapper;
    Map<String, Object> updates;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1l);
        user.setUsername("adminName");
        user.setPassword("adminPass");
        user.setEmail("admin@gmail.com");

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        updates = new HashMap<>();
        updates.put("username", "updatedName");
        updates.put("email", "updatedEmail@ya.ru");
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
        String expected = "{\"id\":1,\"username\":\"updatedName\",\"password\":\"adminPass\"," +
                "\"email\":\"updatedEmail@ya.ru\",\"todoList\":null," +
                "\"registrationDate\":null,\"lastVisit\":null}";

        updateEntity(user, updates);
        String actual = mapper.writeValueAsString(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateFieldsWithReflection_IncorrectFields_BadUpdateFieldException() throws Exception {
        updates.put("WRONG_FIELD", 666);
        Assertions.assertThrows(BadUpdateFieldException.class, () -> updateEntity(user, updates));
    }

    private void updateEntity(User entity, Map<String, Object> updates) {
        Class<?> clazz = entity.getClass();

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String updateFiled = entry.getKey();
            Object updateValue = entry.getValue();

            try {
                Field field = clazz.getDeclaredField(updateFiled);
                field.setAccessible(true);
                field.set(entity, updateValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new BadUpdateFieldException("No Such Field - " + updateFiled + " ." + e.getMessage());
            }
        }
    }
}
