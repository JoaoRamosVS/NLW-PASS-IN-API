package joaoramos.passin.controllers;

import joaoramos.passin.config.ResponseHandler;
import joaoramos.passin.dto.attendee.AttendeeDetails;
import joaoramos.passin.dto.attendee.AttendeeIdDTO;
import joaoramos.passin.dto.attendee.AttendeeRequestDTO;
import joaoramos.passin.dto.attendee.AttendeesListResponseDTO;
import joaoramos.passin.dto.event.EventIdDTO;
import joaoramos.passin.dto.event.EventRequestDTO;
import joaoramos.passin.dto.event.EventResponseDTO;
import joaoramos.passin.services.AttendeeService;
import joaoramos.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events")
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

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder)
    {
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<Object> getEventAttendees(@PathVariable String eventId, @RequestParam(value = "pageIndex", required = false, defaultValue = "1") String pageIndex, @RequestParam(value = "query", required = false, defaultValue = "") String query)
    {
        int index = Integer.parseInt(pageIndex);
        AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttendee(eventId);
        int total = attendeesListResponse.attendees().size();
        List<AttendeeDetails> attendeeList = attendeesListResponse.attendees();

        if (!query.isEmpty())
        {
            List<AttendeeDetails> queryAttendeeList = new ArrayList<AttendeeDetails>();
            for (AttendeeDetails a : attendeeList)
            {
                if(a.name().contains(query)) {
                    queryAttendeeList.add(a);
                }
            }
            attendeeList = queryAttendeeList;
            total = queryAttendeeList.size();
        }


        if(attendeeList.size() > 10)
        {
            if(index * 10 > attendeeList.size())
            {
                attendeeList = attendeeList.subList((index - 1) * 10, attendeeList.size());
            }
            else
            {
                attendeeList = attendeeList.subList((index - 1) * 10, index * 10);
            }
        }

        return ResponseHandler.generateResponse(attendeeList, HttpStatus.OK, total);
    }

}
