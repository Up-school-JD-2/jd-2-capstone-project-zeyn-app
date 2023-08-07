package io.upschool.controller;

import io.upschool.dto.ticketDto.TicketRequest;
import io.upschool.dto.ticketDto.TicketResponse;
import io.upschool.exceptions.CardNumberException;
import io.upschool.exceptions.FlightException;
import io.upschool.service.TicketService;
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
    public ResponseEntity<List<TicketResponse>> getAllTickets(){
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest) throws FlightException, CardNumberException {
        return ResponseEntity.ok(ticketService.createTicket(ticketRequest));
    }
    @DeleteMapping("{ticketNumber}")
    public void cancelTicket(@PathVariable String ticketNumber){
        ticketService.cancelTicket(ticketNumber);
    }
}
