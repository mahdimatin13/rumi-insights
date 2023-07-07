package com.mahdi.rumi.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

import java.util.UUID;

@Setter
@Getter
public class QuoteDto {

        private UUID id;

        @NotNull(message = "Quote text is required")
        private String text;
}
