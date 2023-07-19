package com.chrisxyq.ratelimiter.env.loader;

import com.chrisxyq.ratelimiter.env.PropertySource;

/**
 * The interface represents the environment configuration loaders.
 */
public interface PropertySourceLoader {

	PropertySource load();

}
