package com.oracle.pitchfork.intercept;

import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.interfaces.ContextDataProvider;
import com.oracle.pitchfork.interfaces.LifecycleCallbackException;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.ejb.Timer;
import javax.interceptor.InvocationContext;

public class LifecycleEventCallbackInvocationContext implements InvocationContext {
   private final Jsr250Metadata parentMetadata;
   private final LifecycleEvent le;
   private final ContextDataProvider ctxDataProvider;
   private final Object bean;
   private final Method method;
   private final Timer originalTimer;
   private Object currentParameter;
   protected LinkedList interceptors;
   private Map contextData;

   private LifecycleEventCallbackInvocationContext(Object bean, Method method, Timer timer, Jsr250Metadata parent, LifecycleEvent le, ContextDataProvider ctxDataProvider) {
      this.bean = bean;
      this.method = method;
      this.currentParameter = this.originalTimer = timer;
      this.parentMetadata = parent;
      this.le = le;
      this.ctxDataProvider = ctxDataProvider;
   }

   LifecycleEventCallbackInvocationContext(Object bean, Method m, Timer timer, ContextDataProvider cdp) {
      this(bean, m, timer, (Jsr250Metadata)null, (LifecycleEvent)null, cdp);
   }

   LifecycleEventCallbackInvocationContext(Object bean, Jsr250Metadata parent, LifecycleEvent le, ContextDataProvider cdp) {
      this(bean, (Method)null, (Timer)null, parent, le, cdp);
   }

   public Object getTarget() {
      return this.bean;
   }

   public Method getMethod() {
      return this.method;
   }

   public Constructor getConstructor() {
      return null;
   }

   public Object[] getParameters() {
      if (this.method == null) {
         throw new IllegalStateException("It is illegal to invoke InvocationContext.getParameters() method in lifecycle callback interceptor method");
      } else {
         return this.method.getParameterTypes().length <= 0 ? new Object[0] : new Object[]{this.currentParameter};
      }
   }

   public void setParameters(Object[] params) {
      if (this.method == null) {
         throw new IllegalStateException("It is illegal to invoke InvocationContext.setParameters(Object[] params) method in lifecycle callback interceptor method");
      } else {
         ReflectionUtils.checkMethodAndArguments(this.method, params);
         if (params != null && params.length >= 1) {
            this.currentParameter = params[0];
         }

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
      if (!this.interceptors.isEmpty()) {
         MethodInvocationData mi = (MethodInvocationData)this.interceptors.removeFirst();
         Jsr250Metadata.invokeLifecycleMethod(mi.getInvocationTarget(), mi.getInvocationMethod(), new Object[]{this});
         return null;
      } else {
         if (this.originalTimer != null) {
            Jsr250Metadata.invokeTimeoutMethodInternal(this.bean, this.method, this.currentParameter);
         } else {
            this.parentMetadata.invokeLifecycleMethods(this.bean, this.le);
         }

         return null;
      }
   }

   public Object getTimer() {
      return this.originalTimer;
   }

   void proceed(LinkedList paramInterceptors) {
      this.interceptors = paramInterceptors;

      try {
         this.proceed();
      } catch (LifecycleCallbackException var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new LifecycleCallbackException(var8.getMessage(), var8);
      } finally {
         this.interceptors = null;
      }

   }

   public String toString() {
      return "LifecycleEventCallbackInvocationContext(" + System.identityHashCode(this) + ")";
   }
}
