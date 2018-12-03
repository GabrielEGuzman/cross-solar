package com.crossover.techtrial.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * PanelTest class will test all APIs in Panel.java.
 * 
 * @author Guzman
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelTest {
  
  @Test
  public void testEqualsAndHashCode() {
    Panel panel1 = new Panel("12345", 45.555, 123.10, "guzman");
    Panel panel2 = new Panel("12345", 45.555, 123.10, "guzman");
    assertTrue(panel1.equals(panel2) && panel2.equals(panel1));
    assertTrue(panel1.hashCode() == panel2.hashCode());
  }
}
