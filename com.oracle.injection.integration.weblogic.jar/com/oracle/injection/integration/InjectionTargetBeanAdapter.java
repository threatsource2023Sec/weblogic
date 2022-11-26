package com.oracle.injection.integration;

import java.lang.reflect.Method;
import javax.enterprise.inject.spi.InjectionPoint;
import weblogic.j2ee.descriptor.InjectionTargetBean;

class InjectionTargetBeanAdapter implements InjectionTargetBean {
   private final InjectionPoint m_injectionPoint;

   InjectionTargetBeanAdapter(InjectionPoint injectionPoint) {
      if (injectionPoint == null) {
         throw new IllegalArgumentException("InjectionPoint argument cannot be null");
      } else {
         this.m_injectionPoint = injectionPoint;
      }
   }

   public String getInjectionTargetClass() {
      return this.m_injectionPoint.getMember().getDeclaringClass().getName();
   }

   public void setInjectionTargetClass(String injectionTargetClass) {
   }

   public String getInjectionTargetName() {
      if (this.m_injectionPoint.getMember() instanceof Method) {
         String name = this.m_injectionPoint.getMember().getName();
         if (name.startsWith("set")) {
            name = name.substring(3);
         }

         return name;
      } else {
         return this.m_injectionPoint.getMember().getName();
      }
   }

   public void setInjectionTargetName(String injectionTargetName) {
   }
}
