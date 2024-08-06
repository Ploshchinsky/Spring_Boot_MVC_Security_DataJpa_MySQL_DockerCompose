package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
