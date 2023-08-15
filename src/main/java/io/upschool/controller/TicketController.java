package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.ticketDto.TicketRequest;
import io.upschool.dto.ticketDto.TicketResponse;
import io.upschool.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<TicketResponse>>> getAllTickets() {
        List<TicketResponse> tickets = ticketService.getAllTickets();
        BaseResponse<List<TicketResponse>> response = BaseResponse.<List<TicketResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(tickets)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<TicketResponse>> getTicketByTicketNumber(@RequestParam("ticketNumber") String ticketNumber) {
        TicketResponse ticketResponse = ticketService.getTicketByTicketNumber(ticketNumber);
        BaseResponse<TicketResponse> response = BaseResponse.<TicketResponse>builder()
                .status(HttpStatus.OK.value())
                .data(ticketResponse)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/searchByIdentityNumber")
    public ResponseEntity<BaseResponse<List<TicketResponse>>> getTicketByIdentityNumber(@RequestParam("identityNumber") String identityNumber) {
        List<TicketResponse> tickets = ticketService.getTicketByIdentityNumber(identityNumber);
        BaseResponse<List<TicketResponse>> response = BaseResponse.<List<TicketResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(tickets)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<TicketResponse>> createTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        TicketResponse ticket = ticketService.createTicket(ticketRequest);
        BaseResponse<TicketResponse> response = BaseResponse.<TicketResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(ticket)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel")
    public void cancelTicket(@RequestParam("ticketNumber") String ticketNumber){
        ticketService.cancelTicket(ticketNumber);
        System.out.println("Ticket removed successfully");
    }
}
