package com.crossover.techtrial.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.PanelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * 
 * @author Guzman
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {
  
  @Autowired
  private PanelRepository panelRepostitory;

  @Autowired
  private TestRestTemplate template;

  ObjectMapper mapper = new ObjectMapper()
      .registerModule(new ParameterNamesModule())
      .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
  
  private Panel panel;
  
  @Before
  public void setup() throws Exception { 
    long id = new Random().nextInt((int)panelRepostitory.count());
    if (id > 0) {
       String serial = panelRepostitory.findById(id).get().getSerial();
       panel = panelRepostitory.findBySerial(serial);
    }
  }
  
  @Test
  public void testPanelShouldBeRegistered() throws Exception {

    Integer rand = new Random().nextInt();
    String serial = String.valueOf(rand < 0 ? rand*-1 : rand);
    
    Panel panel = new Panel(serial, 54.123232, 54.123232, "B" + serial);
    String body = mapper.writeValueAsString(panel);
    HttpEntity<Object> request = getHttpEntity(body);

    ResponseEntity<Panel> response = template.postForEntity("/api/register",
        request, Panel.class);
    Assert.assertEquals(202, response.getStatusCode().value());
  }

  @Test
  public void testSaveHourlyElectricity() throws Exception {

    HourlyElectricity electricity = new HourlyElectricity();
    electricity.setPanel(panel);
    electricity.setGeneratedElectricity(10L);
    electricity.setReadingAt(LocalDateTime.now());

    String body = mapper.writeValueAsString(electricity);
    HttpEntity<Object> request = getHttpEntity(body);

    Map<String, String> pElectricity = new HashMap<>();
    pElectricity.put("serial", panel.getSerial());
    ResponseEntity<HourlyElectricity> response = template.postForEntity(
        "/api/panels/{serial}/hourly", request, HourlyElectricity.class,
        pElectricity);

    Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatusCode().value());
  }

  @Test
  public void testAllDailyElectricityFromYesterday() throws Exception {
    
    Map<String, String> pElectricity = new HashMap<>();
    pElectricity.put("serial", panel.getSerial());

    ResponseEntity<List<DailyElectricity>> response = template.exchange(
        "/api/panels/{serial}/daily", HttpMethod.GET, null,
        new ParameterizedTypeReference<List<DailyElectricity>>() {
        }, pElectricity);

    List<DailyElectricity> lista = response.getBody();

    Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatusCode().value());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);

  }
}
