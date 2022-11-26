package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Method;

public class ExtendedBeanInfoFactory implements BeanInfoFactory, Ordered {
   @Nullable
   public BeanInfo getBeanInfo(Class beanClass) throws IntrospectionException {
      return this.supports(beanClass) ? new ExtendedBeanInfo(Introspector.getBeanInfo(beanClass)) : null;
   }

   private boolean supports(Class beanClass) {
      Method[] var2 = beanClass.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         if (ExtendedBeanInfo.isCandidateWriteMethod(method)) {
            return true;
         }
      }

      return false;
   }

   public int getOrder() {
      return Integer.MAX_VALUE;
   }
}
