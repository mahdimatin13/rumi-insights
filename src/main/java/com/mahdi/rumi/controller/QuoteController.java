package com.mahdi.rumi.controller;

import com.mahdi.rumi.dto.QuoteDto;
import com.mahdi.rumi.entity.Quote;
import com.mahdi.rumi.service.QuoteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/quotes")
public class QuoteController {
    private final QuoteService service;
    private final ModelMapper mapper;



    @GetMapping("/{id}")
    public QuoteDto getQuoteById(@PathVariable("id") UUID id) {
        return convertToDto(service.findQuoteById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteQuoteById(@PathVariable("id") UUID id) {
        service.removeQuoteById(id);
    }

    @PostMapping
    public QuoteDto postQuote(@Valid @RequestBody QuoteDto quoteDto) {
        var entity = convertToEntity(quoteDto);
        var quote = service.addQuote(entity);

        return convertToDto(quote);
    }

    @PutMapping("/{id}")
    public void putQuote(
            @PathVariable("id") UUID id,
            @Valid @RequestBody QuoteDto QuoteDto
    ) {
        if (!id.equals(QuoteDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match"
        );

        var Quote = convertToEntity(QuoteDto);
        service.updateQuote(id, Quote);
    }

    private QuoteDto convertToDto(Quote entity) {
        return mapper.map(entity, QuoteDto.class);
    }

    private Quote convertToEntity(QuoteDto dto) {
        return mapper.map(dto, Quote.class);
    }

    @GetMapping
    public List<QuoteDto> getQuotes() {
        var quoteLists = StreamSupport
                .stream(service.findAllQuotes().spliterator(), false)
                .collect(Collectors.toList());
        return quoteLists
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());


    }
}
