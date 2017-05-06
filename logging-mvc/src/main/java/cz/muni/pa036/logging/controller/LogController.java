package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.utils.DBLogLevel;
import cz.muni.pa036.logging.utils.LogDestination;
import cz.muni.pa036.logging.service.DBSystemLogAPI;
import cz.muni.pa036.logging.service.LoggerService;
import cz.muni.pa036.logging.utils.LoggerModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Kamil Triscik.
 */
@Controller
@RequestMapping(LogController.URL)
public class LogController extends BaseController {

    final public static String URL = "/logging";

    Logger logger = org.slf4j.LoggerFactory.getLogger(LogController.class);


    @Autowired
    private DBSystemLogAPI dbSystemLogAPI;

    @Autowired
    private LoggerService loggerService;

    @ModelAttribute("logDestinations")
    public LogDestination[] getLogDestinations() {
        return LogDestination.values();
    }

    @ModelAttribute("logLevels")
    public DBLogLevel[] getLogLevels() {
        return DBLogLevel.values();
    }

    @RequestMapping
    public String renderList(Model model) throws Exception {
        logger.info("Logging management page loaded");
        model.addAttribute("logger", loggerService.getLoggerModel());
        model.addAttribute("dests", LogDestination.values());
        model.addAttribute("lovels", DBLogLevel.values());
        return "logging.page";
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Object apply(@ModelAttribute("logger") LoggerModel loggerModel, Model model) throws Exception {
        logger.debug("apply button clicked:" + loggerModel.toString());
        loggerService.updateLoggingOptions(loggerModel);
        model.addAttribute("logger", loggerModel);
        return "logging.page";
    }

}
