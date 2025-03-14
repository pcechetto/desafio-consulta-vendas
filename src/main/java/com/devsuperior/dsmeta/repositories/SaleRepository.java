package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryMinDTO(s.seller.name, SUM(s.amount)) " +
            "FROM Sale s " +
            "WHERE (:min IS NULL OR s.date >= :min) " +
            "AND (:max IS NULL OR s.date <= :max) " +
            "GROUP BY s.seller.name")
    Page<SummaryMinDTO> searchSummary(
            Pageable pageable,
            @Param("min") LocalDate min,
            @Param("max") LocalDate max);


    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.ReportMinDTO(s.id, s.date, s.amount, s.seller.name) " +
            "FROM Sale s " +
            "WHERE (:min IS NULL OR s.date >= :min) " +
            "AND (:max IS NULL OR s.date <= :max) " +
            "AND (:sellerName IS NULL OR LOWER(s.seller.name) LIKE LOWER(CONCAT('%', :sellerName, '%')))")
    Page<ReportMinDTO> searchReport(
            Pageable pageable,
            @Param("min") LocalDate min,
            @Param("max") LocalDate max,
            @Param("sellerName") String sellerName);
}
