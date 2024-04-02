package joaoramos.passin.services;

import joaoramos.passin.domain.attendee.Attendee;
import joaoramos.passin.domain.event.Event;
import joaoramos.passin.dto.event.EventResponseDTO;
import joaoramos.passin.repositories.AttendeeRepository;
import joaoramos.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetail(String eventId)
    {
        Event event = this.eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found with ID:" + eventId));
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }
}