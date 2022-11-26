package org.jboss.weld.util.bean;

import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Decorator;

public abstract class IsolatedForwardingDecorator extends IsolatedForwardingBean implements Decorator {
   public abstract Decorator delegate();

   public Type getDelegateType() {
      return this.delegate().getDelegateType();
   }

   public Set getDelegateQualifiers() {
      return this.delegate().getDelegateQualifiers();
   }

   public Set getDecoratedTypes() {
      return this.delegate().getDecoratedTypes();
   }

   public static class Impl extends IsolatedForwardingDecorator {
      private final WrappedBeanHolder cartridge;

      public Impl(WrappedBeanHolder cartridge) {
         this.cartridge = cartridge;
      }

      public Decorator delegate() {
         return (Decorator)this.cartridge.getBean();
      }

      protected BeanAttributes attributes() {
         return this.cartridge.getAttributes();
      }
   }
}
