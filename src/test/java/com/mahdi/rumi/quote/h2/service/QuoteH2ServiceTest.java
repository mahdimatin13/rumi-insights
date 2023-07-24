package com.mahdi.rumi.quote.h2.service;

import com.mahdi.rumi.entity.Quote;
import com.mahdi.rumi.exception.NotFoundException;
import com.mahdi.rumi.repository.QuoteRepository;
import com.mahdi.rumi.service.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class QuoteH2ServiceTest {
    @Autowired
    private QuoteRepository repo;
    private QuoteService service;

    Quote quote = new Quote();

    @BeforeEach
    public void setup() {
        service = new QuoteService(repo);
    }

    @Test
    public void shouldFindAllQuote() {
        Quote quote = new Quote();

        quote.setText("A moment of happiness");
        quote.setAddedAt("24-07-2023");

        service.addQuote(quote);

        Iterable<Quote> quoteList = service.findAllQuotes();

        Quote savedQuote = quoteList.iterator().next();

        assertThat(savedQuote).isNotNull();
    }

    @Test
    public void shouldAddQuote() {
        Quote quote = new Quote();
        quote.setText("jahish");
        quote.setAddedAt("24-07-2023");
        Iterable<Quote> quoteList = service.findAllQuotes();
        service.findAllQuotes();
        Quote savedQuote = quoteList.iterator().next();

        assertThat(quote).isEqualTo(savedQuote);
    }

    @Test
    public void shouldUpdateQuote() {
        Quote quote = new Quote();
        quote.setText("ali");
        quote.setAddedAt("24-07-2023");
        Quote savedQuote = service.addQuote(quote);
        savedQuote.setText("ali2");
        savedQuote.setAddedAt("25-0702023");
        service.updateQuote(savedQuote.getId(), savedQuote);
        Quote foundQuote = service.findQuoteById(savedQuote.getId());
        assertThat(foundQuote.getText().equalsIgnoreCase("ali2"));
    }

    @Test
    public void shouldDeleteQuote() {
        assertThrows(NotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Quote savedQuote = service.addQuote(quote);
                service.removeQuoteById(
                        savedQuote.getId()
                );
                Quote foundQuote = service.findQuoteById(savedQuote.getId());
                assertThat(foundQuote).isNull();
            }
        });
    }



}
