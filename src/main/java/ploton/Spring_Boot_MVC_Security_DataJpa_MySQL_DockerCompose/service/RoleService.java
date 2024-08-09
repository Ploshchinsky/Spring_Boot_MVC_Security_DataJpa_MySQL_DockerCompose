package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;

import java.util.List;

public interface RoleService {
    Role save(Role entity);

    Role findById(Long id);

    Role findByName(String roleName);

    List<Role> findAll();

    Long deleteById(Long id);
}
