package com.bea.staxb.buildtime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface MethodBindingInfo {
   TargetSchemaProperty schemaProperty() default @TargetSchemaProperty;

   String defaultCheckerFor() default "";

   String factoryFor() default "";

   boolean isExclude() default false;

   String asTypeNamed() default "";

   String asComponentTypeNamed() default "";
}
