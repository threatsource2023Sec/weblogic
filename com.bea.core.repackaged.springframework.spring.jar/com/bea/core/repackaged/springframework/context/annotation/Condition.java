package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.type.AnnotatedTypeMetadata;

@FunctionalInterface
public interface Condition {
   boolean matches(ConditionContext var1, AnnotatedTypeMetadata var2);
}
