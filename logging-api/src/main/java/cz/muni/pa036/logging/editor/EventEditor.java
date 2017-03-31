package cz.muni.pa036.logging.editor;

import cz.muni.pa036.logging.dto.EventDTO;
import cz.muni.pa036.logging.facade.EventFacade;

import java.beans.PropertyEditorSupport;

/**
 * @author Vit Hovezak
 */
public class EventEditor extends PropertyEditorSupport {

    private final EventFacade eventFacade;

    public EventEditor(EventFacade eventFacade) {
        this.eventFacade = eventFacade;
    }

    public void setAsText(String value) {
        if (value.equals("")) {
            setValue(null);
            return;
        }
        setValue(eventFacade.findById(Long.valueOf(value)));
    }

    public String getAsText() {
        EventDTO eventDTO = (EventDTO) this.getValue();
        if (eventDTO == null) {
            return "";
        }
        return String.valueOf(eventDTO.getId());
    }

}
