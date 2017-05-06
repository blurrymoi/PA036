package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.dto.SportsmanDTO;
import cz.muni.pa036.logging.facade.InvitationFacade;
import cz.muni.pa036.logging.facade.SportsmanFacade;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Kamil Triscik
 */
@Controller
public class InvitationController extends BaseController {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @Autowired
    private InvitationFacade invitationFacade;

    @Autowired
    private SportsmanFacade sportsmanFacade;

    @RequestMapping(value = "/events/{id}/invite", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity invite(@PathVariable Long id, @RequestParam("email") String email) {

        CRUD_LOGGER.logFindBy("email", email);
        SportsmanDTO byEmail = sportsmanFacade.getByEmail(email);

        CRUD_LOGGER.logCustom(" > Trying to invite [" + id + ", " + byEmail.getId() + "]");
        invitationFacade.invite(id, byEmail.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

}
