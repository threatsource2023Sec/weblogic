package org.jboss.weld.bean;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.bean.ForwardingBeanAttributes;
import org.jboss.weld.util.reflection.Reflections;

public abstract class CommonBean extends ForwardingBeanAttributes implements Bean, WeldBean {
   private volatile BeanAttributes attributes;
   private final BeanIdentifier identifier;

   protected CommonBean(BeanAttributes attributes, BeanIdentifier identifier) {
      this.attributes = attributes;
      this.identifier = identifier;
   }

   protected Object unwrap(Object object) {
      if (object instanceof ForwardingBean) {
         return ((ForwardingBean)Reflections.cast(object)).delegate();
      } else if (object instanceof ForwardingInterceptor) {
         return ((ForwardingInterceptor)Reflections.cast(object)).delegate();
      } else {
         return object instanceof ForwardingDecorator ? ((ForwardingDecorator)Reflections.cast(object)).delegate() : object;
      }
   }

   public boolean equals(Object obj) {
      Object object = this.unwrap(obj);
      if (this == obj) {
         return true;
      } else if (object instanceof CommonBean) {
         CommonBean that = (CommonBean)object;
         return this.getIdentifier().equals(that.getIdentifier());
      } else {
         return false;
      }
   }

   protected BeanAttributes attributes() {
      return this.attributes;
   }

   public void setAttributes(BeanAttributes attributes) {
      this.attributes = attributes;
   }

   public boolean isNullable() {
      return false;
   }

   public int hashCode() {
      return this.identifier.hashCode();
   }

   public String getId() {
      return this.identifier.asString();
   }

   public BeanIdentifier getIdentifier() {
      return this.identifier;
   }

   public String toString() {
      return this.getId();
   }
}
