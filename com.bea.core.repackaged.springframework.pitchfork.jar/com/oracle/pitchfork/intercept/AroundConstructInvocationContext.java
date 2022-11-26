package com.oracle.pitchfork.intercept;

import com.oracle.pitchfork.JeeLogger;
import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.interfaces.BeanCreator;
import com.oracle.pitchfork.interfaces.ContextDataProvider;
import com.oracle.pitchfork.interfaces.LifecycleCallbackException;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AroundConstructInvocationContext extends LifecycleEventCallbackInvocationContext {
   private final BeanCreator beanCreator;
   private final Constructor ctr;
   private Object bean;
   private Object[] params;

   AroundConstructInvocationContext(BeanCreator beanCreator, Jsr250Metadata parent, ContextDataProvider cdp) {
      super((Object)null, (Jsr250Metadata)parent, (LifecycleEvent)LifecycleEvent.AROUND_CONSTRUCT, cdp);
      this.beanCreator = beanCreator;
      this.ctr = beanCreator.getBeansConstructor();
      this.params = new Object[this.ctr.getParameterTypes().length];
   }

   public Object getTarget() {
      return this.bean;
   }

   public Method getMethod() {
      return null;
   }

   public Constructor getConstructor() {
      return this.ctr;
   }

   public Object[] getParameters() {
      return this.params;
   }

   public void setParameters(Object[] params) {
      ReflectionUtils.checkArguments(this.ctr.getParameterTypes(), params);
      this.params = params;
   }

   public Object proceed() throws Exception {
      if (!this.interceptors.isEmpty()) {
         MethodInvocationData mi = (MethodInvocationData)this.interceptors.removeFirst();
         Jsr250Metadata.invokeLifecycleMethod(mi.getInvocationTarget(), mi.getInvocationMethod(), new Object[]{this});
         if (!this.interceptors.isEmpty()) {
            throw new LifecycleCallbackException(JeeLogger.logProceedNotInvokedForAroundConstruct(mi.getInvocationMethod()));
         } else {
            return null;
         }
      } else {
         this.bean = this.beanCreator.createBean();
         return null;
      }
   }

   public Object getTimer() {
      return null;
   }

   public String toString() {
      return "AroundConstructInvocationContext(" + System.identityHashCode(this) + ")";
   }
}
