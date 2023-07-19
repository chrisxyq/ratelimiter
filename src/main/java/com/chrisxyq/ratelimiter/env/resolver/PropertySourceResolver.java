package com.chrisxyq.ratelimiter.env.resolver;

import com.chrisxyq.ratelimiter.exception.ConfigurationResolveException;

import java.io.InputStream;
import java.util.Map;

/**
 * The resolver is used to parse the configurations of different formats, such as yaml, properties.
 */
public interface PropertySourceResolver {

  String[] getSupportedFileExtensions();

  boolean canResolvedExtension(String fileExtension);

  Map<String, Object> resolve(InputStream in) throws ConfigurationResolveException;

}
