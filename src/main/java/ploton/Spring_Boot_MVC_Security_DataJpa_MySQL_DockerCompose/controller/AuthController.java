package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserAuthDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserRoleDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.RoleService;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.UserService;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.EntityUtils;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.JwtUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> auth(UserAuthDto userAuth) {
        return new ResponseEntity<>(userService.getToken(userAuth), HttpStatus.OK);
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registration(@RequestBody User user) {
        UserDto dto = EntityUtils.convertEntityToDto(userService.save(user), new UserDto());
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @PostMapping("/add-role")
    public ResponseEntity<?> addRoleByUsername(@RequestBody UserRoleDto userRoleDto) {
        return new ResponseEntity<>(userService.addRole(userRoleDto), HttpStatus.ACCEPTED);
    }
}
