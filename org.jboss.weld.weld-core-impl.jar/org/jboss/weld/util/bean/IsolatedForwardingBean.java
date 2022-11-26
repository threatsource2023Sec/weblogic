package org.jboss.weld.util.bean;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.bean.WrappedContextual;
import org.jboss.weld.util.reflection.Reflections;

public abstract class IsolatedForwardingBean extends ForwardingBeanAttributes implements Bean, WrappedContextual {
   public abstract Bean delegate();

   public Object create(CreationalContext creationalContext) {
      return this.delegate().create(creationalContext);
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      this.delegate().destroy(instance, creationalContext);
   }

   public Class getBeanClass() {
      return this.delegate().getBeanClass();
   }

   public Set getInjectionPoints() {
      return this.delegate().getInjectionPoints();
   }

   public boolean isNullable() {
      return this.delegate().isNullable();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof IsolatedForwardingBean ? this.delegate().equals(((IsolatedForwardingBean)Reflections.cast(obj)).delegate()) : this.delegate().equals(obj);
   }

   public String toString() {
      return "ForwardingBean wrapping bean " + this.delegate().toString() + " and attributes " + this.attributes();
   }

   public static class Impl extends IsolatedForwardingBean {
      private final WrappedBeanHolder cartridge;

      public Impl(WrappedBeanHolder cartridge) {
         this.cartridge = cartridge;
      }

      public Bean delegate() {
         return this.cartridge.getBean();
      }

      protected BeanAttributes attributes() {
         return this.cartridge.getAttributes();
      }
   }
}
