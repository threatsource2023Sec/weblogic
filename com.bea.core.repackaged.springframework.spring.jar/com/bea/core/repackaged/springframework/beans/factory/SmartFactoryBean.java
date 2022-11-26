package com.bea.core.repackaged.springframework.beans.factory;

public interface SmartFactoryBean extends FactoryBean {
   default boolean isPrototype() {
      return false;
   }

   default boolean isEagerInit() {
      return false;
   }
}
