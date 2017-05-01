package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.dbsApi.DBLogLevel;
import cz.muni.pa036.logging.dbsApi.LogDestination;
import cz.muni.pa036.logging.service.DBSystemLogAPI;
import cz.muni.pa036.logging.util.LoggerModel;
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

    Logger logger = org.slf4j.LoggerFactory.getLogger(EventController.class);


    @Autowired
    private DBSystemLogAPI dbSystemLogAPI;

    @RequestMapping
    public String renderList(Model model) {
        logger.info("Logging management page loaded");
        model.addAttribute("logger", new LoggerModel());
        model.addAttribute("dests", LogDestination.values());
        model.addAttribute("lovels", DBLogLevel.values());
        return "logging.page";
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Object apply(@ModelAttribute("logger") LoggerModel loggerModel, Model model) {
        loggerModel.setMinDuration(3);
        model.addAttribute("logger", loggerModel);
        return redirect(LogController.URL);
    }

}
