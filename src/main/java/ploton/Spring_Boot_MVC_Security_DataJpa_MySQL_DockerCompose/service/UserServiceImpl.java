package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.BadUpdateFieldException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.EntityValidateException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.UserAlreadyExistsException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.UserIdNotFoundException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository.UserRepository;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        validate(user);
        if (findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User with username - " + user.getUsername() + " already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserIdNotFoundException("User ID not found - " + id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserIdNotFoundException("Username not found - " + username));
    }

    @Override
    public User updateById(Long id, Map<String, Object> updates) {
        User user = findById(id);
        EntityUtils.updateEntity(user, updates);
        return userRepository.save(user);
    }

    @Override
    public Long deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return id;
        }
        return -1l;
    }

    @Override
    public void validate(User user) {
        List<String> errors = new ArrayList<>();
        if (!user.getUsername().matches("^[a-zA-Z\\s]+$")) {
            errors.add("Wrong username. Use only letters [a-z&A-Z]");
        }
        if (!user.getPassword().matches("^[\\w]{4,12}$")) {
            errors.add("Wrong password. " +
                    "Don't use 'space' and special character ('!', ',', '.' etc). Length from 4 to 12");
        }
        if (!user.getEmail().matches("^([\\w._-]+)(@)([\\w-]+\\.)([a-zA-Z]{2,4})$")) {
            errors.add("Wrong email.");
        }

        if (!errors.isEmpty()) {
            throw new EntityValidateException("User Validate Exception: " + errors);
        }
    }

}
