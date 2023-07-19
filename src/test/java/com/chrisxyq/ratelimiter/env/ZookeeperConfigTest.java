package com.chrisxyq.ratelimiter.env;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class ZookeeperConfigTest {

  public void testBuildFromProperties() {
    PropertySource propertySource = new PropertySource();
    Map<String, Object> properties = new LinkedHashMap<>();
    properties.put(PropertyConstants.PROPERTY_ZOOKEEPER_ADDRESS, "1.1.1.1:1212");
    properties.put(PropertyConstants.PROPERTY_ZOOKEEPER_RULE_PATH, "/com/chrisxyq/ratelimit");
    propertySource.addProperties(properties);

    ZookeeperConfig zkConfig = new ZookeeperConfig();
    zkConfig.buildFromProperties(propertySource);
    assertEquals(zkConfig.getAddress(), "1.1.1.1:1212");
    assertEquals(zkConfig.getPath(), "/com/chrisxyq/ratelimit");
  }

  public void testBuildFromProperties_withEmptyProperties() {
    PropertySource propertySource = new PropertySource();
    ZookeeperConfig zkConfig = new ZookeeperConfig();
    zkConfig.buildFromProperties(propertySource);
    assertTrue(StringUtils.isEmpty(zkConfig.getAddress()));
    assertEquals(zkConfig.getPath(), ZookeeperConfig.DEFAULT_PATH);
  }

}
