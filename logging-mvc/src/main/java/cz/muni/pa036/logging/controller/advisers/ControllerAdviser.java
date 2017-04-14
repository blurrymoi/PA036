package cz.muni.pa036.logging.controller.advisers;

import cz.muni.pa036.logging.helper.ActionLogger;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviser {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    // CreateException si vytvorime a budeme ju vyhadzovat na servise ak sa nieco poserie
    @ExceptionHandler(CreateException.class)
    public void logCreationException(CreateException createException) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().CREATE().build();
        CRUD_LOGGER.logOops(actionLogger, createException);
    }

    // UpdateException si vytvorime
    @ExceptionHandler(UpdateException.class)
    public void logUpdateException(UpdateException updateException) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().UPDATE().build();
        CRUD_LOGGER.logOops(actionLogger, updateException);
    }

    // DeleteException si vytvorime
    @ExceptionHandler(DeleteException.class)
    public void logDeleteException(DeleteException deleteException) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().DELETE().build();
        CRUD_LOGGER.logOops(actionLogger, deleteException);
    }

    // general pre ostatne
    @ExceptionHandler(Throwable.class)
    public void logException(Throwable throwable) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().CUSTOM("[UNKNOWN ACTION]").build();
        CRUD_LOGGER.logOops(actionLogger, (Exception) throwable);
    }
}
