package cz.muni.pa036.logging.controller.advisers;

import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.helper.ActionLogger;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class ControllerAdviser {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @ExceptionHandler(CreateException.class)
    public void logCreationException(CreateException createException) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder()
                .CREATE()
                .causeObject(createException.getCauseObject())
                .build();
        CRUD_LOGGER.logOops(actionLogger, createException);
    }

    @ExceptionHandler(UpdateException.class)
    public void logUpdateException(UpdateException updateException) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder()
                .UPDATE()
                .causeObject(updateException.getCauseObject())
                .build();
        CRUD_LOGGER.logOops(actionLogger, updateException);
    }

    @ExceptionHandler(FindByException.class)
    public void logFindByException(FindByException findByException) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder()
                .DELETE()
                .byValues(findByException.getByValues())
                .build();
        CRUD_LOGGER.logOops(actionLogger, findByException);
    }

    @ExceptionHandler(DeleteException.class)
    public void logDeleteException(DeleteException deleteException) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().DELETE()
                .causeObject(deleteException.getCauseObject())
                .build();
        CRUD_LOGGER.logOops(actionLogger, deleteException);
    }

    // general logging for other exceptions (e.g. thrown and not caught ones)
    @ExceptionHandler(Throwable.class)
    public void logException(Throwable throwable) {
        ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().CUSTOM("[UNKNOWN ACTION]").build();
        CRUD_LOGGER.logOops(actionLogger, (Exception) throwable);
    }
}
