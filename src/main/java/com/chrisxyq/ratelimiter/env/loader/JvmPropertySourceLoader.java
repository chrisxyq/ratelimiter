package com.chrisxyq.ratelimiter.env.loader;

import com.chrisxyq.ratelimiter.env.PropertyConstants;
import com.chrisxyq.ratelimiter.env.PropertySource;
import com.chrisxyq.ratelimiter.extension.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class loads environment configuration from JVM environment variables.
 */
@Order(Order.HIGHEST_PRECEDENCE + 10)
public class JvmPropertySourceLoader implements PropertySourceLoader {

  @Override
  public PropertySource load() {
    Properties properties = System.getProperties();
    Map<String, Object> ratelimiterProperties = new HashMap<String, Object>();
    Set<String> names = properties.stringPropertyNames();
    for (String name : names) {
      if (name.startsWith(PropertyConstants.PROPERTY_KEY_PREFIX)) {
        ratelimiterProperties.put(name, properties.get(name));
      }
    }
    PropertySource source = new PropertySource();
    source.addProperties(ratelimiterProperties);
    return source;
  }

}
