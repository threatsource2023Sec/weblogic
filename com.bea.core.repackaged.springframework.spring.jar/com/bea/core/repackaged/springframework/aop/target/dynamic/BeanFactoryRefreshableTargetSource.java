package com.bea.core.repackaged.springframework.aop.target.dynamic;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.util.Assert;

public class BeanFactoryRefreshableTargetSource extends AbstractRefreshableTargetSource {
   private final BeanFactory beanFactory;
   private final String beanName;

   public BeanFactoryRefreshableTargetSource(BeanFactory beanFactory, String beanName) {
      Assert.notNull(beanFactory, (String)"BeanFactory is required");
      Assert.notNull(beanName, (String)"Bean name is required");
      this.beanFactory = beanFactory;
      this.beanName = beanName;
   }

   protected final Object freshTarget() {
      return this.obtainFreshBean(this.beanFactory, this.beanName);
   }

   protected Object obtainFreshBean(BeanFactory beanFactory, String beanName) {
      return beanFactory.getBean(beanName);
   }
}
