package com.bea.core.repackaged.springframework.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheConfig {
   String[] cacheNames() default {};

   String keyGenerator() default "";

   String cacheManager() default "";

   String cacheResolver() default "";
}
