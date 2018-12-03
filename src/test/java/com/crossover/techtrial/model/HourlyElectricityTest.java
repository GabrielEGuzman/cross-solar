package com.crossover.techtrial.model;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * HourlyElectricityTest class will test all APIs in HourlyElectricity.java.
 * 
 * @author Guzman
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HourlyElectricityTest {
  
  @Test
  public void testEqualsAndHashCode() {
    
    Panel panel = new Panel("4321", 43.11, 22.11, "guzman");

    HourlyElectricity electricity1 = new HourlyElectricity();
    electricity1.setPanel(panel);
    electricity1.setGeneratedElectricity(Long.MAX_VALUE);
    electricity1.setReadingAt(LocalDateTime.MAX);
    
    HourlyElectricity electricity2 = new HourlyElectricity();
    electricity2.setPanel(panel);
    electricity2.setGeneratedElectricity(Long.MAX_VALUE);
    electricity2.setReadingAt(LocalDateTime.MAX);
    
    assertTrue(electricity1.equals(electricity2) && electricity2.equals(electricity1));
    assertTrue(electricity1.hashCode() == electricity2.hashCode());
  }
}
