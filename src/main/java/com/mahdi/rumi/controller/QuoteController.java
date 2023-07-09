package com.mahdi.rumi.controller;

import com.mahdi.rumi.dto.QuoteDto;
import com.mahdi.rumi.entity.Quote;
import com.mahdi.rumi.service.QuoteService;
import com.mahdi.rumi.user.data.UserDto;
import com.mahdi.rumi.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
@CrossOrigin(allowedHeaders = "Content-type")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/quotes")
@PreAuthorize("isAuthenticated()")
public class QuoteController {
    private final QuoteService service;
    private final ModelMapper mapper;
    private final UserService userService;
    // LOGGER FROM SLF4j
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteController.class);
    // LOGGER FROM LOMBOK SLF4j


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




    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<QuoteDto> getQuotes(Pageable pageable) {
        int toSkip = pageable.getPageSize() * pageable.getPageNumber();

        //SLF4J
        LOGGER.info("Using SLF4J: Getting quote list - getQuotes() ");

        //LOMBAK SLF4J
        log.info("Using SLF4J Lombak: Getting quote list - getQuotes() ");


        var quoteLists = StreamSupport
                .stream(service.findAllQuotes().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return quoteLists
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto postUser(@Valid @RequestBody UserDto userDto)
            throws NoSuchAlgorithmException {
        return userService.createUser(userDto, userDto.getPassword());
    }



    private QuoteDto convertToDto(Quote entity) {
        return mapper.map(entity, QuoteDto.class);
    }

    private Quote convertToEntity(QuoteDto dto) {
        return mapper.map(dto, Quote.class);
    }
}
