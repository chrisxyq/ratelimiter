package com.chrisxyq.ratelimiter;

import com.chrisxyq.ratelimiter.Interceptor.RateLimiterInterceptor;
import com.chrisxyq.ratelimiter.exception.InternalErrorException;
import com.chrisxyq.ratelimiter.exception.InvalidUrlException;
import com.chrisxyq.ratelimiter.exception.OverloadException;

import java.util.List;

public interface UrlRateLimiter {

  /**
   * check if the url request of the specified app exceeds the max hit limit.
   * 
   * @param appId the app ID
   * @param url the request url
   * @throws OverloadException if the app exceeds the max hit limit for the api.
   * @throws InvalidUrlException if the url is invalid.
   * @throws InternalErrorException if some internal error occurs.
   */
  void limit(String appId, String url)
      throws OverloadException, InvalidUrlException, InternalErrorException;

  /**
   * Add interceptor into the default interceptor chain. The interceptor will do some work
   * before/after the {@code UrlRateLimiter.limit} method.
   * 
   * @param interceptor the interceptor to be added into the interceptor chain.
   */
  void addInterceptor(RateLimiterInterceptor interceptor);

  /**
   * Add interceptors into the default interceptor chain. The interceptor will do some work
   * before/after the {@code UrlRateLimiter.limit} method.
   * 
   * @param interceptors the interceptor list to be added into the interceptor chain.
   */
  void addInteceptors(List<RateLimiterInterceptor> interceptors);

}
