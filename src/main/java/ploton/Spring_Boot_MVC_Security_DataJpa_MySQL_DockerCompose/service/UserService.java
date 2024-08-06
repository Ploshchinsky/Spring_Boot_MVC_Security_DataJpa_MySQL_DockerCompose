package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;

import java.util.Map;

public interface UserService {
    User save(User user);

    User findById(Long id);

    User findByUsername(String username);

    User updateById(Long id, Map<String, Object> updates);

    Long deleteById(Long id);
}
