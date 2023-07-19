package com.chrisxyq.ratelimiter.Interceptor;

import com.chrisxyq.ratelimiter.extension.Order;
import com.chrisxyq.ratelimiter.monitor.MonitorManager;
import com.chrisxyq.ratelimiter.rule.ApiLimit;

/**
 * The interceptor is used to monitor every call to method
 * {@link UrlRateLimiter#limit(String, String)} and give statistics.
 */
@Order(Order.LOWEST_PRECEDENCE)
public class MonitorInterceptor extends RateLimiterInterceptorAdapter {
  ThreadLocal<Long> startTime = new ThreadLocal<>();

  @Override
  public void beforeLimit(String appId, String api) {
    startTime.set(System.nanoTime());
  }

  @Override
  public void afterLimit(String appId, String api, ApiLimit apiLimit, boolean result,
                         Exception ex) {
    long startNano = startTime.get();
    startTime.remove();
    long duration = (System.nanoTime() - startNano) / 1000; // microsecond(us)
    MonitorManager.collect(appId, api, apiLimit, duration, result, ex);
  }

}
