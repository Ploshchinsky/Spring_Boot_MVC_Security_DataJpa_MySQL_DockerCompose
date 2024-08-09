package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception.EntityValidateException;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.repository.RoleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role entity) {
        validate(entity);
        return roleRepository.save(entity);
    }


    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Role ID - " + id)
        );
    }

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(
                () -> new NoSuchElementException("Role Name - " + roleName)
        );
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Long deleteById(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return id;
        }
        return -1l;
    }

    private void validate(Role entity) {
        if (!entity.getName().matches("^(ROLE){1,}(_[A-Z]+)$")) {
            throw new EntityValidateException("Role Validate Exception. Wrong Role Name - " + entity.getName());
        }
    }
}
