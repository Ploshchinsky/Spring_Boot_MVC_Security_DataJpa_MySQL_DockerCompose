package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserRoleDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository.RoleRepository;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.EntityUtils;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.RoleService;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/reg")
    public ResponseEntity<?> registration(@RequestBody User user) {
        UserDto dto = EntityUtils.convertEntityToDto(userService.save(user), new UserDto());
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
//        UserDto dto = EntityUtils.convertEntityToDto(userService.findById(id), new UserDto());
//        return new ResponseEntity<>(dto, HttpStatus.OK);
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

    @PostMapping("/add-role")
    public ResponseEntity<?> addRoleByUsername(@RequestBody UserRoleDto userRoleDto) {
        User user = userService.findByUsername(userRoleDto.getUsername());
        Role role = roleService.findByName(userRoleDto.getRole());
        if (user != null && role != null) {
            List<Role> roles = user.getRoles();
            roles.add(role);
            Map<String, Object> updates = Map.of("roles", roles);
            userService.updateById(user.getId(), updates);
        }
        return new ResponseEntity<>(role, HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication) {
        return new ResponseEntity<>(authentication, HttpStatus.OK);
    }
}
