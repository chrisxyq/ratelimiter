package com.chrisxyq.ratelimiter.rule.source;

/**
 * Interface to load rule configuration from different kinds of sources.
 */
public interface RuleConfigSource {

  UniformRuleConfigMapping load();

}
