package com.bea.core.repackaged.springframework.aop.scope;

import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class DefaultScopedObject implements ScopedObject, Serializable {
   private final ConfigurableBeanFactory beanFactory;
   private final String targetBeanName;

   public DefaultScopedObject(ConfigurableBeanFactory beanFactory, String targetBeanName) {
      Assert.notNull(beanFactory, (String)"BeanFactory must not be null");
      Assert.hasText(targetBeanName, "'targetBeanName' must not be empty");
      this.beanFactory = beanFactory;
      this.targetBeanName = targetBeanName;
   }

   public Object getTargetObject() {
      return this.beanFactory.getBean(this.targetBeanName);
   }

   public void removeFromScope() {
      this.beanFactory.destroyScopedBean(this.targetBeanName);
   }
}
