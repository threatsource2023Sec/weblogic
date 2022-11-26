package com.bea.staxb.buildtime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
public @interface ClassBindingInfo {
   TargetSchemaType schemaType();

   boolean isIgnoreSuperclass() default false;

   boolean isExclude() default false;
}
