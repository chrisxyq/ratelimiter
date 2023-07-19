package com.chrisxyq.ratelimiter.env.io;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

@Test
public class ClassPathResourceTest {

  private static final String OK_PATH = "ratelimiter-env.yaml";

  public void testGetPath() {
    Resource resource = new ClassPathResource(OK_PATH);
    String actualPath = resource.getPath();
    assertEquals(OK_PATH, actualPath);
  }

  public void testGetExtension() {
    Resource resource = new ClassPathResource(OK_PATH);
    String actualExtension = resource.getExtension();
    assertEquals("yaml", actualExtension);

    resource = new ClassPathResource("no-extension");
    actualExtension = resource.getExtension();
    assertNull(actualExtension);
  }

  public void testGetDescription() {
    Resource resource = new ClassPathResource(OK_PATH);
    String description = resource.getDescription();
    assertNotNull(description);
    assertTrue(description.length() > 0);
  }

  public void testExists() {
    Resource resource = new ClassPathResource(OK_PATH);
    boolean actualExisted = resource.exists();
    assertTrue(actualExisted);

    resource = new ClassPathResource("no-exsiting.yaml");
    actualExisted = resource.exists();
    assertFalse(actualExisted);
  }

  public void testGetInputStream() {
    Resource resource = new ClassPathResource(OK_PATH);
    try {
      resource.getInputStream();
    } catch (IOException e) {
      fail();
    }
  }

  @Test(expectedExceptions = {IOException.class})
  public void testGetInputStream_withNotExistingPath() throws IOException {
    Resource resource = new ClassPathResource("no-existing.yaml");
    resource.getInputStream();
  }

}
