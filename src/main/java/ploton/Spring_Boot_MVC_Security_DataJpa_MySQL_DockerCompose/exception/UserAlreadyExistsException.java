package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
