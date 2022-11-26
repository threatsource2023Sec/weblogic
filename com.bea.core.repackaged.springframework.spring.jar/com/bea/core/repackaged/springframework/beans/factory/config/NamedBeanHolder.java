package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.NamedBean;
import com.bea.core.repackaged.springframework.util.Assert;

public class NamedBeanHolder implements NamedBean {
   private final String beanName;
   private final Object beanInstance;

   public NamedBeanHolder(String beanName, Object beanInstance) {
      Assert.notNull(beanName, (String)"Bean name must not be null");
      this.beanName = beanName;
      this.beanInstance = beanInstance;
   }

   public String getBeanName() {
      return this.beanName;
   }

   public Object getBeanInstance() {
      return this.beanInstance;
   }
}
