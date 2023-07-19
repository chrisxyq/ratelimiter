package com.chrisxyq.ratelimiter.integration.spi;


import com.chrisxyq.ratelimiter.extension.Order;
import com.chrisxyq.ratelimiter.rule.parser.RuleConfigParser;
import com.chrisxyq.ratelimiter.rule.source.UniformRuleConfigMapping;

import java.io.InputStream;

@Order(Order.HIGHEST_PRECEDENCE)
public class SPIRuleConfigParser implements RuleConfigParser {

  @Override
  public UniformRuleConfigMapping parse(String configurationText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UniformRuleConfigMapping parse(InputStream in) {
    // TODO Auto-generated method stub
    return null;
  }

}
