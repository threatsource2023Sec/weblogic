package com.bea.core.repackaged.aspectj.lang.reflect;

public interface Pointcut {
   String getName();

   int getModifiers();

   AjType[] getParameterTypes();

   String[] getParameterNames();

   AjType getDeclaringType();

   PointcutExpression getPointcutExpression();
}
