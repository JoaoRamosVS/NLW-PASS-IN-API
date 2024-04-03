package joaoramos.passin.controllers;

import joaoramos.passin.dto.attendee.AttendeesListResponseDTO;
import joaoramos.passin.dto.event.EventIdDTO;
import joaoramos.passin.dto.event.EventRequestDTO;
import joaoramos.passin.dto.event.EventResponseDTO;
import joaoramos.passin.services.AttendeeService;
import joaoramos.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId)
    {
        EventResponseDTO event = this.eventService.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder)
    {
        EventIdDTO eventIdDTO = this.eventService.CreateEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String eventId)
    {
        AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttendee(eventId);
        return ResponseEntity.ok(attendeesListResponse);
    }
}
