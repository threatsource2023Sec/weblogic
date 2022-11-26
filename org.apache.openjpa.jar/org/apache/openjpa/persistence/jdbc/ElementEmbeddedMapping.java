package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementEmbeddedMapping {
   String TRUE = "true";
   String FALSE = "false";

   String nullIndicatorColumnName() default "";

   String nullIndicatorAttributeName() default "";

   XMappingOverride[] overrides() default {};
}