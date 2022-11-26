package com.bea.staxb.buildtime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
public @interface FieldBindingInfo {
   TargetSchemaProperty schemaProperty() default @TargetSchemaProperty;

   boolean isExclude() default false;

   String asTypeNamed() default "";

   String asComponentTypeNamed() default "";
}
