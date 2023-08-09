package io.upschool.controller;

import io.upschool.dto.ticketDto.TicketRequest;
import io.upschool.dto.ticketDto.TicketResponse;
import io.upschool.exceptions.CardNumberException;
import io.upschool.exceptions.FlightException;
import io.upschool.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/search")
    public ResponseEntity<TicketResponse> getTicketByTicketNumber(@RequestParam("ticketNumber") String ticketNumber) {
        return ResponseEntity.ok(ticketService.getTicketByTicketNumber(ticketNumber));
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketRequest ticketRequest) throws FlightException, CardNumberException {
        return ResponseEntity.ok(ticketService.createTicket(ticketRequest));
    }

    @DeleteMapping("/cancel")
    public void cancelTicket(@RequestParam("ticketNumber") String ticketNumber) throws FlightException {
        ticketService.cancelTicket(ticketNumber);
    }
}
