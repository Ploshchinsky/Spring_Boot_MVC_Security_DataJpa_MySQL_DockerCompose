package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.exception;

public class BadUpdateFieldException extends RuntimeException{
    public BadUpdateFieldException(String message) {
        super(message);
    }
}
