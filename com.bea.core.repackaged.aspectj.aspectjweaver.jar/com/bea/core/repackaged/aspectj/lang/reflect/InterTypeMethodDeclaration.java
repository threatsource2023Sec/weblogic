package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public interface InterTypeMethodDeclaration extends InterTypeDeclaration {
   String getName();

   AjType getReturnType();

   Type getGenericReturnType();

   AjType[] getParameterTypes();

   Type[] getGenericParameterTypes();

   TypeVariable[] getTypeParameters();

   AjType[] getExceptionTypes();
}
