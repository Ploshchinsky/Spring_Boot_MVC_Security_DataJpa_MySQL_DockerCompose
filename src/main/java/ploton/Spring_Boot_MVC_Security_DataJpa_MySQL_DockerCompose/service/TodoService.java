package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Todo;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;

import java.util.Map;

public interface TodoService {
    Todo save(Todo todo);

    Todo findById(Long id);

    Todo findByDescription(String description);

    Todo updateById(Long id, Map<String, Object> updates);

    Long deleteById(Long id);
}
