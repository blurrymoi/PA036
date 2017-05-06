package cz.muni.pa036.logging.exceptions;

/**
 * This extends RuntimeException, which means all of its subclasses will be unchecked exceptions.
 * Usually, this is VERY BAD idea, but there are two good reasons to justify this choice:
 * 1) Unchecked exceptions might be used when client can't recover, which covers the CRUD operation IMHO
 * 2) I don't have a time, nor the necessary willpower to write hundreds of "throws X exception" in the methods
 */
public class BaseCRUDException extends RuntimeException {

    public BaseCRUDException(String message, Throwable cause) {
        super(message, cause);
    }
}
