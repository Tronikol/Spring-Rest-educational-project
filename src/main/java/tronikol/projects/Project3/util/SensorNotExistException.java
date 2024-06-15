package tronikol.projects.Project3.util;

public class SensorNotExistException extends RuntimeException{
    public SensorNotExistException(String message) {
        super(message);
    }
}
