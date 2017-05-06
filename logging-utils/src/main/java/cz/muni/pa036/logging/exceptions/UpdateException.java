package cz.muni.pa036.logging.exceptions;

public class UpdateException extends HasCauseObjectException {

    public UpdateException(String message, Throwable cause, Object payload) {
        super(message, cause, payload);
    }
}
