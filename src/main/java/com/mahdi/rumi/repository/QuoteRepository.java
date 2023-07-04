package com.mahdi.rumi.repository;

import com.mahdi.rumi.entity.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface QuoteRepository extends CrudRepository<Quote, UUID> {
}
