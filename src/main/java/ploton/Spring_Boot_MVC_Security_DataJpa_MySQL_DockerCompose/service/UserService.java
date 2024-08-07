package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.UserIdNotFoundException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.UsernameNotFoundException;

import java.util.List;
import java.util.Map;

public interface UserService {
    User save(User user);

    User findById(Long id) throws UserIdNotFoundException;

    User findByUsername(String username) throws UsernameNotFoundException;

    List<User> findAll();

    User updateById(Long id, Map<String, Object> updates);

    Long deleteById(Long id);

    void validate(User user);
}
