package com.chrisxyq.ratelimiter.rule.parser;


import com.chrisxyq.ratelimiter.rule.source.UniformRuleConfigMapping;

import java.io.InputStream;

/**
 * Interface of parser used to parse different types of configurations, such as JSON, YAML and etc.
 */
public interface RuleConfigParser {

  UniformRuleConfigMapping parse(String configurationText);

  UniformRuleConfigMapping parse(InputStream in);

}
