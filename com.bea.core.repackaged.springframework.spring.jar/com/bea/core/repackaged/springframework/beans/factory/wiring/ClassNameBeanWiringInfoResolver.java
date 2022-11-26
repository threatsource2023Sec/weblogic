package com.bea.core.repackaged.springframework.beans.factory.wiring;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class ClassNameBeanWiringInfoResolver implements BeanWiringInfoResolver {
   public BeanWiringInfo resolveWiringInfo(Object beanInstance) {
      Assert.notNull(beanInstance, "Bean instance must not be null");
      return new BeanWiringInfo(ClassUtils.getUserClass(beanInstance).getName(), true);
   }
}
