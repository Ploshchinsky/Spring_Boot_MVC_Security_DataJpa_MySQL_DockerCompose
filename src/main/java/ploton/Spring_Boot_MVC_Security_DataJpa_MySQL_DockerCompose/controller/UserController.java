package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.UserService;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.EntityUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable("username") String username) {
        UserDto dto = EntityUtils.convertEntityToDto(userService.findByUsername(username), new UserDto());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<UserDto> dtoList = userService.findAll().stream()
                .map(user -> EntityUtils.convertEntityToDto(user, new UserDto()))
                .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> updates) {
        UserDto dto = EntityUtils.convertEntityToDto(userService.updateById(id, updates), new UserDto());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteById(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = Optional.of(userService.findByUsername(username));
        UserDto userDto = EntityUtils.convertEntityToDto(user.get(), new UserDto());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
