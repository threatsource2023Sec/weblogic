package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.core.SimpleAliasRegistry;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanDefinitionRegistry extends SimpleAliasRegistry implements BeanDefinitionRegistry {
   private final Map beanDefinitionMap = new ConcurrentHashMap(64);

   public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
      Assert.hasText(beanName, "'beanName' must not be empty");
      Assert.notNull(beanDefinition, (String)"BeanDefinition must not be null");
      this.beanDefinitionMap.put(beanName, beanDefinition);
   }

   public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
      if (this.beanDefinitionMap.remove(beanName) == null) {
         throw new NoSuchBeanDefinitionException(beanName);
      }
   }

   public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
      BeanDefinition bd = (BeanDefinition)this.beanDefinitionMap.get(beanName);
      if (bd == null) {
         throw new NoSuchBeanDefinitionException(beanName);
      } else {
         return bd;
      }
   }

   public boolean containsBeanDefinition(String beanName) {
      return this.beanDefinitionMap.containsKey(beanName);
   }

   public String[] getBeanDefinitionNames() {
      return StringUtils.toStringArray((Collection)this.beanDefinitionMap.keySet());
   }

   public int getBeanDefinitionCount() {
      return this.beanDefinitionMap.size();
   }

   public boolean isBeanNameInUse(String beanName) {
      return this.isAlias(beanName) || this.containsBeanDefinition(beanName);
   }
}
