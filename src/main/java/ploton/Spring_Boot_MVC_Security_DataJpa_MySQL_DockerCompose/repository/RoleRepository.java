package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
