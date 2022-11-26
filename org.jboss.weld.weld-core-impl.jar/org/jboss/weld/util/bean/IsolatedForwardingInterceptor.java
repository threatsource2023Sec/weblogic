package org.jboss.weld.util.bean;

import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.interceptor.InvocationContext;

public abstract class IsolatedForwardingInterceptor extends IsolatedForwardingBean implements Interceptor {
   public abstract Interceptor delegate();

   public Set getInterceptorBindings() {
      return this.delegate().getInterceptorBindings();
   }

   public boolean intercepts(InterceptionType type) {
      return this.delegate().intercepts(type);
   }

   public Object intercept(InterceptionType type, Object instance, InvocationContext ctx) throws Exception {
      return this.delegate().intercept(type, instance, ctx);
   }

   public static class Impl extends IsolatedForwardingInterceptor {
      private final WrappedBeanHolder cartridge;

      public Impl(WrappedBeanHolder cartridge) {
         this.cartridge = cartridge;
      }

      public Interceptor delegate() {
         return (Interceptor)this.cartridge.getBean();
      }

      protected BeanAttributes attributes() {
         return this.cartridge.getAttributes();
      }
   }
}
