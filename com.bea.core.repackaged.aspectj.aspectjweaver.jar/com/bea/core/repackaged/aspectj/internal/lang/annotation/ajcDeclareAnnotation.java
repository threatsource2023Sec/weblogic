package com.bea.core.repackaged.aspectj.internal.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ajcDeclareAnnotation {
   String pattern();

   String annotation();

   String kind();
}
