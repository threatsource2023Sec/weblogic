package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.reflect.Type;

public interface InterTypeFieldDeclaration extends InterTypeDeclaration {
   String getName();

   AjType getType();

   Type getGenericType();
}
