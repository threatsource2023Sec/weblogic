package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.AbstractComponentDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class PointcutComponentDefinition extends AbstractComponentDefinition {
   private final String pointcutBeanName;
   private final BeanDefinition pointcutDefinition;
   private final String description;

   public PointcutComponentDefinition(String pointcutBeanName, BeanDefinition pointcutDefinition, String expression) {
      Assert.notNull(pointcutBeanName, (String)"Bean name must not be null");
      Assert.notNull(pointcutDefinition, (String)"Pointcut definition must not be null");
      Assert.notNull(expression, (String)"Expression must not be null");
      this.pointcutBeanName = pointcutBeanName;
      this.pointcutDefinition = pointcutDefinition;
      this.description = "Pointcut <name='" + pointcutBeanName + "', expression=[" + expression + "]>";
   }

   public String getName() {
      return this.pointcutBeanName;
   }

   public String getDescription() {
      return this.description;
   }

   public BeanDefinition[] getBeanDefinitions() {
      return new BeanDefinition[]{this.pointcutDefinition};
   }

   @Nullable
   public Object getSource() {
      return this.pointcutDefinition.getSource();
   }
}
