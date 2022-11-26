package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.reflect.Type;

public interface DeclareParents {
   AjType getDeclaringType();

   TypePattern getTargetTypesPattern();

   boolean isExtends();

   boolean isImplements();

   Type[] getParentTypes() throws ClassNotFoundException;
}
