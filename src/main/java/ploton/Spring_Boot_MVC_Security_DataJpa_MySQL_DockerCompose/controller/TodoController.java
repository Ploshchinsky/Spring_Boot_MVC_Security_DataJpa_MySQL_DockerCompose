package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Todo;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.TodoService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/new")
    public ResponseEntity<?> save(@RequestBody Todo entity) {
        return new ResponseEntity<>(todoService.save(entity), HttpStatus.ACCEPTED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.deleteById(id), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(todoService.updateById(id, updates), HttpStatus.OK);
    }
}
