package cz.muni.pa036.logging.exceptions;

import java.util.HashMap;
import java.util.Map;

public class FindByException extends BaseCRUDException {

    private Map<Object, Object> byValues;

    public FindByException(String message, Throwable cause, Map<Object, Object> byValue) {
        super(message, cause);
        this.byValues = byValue;
    }

    public FindByException(String message, Throwable cause, Object by, Object byValue) {
        super(message, cause);

        Map<Object, Object> byValues = new HashMap<>();
        byValues.put(by, byValue);
        this.byValues = byValues;
    }

    public Map<Object, Object> getByValues() {
        return byValues;
    }
}
