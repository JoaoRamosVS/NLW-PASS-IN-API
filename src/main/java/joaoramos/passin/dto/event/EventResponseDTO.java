package joaoramos.passin.dto.event;

import joaoramos.passin.domain.event.Event;

public class EventResponseDTO {
    EventDetailDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttendees)
    {
        this.event = new EventDetailDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(), event.getMaximumAttendees(), numberOfAttendees);
    }
}
