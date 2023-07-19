package com.chrisxyq.ratelimiter.extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Order {

  int HIGHEST_PRECEDENCE = 0;

  int LOWEST_PRECEDENCE = 100;

  int value() default LOWEST_PRECEDENCE;
}
