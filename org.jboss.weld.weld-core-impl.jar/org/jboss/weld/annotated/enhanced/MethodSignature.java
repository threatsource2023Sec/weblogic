package org.jboss.weld.annotated.enhanced;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface MethodSignature extends Serializable {
   String getMethodName();

   String[] getParameterTypes();

   boolean matches(Method var1);
}
