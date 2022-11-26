package com.bea.core.repackaged.aspectj.lang.reflect;

public interface DeclareSoft {
   AjType getDeclaringType();

   AjType getSoftenedExceptionType() throws ClassNotFoundException;

   PointcutExpression getPointcutExpression();
}
