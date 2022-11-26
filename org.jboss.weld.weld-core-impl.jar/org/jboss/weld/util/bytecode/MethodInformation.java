package org.jboss.weld.util.bytecode;

import java.lang.reflect.Method;

public interface MethodInformation {
   String getDeclaringClass();

   Method getMethod();

   String getDescriptor();

   String[] getParameterTypes();

   String getReturnType();

   String getName();

   int getModifiers();
}
