package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Locale;

public class StaticApplicationContext extends GenericApplicationContext {
   private final StaticMessageSource staticMessageSource;

   public StaticApplicationContext() throws BeansException {
      this((ApplicationContext)null);
   }

   public StaticApplicationContext(@Nullable ApplicationContext parent) throws BeansException {
      super(parent);
      this.staticMessageSource = new StaticMessageSource();
      this.getBeanFactory().registerSingleton("messageSource", this.staticMessageSource);
   }

   protected void assertBeanFactoryActive() {
   }

   public final StaticMessageSource getStaticMessageSource() {
      return this.staticMessageSource;
   }

   public void registerSingleton(String name, Class clazz) throws BeansException {
      GenericBeanDefinition bd = new GenericBeanDefinition();
      bd.setBeanClass(clazz);
      this.getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
   }

   public void registerSingleton(String name, Class clazz, MutablePropertyValues pvs) throws BeansException {
      GenericBeanDefinition bd = new GenericBeanDefinition();
      bd.setBeanClass(clazz);
      bd.setPropertyValues(pvs);
      this.getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
   }

   public void registerPrototype(String name, Class clazz) throws BeansException {
      GenericBeanDefinition bd = new GenericBeanDefinition();
      bd.setScope("prototype");
      bd.setBeanClass(clazz);
      this.getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
   }

   public void registerPrototype(String name, Class clazz, MutablePropertyValues pvs) throws BeansException {
      GenericBeanDefinition bd = new GenericBeanDefinition();
      bd.setScope("prototype");
      bd.setBeanClass(clazz);
      bd.setPropertyValues(pvs);
      this.getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
   }

   public void addMessage(String code, Locale locale, String defaultMessage) {
      this.getStaticMessageSource().addMessage(code, locale, defaultMessage);
   }
}
