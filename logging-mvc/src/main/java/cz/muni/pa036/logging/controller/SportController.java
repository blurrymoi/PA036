package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.dto.SportUpdateDTO;
import cz.muni.pa036.logging.dto.SportCreateDTO;
import cz.muni.pa036.logging.dto.SportDTO;
import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.facade.SportFacade;
import cz.muni.pa036.logging.service.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cz.muni.pa036.logging.helper.CRUDLogger;

import javax.validation.Valid;

/**
 * @author Matej Majdis
 */
@Controller
@RequestMapping("/sports")
public class SportController extends BaseController {

	private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

	@Autowired
	private EventFacade eventFacade;

	@Autowired
	private SportFacade sportFacade;

	@Autowired
	private BeanMappingService beanMappingService;

	@RequestMapping
	public String renderList(Model model) {
		CRUD_LOGGER.logFindAll();
		model.addAttribute("sports", sportFacade.getAllSports());
		return "sport.list";
	}

	@RequestMapping("/{id}")
	public Object renderDetail(@PathVariable("id") Long id, Model model) {
		CRUD_LOGGER.logFindBy("ID", id);
		SportDTO sportDTO = sportFacade.getSportById(id);
		if (sportDTO == null) {
			return redirect("/sports");
		}
		model.addAttribute("sport", sportDTO);
		model.addAttribute("events", eventFacade.findBySport(sportDTO.getId()));
		return "sport.detail";
	}

	@RequestMapping("/create")
	public String renderCreate(Model model) {
		model.addAttribute("sport", new SportCreateDTO());
		return "sport.create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Object processCreate(@ModelAttribute("sport") @Valid SportCreateDTO sportCreateDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", true);
			return "sport.create";
		}
		CRUD_LOGGER.logCreate(sportCreateDTO);
		SportDTO sportDTO = sportFacade.create(sportCreateDTO);
		return redirect("/sports/" + sportDTO.getId() + "?create");
	}

	@RequestMapping("/{id}/update")
	public Object renderUpdate(@PathVariable("id") Long id, Model model) {
		CRUD_LOGGER.logFindBy("ID", id);
		SportDTO sportDTO = sportFacade.getSportById(id);
		if (sportDTO == null) {
			return redirect("/sports");
		}
		model.addAttribute("sport", beanMappingService.mapTo(sportDTO, SportUpdateDTO.class));
		return "sport.update";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public Object processUpdate(@Valid @ModelAttribute("sport") SportUpdateDTO sportUpdateDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", true);
			return "sport.update";
		}
		CRUD_LOGGER.logUpdate(sportUpdateDTO);
		sportFacade.update(sportUpdateDTO);
		return redirect("/sports/" + sportUpdateDTO.getId() + "?update");
	}

	@RequestMapping("/{id}/delete")
	public Object renderDelete(@PathVariable("id") Long id) {
		CRUD_LOGGER.logFindBy("ID", id);
		SportDTO sportDTO = sportFacade.getSportById(id);
		if (sportDTO != null) {
			sportFacade.delete(sportDTO.getId());
		}
		return redirect("/sports?delete");
	}

}
