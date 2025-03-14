package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<Page<ReportMinDTO>> getReport(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "maxDate", required = false) String maxDate,
            @RequestParam(value = "minDate", required = false) String minDate,
            @RequestParam(value = "name", required = false) String sellerName
    ) {
        if (maxDate == null || maxDate.isEmpty()) {
            maxDate = service.today.toString();
        }
        if (minDate == null || minDate.isEmpty()) {
            minDate = service.result.toString();
        }

        Page<ReportMinDTO> obj = service.findReport(PageRequest.of(page, size), minDate, maxDate, sellerName);
        return ResponseEntity.ok(obj);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<Page<SummaryMinDTO>> getSummary(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "maxDate", required = false) String maxDate,
            @RequestParam(value = "minDate", required = false) String minDate
    ) {
        if (maxDate == null || maxDate.isEmpty()) {
            maxDate = service.today.toString();
        }
        if (minDate == null || minDate.isEmpty()) {
            minDate = service.result.toString();
        }

        Page<SummaryMinDTO> obj = service.findSummary(PageRequest.of(page, size), minDate, maxDate);
        return ResponseEntity.ok(obj);
    }
}
