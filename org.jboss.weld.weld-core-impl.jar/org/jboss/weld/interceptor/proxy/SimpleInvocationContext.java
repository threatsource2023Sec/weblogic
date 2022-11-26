package org.jboss.weld.interceptor.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class SimpleInvocationContext extends AbstractInvocationContext {
   public SimpleInvocationContext(Object target, Method targetMethod, Method proceed, Object[] parameters, Set interceptorBindings) {
      super(target, targetMethod, proceed, (Constructor)null, parameters, (Object)null, (Map)null, interceptorBindings);
   }

   public SimpleInvocationContext(Constructor constructor, Object[] parameters, Map contextData, Set interceptorBindings) {
      super((Object)null, (Method)null, (Method)null, constructor, parameters, (Object)null, contextData, interceptorBindings);
   }

   public Object proceed() throws Exception {
      return this.proceed != null ? this.proceed.invoke(this.target, this.parameters) : null;
   }
}
