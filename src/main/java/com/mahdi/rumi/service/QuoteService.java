package com.mahdi.rumi.service;

import com.mahdi.rumi.entity.Quote;
import com.mahdi.rumi.exception.NotFoundException;
import com.mahdi.rumi.repository.QuoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class QuoteService {
    private final QuoteRepository repo;

    public Iterable<Quote> findAllQuotes() {
        return repo.findAll();
    }

    public Quote findQuoteById(UUID id) {
               return findOrThrow(id);
    }

    public void removeQuoteById(UUID id) {
        repo.deleteById(id);
    }

    public Quote addQuote(Quote quote) {
        return repo.save(quote);
    }

    public void updateQuote(UUID id, Quote quote) {
        findOrThrow(id);
        repo.save(quote);
    }

    private Quote findOrThrow(final UUID id) {
        return repo
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Quote by id "+ id + "was not found")
                );
    }

}
