package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Column(name = "is_complete")
    private boolean isComplete;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private LocalDateTime creationTime;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @PrePersist
    private void onCreate() {
        this.creationTime = LocalDateTime.now();
    }
}
