package org.jboss.weld.util.bean;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;

public class WrappedBeanHolder {
   private final BeanAttributes attributes;
   private final Bean bean;

   public static WrappedBeanHolder of(BeanAttributes attributes, Bean bean) {
      return new WrappedBeanHolder(attributes, bean);
   }

   public WrappedBeanHolder(BeanAttributes attributes, Bean bean) {
      this.attributes = attributes;
      this.bean = bean;
   }

   public BeanAttributes getAttributes() {
      return this.attributes;
   }

   public Bean getBean() {
      return this.bean;
   }
}
