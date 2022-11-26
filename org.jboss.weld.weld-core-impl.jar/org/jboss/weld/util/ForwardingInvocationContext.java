package org.jboss.weld.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import javax.interceptor.InvocationContext;

public abstract class ForwardingInvocationContext implements InvocationContext {
   protected abstract InvocationContext delegate();

   public Object getTarget() {
      return this.delegate().getTarget();
   }

   public Method getMethod() {
      return this.delegate().getMethod();
   }

   public Constructor getConstructor() {
      return this.delegate().getConstructor();
   }

   public Object[] getParameters() throws IllegalStateException {
      return this.delegate().getParameters();
   }

   public void setParameters(Object[] params) throws IllegalStateException, IllegalArgumentException {
      this.delegate().setParameters(params);
   }

   public Map getContextData() {
      return this.delegate().getContextData();
   }

   public Object getTimer() {
      return this.delegate().getTimer();
   }

   public Object proceed() throws Exception {
      return this.delegate().proceed();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof ForwardingInvocationContext ? this.delegate().equals(((ForwardingInvocationContext)ForwardingInvocationContext.class.cast(obj)).delegate()) : this.delegate().equals(obj);
   }

   public String toString() {
      return this.delegate().toString();
   }
}
