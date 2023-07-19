package com.chrisxyq.ratelimiter.rule.parser;

import com.chrisxyq.ratelimiter.extension.Order;
import com.chrisxyq.ratelimiter.rule.source.UniformRuleConfigMapping;
import com.chrisxyq.ratelimiter.utils.JsonUtils;

import java.io.InputStream;

/**
 * Parser used to parse JSON formatted configuration into {@link UniformRuleConfigMapping}
 */
@Order(Order.HIGHEST_PRECEDENCE + 20)
public class JsonRuleConfigParser implements RuleConfigParser {

  @Override
  public UniformRuleConfigMapping parse(String configurationText) {
    return JsonUtils.json2Object(configurationText, UniformRuleConfigMapping.class);
  }

  @Override
  public UniformRuleConfigMapping parse(InputStream in) {
    return JsonUtils.stream2Object(in, UniformRuleConfigMapping.class);
  }

}
