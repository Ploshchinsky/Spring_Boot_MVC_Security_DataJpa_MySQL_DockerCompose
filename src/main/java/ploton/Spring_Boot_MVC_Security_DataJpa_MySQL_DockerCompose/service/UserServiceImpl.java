package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.User;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.*;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserAuthDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model.UserRoleDto;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository.RoleRepository;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository.UserRepository;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.EntityUtils;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.JwtUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username)
            throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role ->
                                new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
    }

    @Override
    public User save(User user) {
        validate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        intiRoleByDefault(user);
        try {
            findByUsername(user.getUsername());
            throw new UserAlreadyExistsException("User Already Exists - " + user.getUsername());
        } catch (UsernameNotFoundException e) {
            return userRepository.save(user);
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserIdNotFoundException("User ID not found - " + id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found - " + username));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
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

    public Role addRole(UserRoleDto userRoleDto) {
        Optional<User> user = userRepository.findByUsername(userRoleDto.getUsername());
        Optional<Role> role = roleRepository.findByName(userRoleDto.getRole());

        if (user.isPresent() && role.isPresent()) {
            List<Role> roles = user.get().getRoles();
            roles.add(role.get());
            Map<String, Object> updates = Map.of("roles", roles);
            updateById(user.get().getId(), updates);
            return role.get();
        }
        return null;
    }

    @Transactional
    @Override
    public String getToken(UserAuthDto userAuthDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthDto.getUsername(), userAuthDto.getPassword()
        ));
        UserDetails userDetails = loadUserByUsername(userAuthDto.getUsername());
        return jwtUtils.generateToken(userDetails);
    }

    //Utils
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

    private void intiRoleByDefault(User user) {
        String defaultRole = "ROLE_USER";
        user.setRoles(List.of(roleRepository.findByName(defaultRole).orElseThrow(
                () -> new NoSuchElementException("Role not found - " + defaultRole)
        )));
    }

}
