package com.oracle.pitchfork.intercept;

import com.bea.core.repackaged.springframework.aop.framework.ReflectiveMethodInvocation;
import com.oracle.pitchfork.interfaces.ContextDataProvider;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.interceptor.InvocationContext;

public class MethodInvocationInvocationContext implements InvocationContext {
   private final ReflectiveMethodInvocation mi;
   private final Map intfMethodToBeanMethod = new HashMap();
   private final ContextDataProvider ctxDataProvider;
   private Map contextData;

   public MethodInvocationInvocationContext(ReflectiveMethodInvocation mi, ContextDataProvider cdp) {
      this.mi = mi;
      this.ctxDataProvider = cdp;
   }

   public Object getTarget() {
      return this.mi.getThis();
   }

   public Method getMethod() {
      if (this.intfMethodToBeanMethod.containsKey(this.mi.getMethod())) {
         return (Method)this.intfMethodToBeanMethod.get(this.mi.getMethod());
      } else {
         String methodName = this.mi.getMethod().getName();
         Class[] params = this.mi.getMethod().getParameterTypes();

         try {
            Method beanMethod = this.mi.getThis().getClass().getMethod(methodName, params);
            this.intfMethodToBeanMethod.put(this.mi.getMethod(), beanMethod);
            return beanMethod;
         } catch (NoSuchMethodException var4) {
            throw new AssertionError(var4);
         }
      }
   }

   public Object[] getParameters() {
      return this.mi.getArguments();
   }

   public void setParameters(Object[] params) {
      if (params == null) {
         params = new Object[0];
      }

      ReflectionUtils.checkMethodAndArguments(this.mi.getMethod(), params);

      for(int i = 0; i < params.length; ++i) {
         this.mi.getArguments()[i] = params[i];
      }

   }

   public Map getContextData() {
      if (this.ctxDataProvider != null) {
         return this.ctxDataProvider.getContextData();
      } else {
         if (this.contextData == null) {
            this.contextData = new HashMap();
         }

         return this.contextData;
      }
   }

   public Object proceed() throws Exception {
      try {
         return this.mi.proceed();
      } catch (Exception var3) {
         throw var3;
      } catch (Throwable var4) {
         EJBException e = new EJBException("Unpredictable runtime error is caught. See the nested exception.");
         e.initCause(var4);
         throw e;
      }
   }

   public Object getTimer() {
      return null;
   }

   public Constructor getConstructor() {
      return null;
   }
}
