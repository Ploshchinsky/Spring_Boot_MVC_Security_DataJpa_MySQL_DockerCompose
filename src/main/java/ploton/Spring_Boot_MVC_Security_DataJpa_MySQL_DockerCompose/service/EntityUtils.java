package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import org.hibernate.sql.Update;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Todo;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.BadUpdateFieldException;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityUtils {

    public static <T> void updateEntity(T entity, Map<String, Object> updates) {
        Class clazz = entity.getClass();

        for (Map.Entry<String, Object> update : updates.entrySet()) {
            String fieldName = update.getKey();
            Object fieldValue = update.getValue();

            try {
                Field updatedfield = clazz.getDeclaredField(fieldName);
                updatedfield.setAccessible(true);
                updatedfield.set(entity, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new BadUpdateFieldException("Wrong Field - " + fieldName + ". " + e.getMessage());
            }
        }
    }

}
