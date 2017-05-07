package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.dbsApi.DBSApi;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.event.Level;


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

    @Autowired
    private DBSApi dbsApi;

    @ModelAttribute("logDestinations")
    public LogDestination[] getLogDestinations() {
        return LogDestination.values();
    }

    @ModelAttribute("logLevels")
    public DBLogLevel[] getLogLevels() {
        return DBLogLevel.values();
    }

    @ModelAttribute("logLevels2")
    public Level[] getLogLevels2() {
        return Level.values();
    }

    @RequestMapping
    public String renderList(Model model) throws Exception {
        logger.info("Logging management page loaded");
        model.addAttribute("logger", loggerService.getLoggerModel());
        model.addAttribute("dests", LogDestination.values());
        model.addAttribute("dblevels", DBLogLevel.values());
        model.addAttribute("levels", Level.values());
        return "logging.page";
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Object apply(@ModelAttribute("logger") LoggerModel loggerModel, Model model) throws Exception {
        logger.debug("apply button clicked:" + loggerModel.toString());
        loggerService.updateLoggingOptions(loggerModel);
        model.addAttribute("logger", loggerService.getLoggerModel());
        return "logging.page";
    }

    @RequestMapping(value = "/longrun/{time}", method = RequestMethod.GET)
    public Object longrun(@PathVariable("time") Long time, Model model) throws Exception {
        logger.debug("Running select query with timeout " + time + "s.");
        dbsApi.runQuery("Select * from event,pg_sleep(" + time.toString() + ");");
        model.addAttribute("logger", loggerService.getLoggerModel());
        return "logging.page";
    }

}
