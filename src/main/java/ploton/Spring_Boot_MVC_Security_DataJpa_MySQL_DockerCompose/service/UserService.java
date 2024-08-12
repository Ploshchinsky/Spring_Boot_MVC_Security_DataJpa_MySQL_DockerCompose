package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.UserIdNotFoundException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.UsernameNotFoundException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserAuthDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserRoleDto;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    User save(User user);

    User findById(Long id) throws UserIdNotFoundException;

    User findByUsername(String username) throws UsernameNotFoundException;

    List<User> findAll();

    User updateById(Long id, Map<String, Object> updates);

    Long deleteById(Long id);

    Role addRole(UserRoleDto userRoleDto);

    String getToken(UserAuthDto userAuthDto);

    void validate(User user);
}
