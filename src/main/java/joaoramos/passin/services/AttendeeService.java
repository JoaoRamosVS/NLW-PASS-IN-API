package joaoramos.passin.services;

import joaoramos.passin.domain.attendee.Attendee;
import joaoramos.passin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private AttendeeRepository attendeeRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId)
    {
        List<Attendee> attendeesList = this.attendeeRepository.findByEventId(eventId);

        return attendeesList;
    }
}
