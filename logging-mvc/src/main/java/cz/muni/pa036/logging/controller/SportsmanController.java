package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.dto.InvitationUpdateDTO;
import cz.muni.pa036.logging.facade.InvitationFacade;
import cz.muni.pa036.logging.service.BeanMappingService;
import org.springframework.security.core.Authentication;
import cz.muni.pa036.logging.dto.ResultDTO;
import cz.muni.pa036.logging.dto.SportsmanDTO;
import cz.muni.pa036.logging.dto.InvitationDTO;
import cz.muni.pa036.logging.facade.ResultFacade;
import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.facade.SportsmanFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cz.muni.pa036.logging.helper.CRUDLogger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronika Aksamitova
 */
@Controller
public class SportsmanController extends BaseController {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private SportsmanFacade sportsmanFacade;

    @Inject
    private InvitationFacade invitationFacade;

    @Autowired
    private ResultFacade resultFacade;

    @RequestMapping("/my-account")
    public String myAccount(Model model) {
        String email;
        SportsmanDTO sportsman;
        List<ResultDTO> results;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            email = auth.getName();
            CRUD_LOGGER.logFindBy("email", email);
            sportsman = sportsmanFacade.getByEmail(email);
            results = resultFacade.findBySportsman(sportsman);

        }
        catch(Exception ex){
            return "index";
        }
        model.addAttribute("name", sportsman.getName());
        model.addAttribute("surname", sportsman.getSurname());
        model.addAttribute("email", sportsman.getEmail());
        model.addAttribute("birthdate", sportsman.getBirthDate());
        CRUD_LOGGER.logFindBy("invitee", sportsman);
        model.addAttribute("invitations", invitationFacade.findByInvitee(sportsman));
        CRUD_LOGGER.logFindBy("participant", sportsman);
        model.addAttribute("events", eventFacade.findByParticipant(sportsman.getId()));
        model.addAttribute("results", results);
        return "user.detail";
    }


    @RequestMapping("/accept/{id}")
    public Object accept(Authentication authentication, @PathVariable Long id) {
        InvitationDTO  invitation;
        try{
            CRUD_LOGGER.logFindBy("ID", id);
            invitation = invitationFacade.findById(id);
            if(!authentication.getName().equals(invitation.getInvitee().getEmail())) {
                return "error.403";
            }
            invitationFacade.accept(beanMappingService.mapTo(invitation, InvitationUpdateDTO.class));
        }
        catch(Exception ex){

            return "error.403";
        }
        return redirect("/events/" + invitation.getEvent().getId() + "?accepted");
    }

    @RequestMapping("/decline/{id}")
    public Object decline(@PathVariable Long id) {
        try{
            CRUD_LOGGER.logFindBy("ID", id);
            InvitationDTO  invitation = invitationFacade.findById(id);
            invitationFacade.decline(beanMappingService.mapTo(invitation, InvitationUpdateDTO.class));
        }
        catch(Exception ex){

            return "error.403";
        }
        return redirect("/my-account?decline");
    }


}
