package ru.merezh.walletservice.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AmountDto(

        @Positive(message = "Сумма должна быть больше нуля")
        BigDecimal amount
) {
}
