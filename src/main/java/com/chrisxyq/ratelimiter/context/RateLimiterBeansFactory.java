package com.chrisxyq.ratelimiter.context;

import com.chrisxyq.ratelimiter.Interceptor.RateLimiterInterceptor;
import com.chrisxyq.ratelimiter.Interceptor.RateLimiterInterceptorChain;
import com.chrisxyq.ratelimiter.env.RateLimiterConfig;
import com.chrisxyq.ratelimiter.extension.ExtensionLoader;
import com.chrisxyq.ratelimiter.redis.DefaultJedisTaskExecutor;
import com.chrisxyq.ratelimiter.redis.JedisTaskExecutor;
import com.chrisxyq.ratelimiter.rule.RateLimitRule;
import com.chrisxyq.ratelimiter.rule.UrlRateLimitRule;
import com.chrisxyq.ratelimiter.rule.parser.JsonRuleConfigParser;
import com.chrisxyq.ratelimiter.rule.parser.RuleConfigParser;
import com.chrisxyq.ratelimiter.rule.parser.YamlRuleConfigParser;
import com.chrisxyq.ratelimiter.rule.source.FileRuleConfigSource;
import com.chrisxyq.ratelimiter.rule.source.RuleConfigSource;
import com.chrisxyq.ratelimiter.rule.source.ZookeeperRuleConfigSource;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Collections;
import java.util.List;

/**
 * Bean factory to create beans. Create object according from different of sources sequentially:
 * user specified object > SPI > configuration > default object.
 * <p>
 * TODO(zheng): support singleton bean factory.
 */
public class RateLimiterBeansFactory {

    public static final RateLimiterBeansFactory BEANS_CONTEXT = new RateLimiterBeansFactory();

    private RateLimiterBeansFactory() {
        RateLimiterConfig.instance().load();
    }

    /**
     * Obtain {@link RateLimiterInterceptorChain}
     *
     * @param chain the interceptor chain
     * @return the input parameter {@code chain} if not null, otherwise return a new
     * {@link RateLimiterInterceptorChain}
     */
    public RateLimiterInterceptorChain obtainInterceptorChain(RateLimiterInterceptorChain chain) {
        if (chain == null) {
            chain = new RateLimiterInterceptorChain();
            List<RateLimiterInterceptor> interceptors = obtainLimiterInterceptors(null);
            chain.addInterceptors(interceptors);
        }

        return chain;
    }

    /**
     * Obtain {@link RateLimiterInterceptor} list.
     *
     * @param interceptors the interceptor list which can be null.
     * @return the input parameter {@code interceptors} if not null, otherwise return a new
     * {@link RateLimiterInterceptor} list.
     */
    public List<RateLimiterInterceptor> obtainLimiterInterceptors(
            List<RateLimiterInterceptor> interceptors) {
        // SPI
        if (interceptors == null) {
            interceptors = ExtensionLoader.getExtensionList(RateLimiterInterceptor.class, false);
        }

        if (interceptors == null) {
            interceptors = Collections.emptyList();
        }

        return interceptors;
    }

    /**
     * Obtain {@link RuleConfigSource}
     *
     * @param ruleConfigSource the rule config source which can be null.
     * @return the input parameter {@code ruleConfigSource} if not null, otherwise return a new
     * {@link RuleConfigSource}.
     */
    public RuleConfigSource obtainRuleConfigSource(RuleConfigSource ruleConfigSource) {
        /* create according to SPI */
        if (ruleConfigSource == null) {
            ruleConfigSource = ExtensionLoader.getExtension(RuleConfigSource.class, false);
        }

        /* create according to configuration */
        if (ruleConfigSource == null) {
            String sourceType = RateLimiterConfig.instance().getRuleConfigSourceType();
            // TODO(zheng): ugly code, refactor it!
            if (sourceType.equals("zookeeper")) {
                ruleConfigSource = new ZookeeperRuleConfigSource();
            } else if (sourceType.equals("file")) {
                ruleConfigSource = new FileRuleConfigSource();
            }
        }

        /* use default rule config source. */
        if (ruleConfigSource == null) {
            ruleConfigSource = new FileRuleConfigSource();
        }

        return ruleConfigSource;
    }

    /**
     * Obtain {@link RuleConfigParser}.
     *
     * @param ruleConfigParser the rule config source which can be null.
     * @return the input parameter {@code ruleConfigSource} if not null, otherwise return a new
     * {@link RuleConfigSource}.
     */
    public RuleConfigParser obtainRuleConfigParser(RuleConfigParser ruleConfigParser) {
        /* create according to SPI */
        if (ruleConfigParser == null) {
            ruleConfigParser = ExtensionLoader.getExtension(RuleConfigParser.class, false);
        }

        /* create according to configuration */
        if (ruleConfigParser == null) {
            String parserType = RateLimiterConfig.instance().getRuleConfigParserType();
            // TODO(zheng): ugly code, refactor it!
            if (parserType.equals("yaml")) {
                ruleConfigParser = new YamlRuleConfigParser();
            } else if (parserType.equals("json")) {
                ruleConfigParser = new JsonRuleConfigParser();
            }
        }

        /* use default rule config source. */
        if (ruleConfigParser == null) {
            ruleConfigParser = new YamlRuleConfigParser();
        }

        return ruleConfigParser;
    }

    /**
     * Obtain {@link JedisTaskExecutor}
     *
     * @param jedisTaskExecutor the Jedis wrapper which can be null.
     * @return the input parameter {@code jedisTaskExecutor} if not null, otherwise return a new
     * {@link JedisTaskExecutor}.
     */
    public JedisTaskExecutor obtainJedisTaskExecutor(JedisTaskExecutor jedisTaskExecutor) {
        if (jedisTaskExecutor != null) {
            return jedisTaskExecutor;
        }

        GenericObjectPoolConfig poolConfig =
                RateLimiterConfig.instance().getRedisConfig().getPoolConfig();
        String address = RateLimiterConfig.instance().getRedisConfig().getAddress();
        int timeout = RateLimiterConfig.instance().getRedisConfig().getTimeout();
        return new DefaultJedisTaskExecutor(address, timeout, poolConfig);
    }

    /**
     * Obtain {@link RateLimitRule}
     *
     * @param urlRateLimitRule the limit rule which can be null.
     * @return the input parameter {@code urlRateLimitRule} if not null, otherwise return a new
     * {@link RateLimitRule}.
     */
    public RateLimitRule obtainUrlRateLimitRule(RateLimitRule urlRateLimitRule) {
        if (urlRateLimitRule != null) {
            return urlRateLimitRule;
        }

        urlRateLimitRule = new UrlRateLimitRule();
        return urlRateLimitRule;
    }

}
