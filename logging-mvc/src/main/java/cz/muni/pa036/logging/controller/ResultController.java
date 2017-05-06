package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.utils.PerformanceUnits;
import cz.muni.pa036.logging.dto.*;
import cz.muni.pa036.logging.editor.EventEditor;
import cz.muni.pa036.logging.editor.SportsmanEditor;
import cz.muni.pa036.logging.facade.ResultFacade;
import cz.muni.pa036.logging.facade.SportsmanFacade;
import cz.muni.pa036.logging.service.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import cz.muni.pa036.logging.helper.CRUDLogger;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vit Hovezak
 */
@Controller
@RequestMapping("/results")
public class ResultController extends BaseController {

	private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

	@Autowired
	private EventFacade eventFacade;

	@Autowired
	private ResultFacade resultFacade;

	@Autowired
	private SportsmanFacade sportsmanFacade;

	@Autowired
	private BeanMappingService beanMappingService;

	@ModelAttribute("performanceUnits")
	public PerformanceUnits[] getPerformanceUnits() {
		return PerformanceUnits.values();
	}

	@ModelAttribute("events")
	public List<EventDTO> getEvents() {
		CRUD_LOGGER.logFindAll();
		return eventFacade.findAll();
	}

	@ModelAttribute("sportsmans")
	public List<SportsmanDTO> getSportsmans() {
		CRUD_LOGGER.logFindAll();
		return sportsmanFacade.getAll();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(EventDTO.class, new EventEditor(eventFacade));
		binder.registerCustomEditor(SportsmanDTO.class, new SportsmanEditor(sportsmanFacade));
	}

	@RequestMapping
	public String renderList(Model model) {
		CRUD_LOGGER.logFindAll();
		List<ResultDTO> allResults = resultFacade.findAll();
		Map<EventDTO, List<ResultDTO>> events = new HashMap<>();
		for (ResultDTO result : allResults) {
			if (result.getPerformance() >= 0 && result.getPosition() >= 0) {
				if (events.keySet().contains(result.getEvent())) {
					events.get(result.getEvent()).add(result);
				} else {
					events.put(result.getEvent(), new ArrayList<ResultDTO>(){{add(result);}});
				}
			}
		}
		model.addAttribute("events", events);
		return "result.list";
	}

	@RequestMapping("/{id}")
	public Object renderDetail(@PathVariable("id") Long id, Model model) {
		CRUD_LOGGER.logFindBy("ID", id);
		ResultDTO resultDTO = resultFacade.findById(id);
		if (resultDTO == null) {
			return redirect("/results");
		}
		model.addAttribute("result", resultDTO);
		CRUD_LOGGER.logFindBy("event", eventFacade);
		List<ResultDTO> results = resultFacade.findByEvent(resultDTO.getEvent());
		results.remove(resultDTO);
		model.addAttribute("results", results);
		return "result.detail";
	}

	@RequestMapping("/{id}/update")
	public Object renderUpdate(@PathVariable("id") Long id, Model model) {
		CRUD_LOGGER.logFindBy("ID", id);
		ResultDTO resultDTO = resultFacade.findById(id);
		if (resultDTO == null) {
			return redirect("/results");
		}
		model.addAttribute("result", beanMappingService.mapTo(resultDTO, ResultUpdateDTO.class));
		return "result.update";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public Object processUpdate(@Valid @ModelAttribute("result") ResultUpdateDTO resultUpdateDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", true);
			return "result.update";
		}
		CRUD_LOGGER.logUpdate(resultUpdateDTO);
		resultFacade.update(resultUpdateDTO);
		return redirect("/results/" + resultUpdateDTO.getId() + "?update");
	}

	@RequestMapping("/{id}/delete")
	public Object renderDelete(@PathVariable("id") Long id) {
		CRUD_LOGGER.logFindBy("ID", id);
		ResultDTO resultDTO = resultFacade.findById(id);
		if (resultDTO != null) {
			resultFacade.delete(resultDTO.getId());
		}
		return redirect("/results?delete");
	}

	@RequestMapping("/{id}/participants")
	public Object renderParticipants(@PathVariable("id") Long id, Model model) {
		CRUD_LOGGER.logFindBy("ID", id);
		EventDTO eventDTO = eventFacade.findById(id);
		if (eventDTO == null) {
			return redirect("/event/"+id);
		}
		CRUD_LOGGER.logFindBy("event", eventDTO);
		List<ResultDTO> results = resultFacade.findByEvent(eventDTO);

		if(results.isEmpty()){
			return redirect("/event/"+id);
		}

		model.addAttribute("results", beanMappingService.mapTo(results, ResultUpdateDTO.class));
		return "result.participants";
	}

	@RequestMapping("/{eventId}/reset/{id}")
	public Object renderReset(@PathVariable("eventId") Long eventId, @PathVariable("id") Long id) {
		CRUD_LOGGER.logFindBy("ID", id);
		ResultDTO resultDTO = resultFacade.findById(id);
		if (resultDTO != null) {
			resultDTO.setPerformance(new Double(-1));
			resultDTO.setPosition(new Integer(-1));
			resultFacade.update(beanMappingService.mapTo(resultDTO, ResultUpdateDTO.class));
		}
		return redirect("/results/"+ eventId + "/participants/");
	}

	@RequestMapping("/{eventId}/insert/{id}")
	public Object renderInsert(@PathVariable("eventId") Long eventId, @PathVariable("id") Long id, Model model) {
		CRUD_LOGGER.logFindBy("ID", id);
		ResultDTO resultDTO = resultFacade.findById(id);
		if (resultDTO == null) {
			return redirect("/results/"+ eventId + "/participants/");
		}
		model.addAttribute("result", beanMappingService.mapTo(resultDTO, ResultUpdateDTO.class));
		return "result.insert";
	}

	@RequestMapping(value = "/{eventId}/insert/{id}", method = RequestMethod.POST)
	public Object processInsert(@PathVariable("eventId") Long eventId,
								@Valid @ModelAttribute("result") ResultUpdateDTO resultUpdateDTO,
								BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", true);
			return "result.insert";
		}
		CRUD_LOGGER.logUpdate(resultUpdateDTO);
		resultFacade.update(resultUpdateDTO);
		return redirect("/results/" + eventId + "/participants/");
	}

}
