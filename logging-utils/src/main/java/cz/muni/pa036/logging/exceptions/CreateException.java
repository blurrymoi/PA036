package cz.muni.pa036.logging.exceptions;

public class CreateException extends HasCauseObjectException {

    public CreateException(String message, Throwable cause, Object payload) {
        super(message, cause, payload);
    }
}
