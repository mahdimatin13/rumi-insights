package com.mahdi.rumi.quote.h2.service.service;

import com.mahdi.rumi.entity.Quote;
import com.mahdi.rumi.repository.QuoteRepository;
import com.mahdi.rumi.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AntiHeroServiceTest {

    @Mock
    private QuoteRepository quoteRepository;
    @InjectMocks
    private QuoteService underTest;


    @Test
    void canFindAllQuotes() {
        // when
        underTest.findAllQuotes();
        // then
        verify(quoteRepository).findAll();
    }

    @Test
    void canAddQuote() {

        // given

        Quote quote = new Quote(
                UUID.randomUUID(),
                "poornima",
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z").format(new Date())
        );

        // when
        underTest.addQuote(quote);

        // then

        ArgumentCaptor<Quote> quoteDtoArgumentCaptor = ArgumentCaptor.forClass(
                Quote.class
        );

        verify(quoteRepository).save(quoteDtoArgumentCaptor.capture());
        Quote capturedAntiHero = quoteDtoArgumentCaptor.getValue();

        assertThat(capturedAntiHero).isEqualTo(quote);


    }




}
