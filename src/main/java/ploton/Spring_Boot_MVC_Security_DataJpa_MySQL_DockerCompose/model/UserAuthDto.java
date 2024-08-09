package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.model;

import lombok.Data;

@Data
public class UserAuthDto {
    private String username;
    private String password;
}
