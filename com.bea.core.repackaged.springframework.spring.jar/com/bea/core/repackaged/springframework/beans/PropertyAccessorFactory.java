package com.bea.core.repackaged.springframework.beans;

public final class PropertyAccessorFactory {
   private PropertyAccessorFactory() {
   }

   public static BeanWrapper forBeanPropertyAccess(Object target) {
      return new BeanWrapperImpl(target);
   }

   public static ConfigurablePropertyAccessor forDirectFieldAccess(Object target) {
      return new DirectFieldAccessor(target);
   }
}
