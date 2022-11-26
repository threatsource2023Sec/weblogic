package org.jboss.weld.bean;

import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.util.bean.IsolatedForwardingInterceptor;

public abstract class ForwardingInterceptor extends IsolatedForwardingInterceptor {
   public abstract Interceptor delegate();

   protected BeanAttributes attributes() {
      return this.delegate();
   }

   public String toString() {
      return "ForwardingInterceptor wrapping " + this.delegate().toString();
   }
}
