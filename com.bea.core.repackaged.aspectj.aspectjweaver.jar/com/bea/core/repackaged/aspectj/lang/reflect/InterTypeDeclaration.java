package com.bea.core.repackaged.aspectj.lang.reflect;

public interface InterTypeDeclaration {
   AjType getDeclaringType();

   AjType getTargetType() throws ClassNotFoundException;

   int getModifiers();
}
