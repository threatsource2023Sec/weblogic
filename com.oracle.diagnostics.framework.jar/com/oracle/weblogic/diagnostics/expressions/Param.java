package com.oracle.weblogic.diagnostics.expressions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
public @interface Param {
   String getDefaultValue() default "";

   String legalMin() default "";

   String legalMax() default "";

   String legalValues() default "";
}
