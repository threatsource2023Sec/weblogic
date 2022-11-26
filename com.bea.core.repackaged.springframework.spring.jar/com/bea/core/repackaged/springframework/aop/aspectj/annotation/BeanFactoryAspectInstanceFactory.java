package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.annotation.OrderUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.Serializable;

public class BeanFactoryAspectInstanceFactory implements MetadataAwareAspectInstanceFactory, Serializable {
   private final BeanFactory beanFactory;
   private final String name;
   private final AspectMetadata aspectMetadata;

   public BeanFactoryAspectInstanceFactory(BeanFactory beanFactory, String name) {
      this(beanFactory, name, (Class)null);
   }

   public BeanFactoryAspectInstanceFactory(BeanFactory beanFactory, String name, @Nullable Class type) {
      Assert.notNull(beanFactory, (String)"BeanFactory must not be null");
      Assert.notNull(name, (String)"Bean name must not be null");
      this.beanFactory = beanFactory;
      this.name = name;
      Class resolvedType = type;
      if (type == null) {
         resolvedType = beanFactory.getType(name);
         Assert.notNull(resolvedType, (String)"Unresolvable bean type - explicitly specify the aspect class");
      }

      this.aspectMetadata = new AspectMetadata(resolvedType, name);
   }

   public Object getAspectInstance() {
      return this.beanFactory.getBean(this.name);
   }

   @Nullable
   public ClassLoader getAspectClassLoader() {
      return this.beanFactory instanceof ConfigurableBeanFactory ? ((ConfigurableBeanFactory)this.beanFactory).getBeanClassLoader() : ClassUtils.getDefaultClassLoader();
   }

   public AspectMetadata getAspectMetadata() {
      return this.aspectMetadata;
   }

   @Nullable
   public Object getAspectCreationMutex() {
      if (this.beanFactory.isSingleton(this.name)) {
         return null;
      } else {
         return this.beanFactory instanceof ConfigurableBeanFactory ? ((ConfigurableBeanFactory)this.beanFactory).getSingletonMutex() : this;
      }
   }

   public int getOrder() {
      Class type = this.beanFactory.getType(this.name);
      if (type != null) {
         return Ordered.class.isAssignableFrom(type) && this.beanFactory.isSingleton(this.name) ? ((Ordered)this.beanFactory.getBean(this.name)).getOrder() : OrderUtils.getOrder(type, Integer.MAX_VALUE);
      } else {
         return Integer.MAX_VALUE;
      }
   }

   public String toString() {
      return this.getClass().getSimpleName() + ": bean name '" + this.name + "'";
   }
}
