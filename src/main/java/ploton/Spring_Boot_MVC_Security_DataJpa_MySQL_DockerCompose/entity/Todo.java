package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Boolean isComplete;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private LocalDateTime creationTime;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @PrePersist
    protected void onCreate() {
        this.creationTime = LocalDateTime.now();
        this.isComplete = false;
    }
}
