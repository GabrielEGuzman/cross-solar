package com.crossover.techtrial.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;

/**
 * HourlyElectricity Repository is for all operations for HourlyElectricity.
 * @author Crossover
 */
@RestResource(exported = false)
public interface HourlyElectricityRepository  extends PagingAndSortingRepository<HourlyElectricity,Long> {
  Page<HourlyElectricity> findAllByPanelIdOrderByReadingAtDesc(Long panelId,Pageable pageable);
  
  /**
   * Find all daily electricity by panel.
   *
   * @param panelId the panel id
   * @param dateTo the date to
   * @return the list
   */
  @Query("select NEW com.crossover.techtrial.dto.DailyElectricity(readingAt, SUM(generatedElectricity), AVG(generatedElectricity), MIN(generatedElectricity), MAX(generatedElectricity)) "
  		+ "from HourlyElectricity "
  		+ "where panel.id = ?1 and readingAt <= ?2 "
  		+ "group by readingAt "
  		+ "order by readingAt")
  List<DailyElectricity> findAllDailyElectricityByPanel(Long panelId, LocalDateTime dateTo);
}
