package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.time.Instant.now;

@Service
public class SaleService {

    private final SaleRepository repository;

    public LocalDate today = LocalDate.ofInstant(now(), ZoneId.systemDefault());
    public LocalDate result = today.minusYears(1L);

    public SaleService(SaleRepository repository) {
        this.repository = repository;
    }

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SummaryMinDTO> findSummary(Pageable pageable, String minDate, String maxDate) {
        // Convert String dates to LocalDate
        LocalDate min = minDate.isEmpty() ? null : LocalDate.parse(minDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate max = maxDate.isEmpty() ? null : LocalDate.parse(maxDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return repository.searchSummary(pageable, min, max);
    }
}
