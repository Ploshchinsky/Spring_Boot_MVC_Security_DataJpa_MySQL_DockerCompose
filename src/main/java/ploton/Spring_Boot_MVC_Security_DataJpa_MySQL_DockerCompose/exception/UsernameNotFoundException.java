package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
