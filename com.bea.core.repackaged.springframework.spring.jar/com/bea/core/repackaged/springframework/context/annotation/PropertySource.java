package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.io.support.PropertySourceFactory;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(PropertySources.class)
public @interface PropertySource {
   String name() default "";

   String[] value();

   boolean ignoreResourceNotFound() default false;

   String encoding() default "";

   Class factory() default PropertySourceFactory.class;
}
