package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Todo;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.EntityValidateException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository.TodoRepository;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Override
    public Todo save(Todo todo) {
        validate(todo);
        return todoRepository.save(todo);
    }

    @Override
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Todo not found. ID - " + id)
        );
    }

    @Override
    public Todo findByDescription(String description) {
        return todoRepository.findByDescription(description).orElseThrow(
                () -> new NoSuchElementException("Todo not found. Description - " + description)
        );
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo updateById(Long id, Map<String, Object> updates) {
        Todo todo = findById(id);
        EntityUtils.updateEntity(todo, updates);
        return todoRepository.save(todo);
    }

    @Override
    public Long deleteById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return id;
        }
        return -1l;
    }

    private void validate(Todo todo) {
        List<String> errors = new ArrayList<>();

        if (!todo.getDescription().matches("^[\\w ]+$")) {
            errors.add("Wrong Description. Only chars [a-z, A-Z, 0-9]");
        }

        if (!errors.isEmpty()) {
            throw new EntityValidateException("Todo Validate Exception: " + errors);
        }
    }
}
