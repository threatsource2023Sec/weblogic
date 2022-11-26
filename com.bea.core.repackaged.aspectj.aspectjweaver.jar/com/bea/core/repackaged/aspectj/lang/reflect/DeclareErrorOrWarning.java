package com.bea.core.repackaged.aspectj.lang.reflect;

public interface DeclareErrorOrWarning {
   AjType getDeclaringType();

   PointcutExpression getPointcutExpression();

   String getMessage();

   boolean isError();
}
