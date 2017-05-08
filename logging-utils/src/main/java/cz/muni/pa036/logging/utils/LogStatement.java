package cz.muni.pa036.logging.utils;

/**
 * @author Kamil Triscik.
 */
public enum LogStatement {

    NONE("NONE", "Off"),
    DDL("DDL", "Data definition statements"),
    MOD("MOD", "Data modifying statements"),
    ALL("ALL", "All statements");


    private final String type;
    private final String description;

    private LogStatement(String fullName, String desc) {
        this.type = fullName;
        this.description = desc;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }



}
