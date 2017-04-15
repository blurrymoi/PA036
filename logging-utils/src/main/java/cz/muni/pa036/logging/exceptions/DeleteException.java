package cz.muni.pa036.logging.exceptions;

public class DeleteException extends HasCauseObjectException {

    public DeleteException(String message, Throwable cause, Object payload) {
        super(message, cause, payload);
    }
}
