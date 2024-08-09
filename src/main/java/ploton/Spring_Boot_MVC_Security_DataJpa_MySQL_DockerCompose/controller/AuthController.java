package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserAuthDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service.UserService;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.JwtUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> auth(UserAuthDto userAuth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuth.getUsername(), userAuth.getPassword()
        ));
        UserDetails userDetails = userService.loadUserByUsername(userAuth.getUsername());
        String token = JwtUtils.generateToken(userDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
