package io.upschool.model;

import lombok.Getter;

@Getter
public enum TicketStatus {
    PENDING,
    CANCELED,
    RESERVED,
    CONFIRMED
}
