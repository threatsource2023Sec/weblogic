package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.reflect.Type;

public interface Advice {
   AjType getDeclaringType();

   AdviceKind getKind();

   String getName();

   AjType[] getParameterTypes();

   Type[] getGenericParameterTypes();

   AjType[] getExceptionTypes();

   PointcutExpression getPointcutExpression();
}
