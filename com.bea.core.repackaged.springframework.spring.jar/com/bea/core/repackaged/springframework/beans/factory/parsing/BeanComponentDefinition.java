package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.beans.PropertyValue;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanReference;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BeanComponentDefinition extends BeanDefinitionHolder implements ComponentDefinition {
   private BeanDefinition[] innerBeanDefinitions;
   private BeanReference[] beanReferences;

   public BeanComponentDefinition(BeanDefinition beanDefinition, String beanName) {
      this(new BeanDefinitionHolder(beanDefinition, beanName));
   }

   public BeanComponentDefinition(BeanDefinition beanDefinition, String beanName, @Nullable String[] aliases) {
      this(new BeanDefinitionHolder(beanDefinition, beanName, aliases));
   }

   public BeanComponentDefinition(BeanDefinitionHolder beanDefinitionHolder) {
      super(beanDefinitionHolder);
      List innerBeans = new ArrayList();
      List references = new ArrayList();
      PropertyValues propertyValues = beanDefinitionHolder.getBeanDefinition().getPropertyValues();
      PropertyValue[] var5 = propertyValues.getPropertyValues();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         PropertyValue propertyValue = var5[var7];
         Object value = propertyValue.getValue();
         if (value instanceof BeanDefinitionHolder) {
            innerBeans.add(((BeanDefinitionHolder)value).getBeanDefinition());
         } else if (value instanceof BeanDefinition) {
            innerBeans.add((BeanDefinition)value);
         } else if (value instanceof BeanReference) {
            references.add((BeanReference)value);
         }
      }

      this.innerBeanDefinitions = (BeanDefinition[])innerBeans.toArray(new BeanDefinition[0]);
      this.beanReferences = (BeanReference[])references.toArray(new BeanReference[0]);
   }

   public String getName() {
      return this.getBeanName();
   }

   public String getDescription() {
      return this.getShortDescription();
   }

   public BeanDefinition[] getBeanDefinitions() {
      return new BeanDefinition[]{this.getBeanDefinition()};
   }

   public BeanDefinition[] getInnerBeanDefinitions() {
      return this.innerBeanDefinitions;
   }

   public BeanReference[] getBeanReferences() {
      return this.beanReferences;
   }

   public String toString() {
      return this.getDescription();
   }

   public boolean equals(Object other) {
      return this == other || other instanceof BeanComponentDefinition && super.equals(other);
   }
}
