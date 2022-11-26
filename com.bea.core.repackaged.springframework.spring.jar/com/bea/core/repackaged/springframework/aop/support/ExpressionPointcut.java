package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ExpressionPointcut extends Pointcut {
   @Nullable
   String getExpression();
}
