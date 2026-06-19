package com.template.service;

import com.template.entity.*;
import com.template.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepo;
    private final TicketTypeRepository ticketTypeRepo;

    public RegistrationService(RegistrationRepository registrationRepo,
                               TicketTypeRepository ticketTypeRepo) {
        this.registrationRepo = registrationRepo;
        this.ticketTypeRepo   = ticketTypeRepo;
    }

    public Registration register(Attendee attendee, Long eventId, Long ticketTypeId) {
        TicketType ticketType = ticketTypeRepo.findById(ticketTypeId)
                .orElseThrow(() -> new RuntimeException("Ticket type not found"));

        if (ticketType.getRemainingCapacity() <= 0) {
            throw new IllegalStateException("This ticket type is sold out");
        }

        if (registrationRepo.existsByAttendeeAndTicketType(attendee, ticketType)) {
            throw new IllegalStateException("Already registered for this event");
        }

        ticketType.setRemainingCapacity(ticketType.getRemainingCapacity() - 1);
        ticketTypeRepo.save(ticketType);

        Registration reg = new Registration();
        reg.setAttendee(attendee);
        reg.setEvent(ticketType.getEvent());
        reg.setTicketType(ticketType);
        reg.setStatus(Registration.RegistrationStatus.CONFIRMED);
        return registrationRepo.save(reg);
    }

    public void checkIn(String qrCode) {
        Registration reg = registrationRepo.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Invalid QR code"));

        if (reg.isCheckedIn()) {
            throw new IllegalStateException("Already checked in");
        }

        reg.setCheckedIn(true);
        reg.setCheckInTime(java.time.LocalDateTime.now());
        registrationRepo.save(reg);
    }
}
