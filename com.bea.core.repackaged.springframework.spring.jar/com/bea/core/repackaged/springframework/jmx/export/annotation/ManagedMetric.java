package com.bea.core.repackaged.springframework.jmx.export.annotation;

import com.bea.core.repackaged.springframework.jmx.support.MetricType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ManagedMetric {
   String category() default "";

   int currencyTimeLimit() default -1;

   String description() default "";

   String displayName() default "";

   MetricType metricType() default MetricType.GAUGE;

   int persistPeriod() default -1;

   String persistPolicy() default "";

   String unit() default "";
}
