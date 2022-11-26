package com.oracle.pitchfork.spi;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.InstantiationStrategy;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.oracle.pitchfork.interfaces.ComponentFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class WLSInstantiationStrategy implements InstantiationStrategy {
   private final ComponentFactory componentFactory;

   public WLSInstantiationStrategy(ComponentFactory componentFactory) {
      this.componentFactory = componentFactory;
   }

   public Object instantiate(RootBeanDefinition beanDefinition, String beanName, BeanFactory owner) throws BeansException {
      return this.componentFactory.newInstance(beanDefinition.getBeanClass());
   }

   public Object instantiate(RootBeanDefinition beanDefinition, String beanName, BeanFactory owner, Constructor ctor, Object[] args) throws BeansException {
      throw new UnsupportedOperationException("constructor injection is not supported for third-party ComponentFactories");
   }

   public Object instantiate(RootBeanDefinition beanDefinition, String beanName, BeanFactory owner, Object factoryBean, Method factoryMethod, Object[] args) throws BeansException {
      throw new UnsupportedOperationException("factory methods are not supported for third-party ComponentFactories");
   }
}
