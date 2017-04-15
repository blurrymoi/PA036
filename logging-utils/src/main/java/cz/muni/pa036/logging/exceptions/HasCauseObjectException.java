package cz.muni.pa036.logging.exceptions;

public class HasCauseObjectException extends BaseCRUDException {

    private Object causeObject;

    public HasCauseObjectException(String message, Throwable cause, Object causeObject) {
        super(message, cause);
        this.causeObject = causeObject;
    }

    public Object getCauseObject() {
        return causeObject;
    }
}
