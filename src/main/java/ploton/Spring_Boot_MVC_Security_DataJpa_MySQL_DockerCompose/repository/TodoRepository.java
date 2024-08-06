package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Todo;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByDescription(String description);
}
