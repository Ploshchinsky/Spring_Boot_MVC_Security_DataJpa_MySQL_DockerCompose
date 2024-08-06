package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception;

public class UserIdNotFoundException extends RuntimeException{
    public UserIdNotFoundException(String message) {
        super(message);
    }
}
