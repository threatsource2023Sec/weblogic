package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.reflect.Type;

public interface InterTypeConstructorDeclaration extends InterTypeDeclaration {
   AjType[] getParameterTypes();

   Type[] getGenericParameterTypes();

   AjType[] getExceptionTypes();
}
