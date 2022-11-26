package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionCustomizer;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.io.support.ResourcePatternResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {
   private final DefaultListableBeanFactory beanFactory;
   @Nullable
   private ResourceLoader resourceLoader;
   private boolean customClassLoader;
   private final AtomicBoolean refreshed;

   public GenericApplicationContext() {
      this.customClassLoader = false;
      this.refreshed = new AtomicBoolean();
      this.beanFactory = new DefaultListableBeanFactory();
   }

   public GenericApplicationContext(DefaultListableBeanFactory beanFactory) {
      this.customClassLoader = false;
      this.refreshed = new AtomicBoolean();
      Assert.notNull(beanFactory, (String)"BeanFactory must not be null");
      this.beanFactory = beanFactory;
   }

   public GenericApplicationContext(@Nullable ApplicationContext parent) {
      this();
      this.setParent(parent);
   }

   public GenericApplicationContext(DefaultListableBeanFactory beanFactory, ApplicationContext parent) {
      this(beanFactory);
      this.setParent(parent);
   }

   public void setParent(@Nullable ApplicationContext parent) {
      super.setParent(parent);
      this.beanFactory.setParentBeanFactory(this.getInternalParentBeanFactory());
   }

   public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
      this.beanFactory.setAllowBeanDefinitionOverriding(allowBeanDefinitionOverriding);
   }

   public void setAllowCircularReferences(boolean allowCircularReferences) {
      this.beanFactory.setAllowCircularReferences(allowCircularReferences);
   }

   public void setResourceLoader(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
   }

   public Resource getResource(String location) {
      return this.resourceLoader != null ? this.resourceLoader.getResource(location) : super.getResource(location);
   }

   public Resource[] getResources(String locationPattern) throws IOException {
      return this.resourceLoader instanceof ResourcePatternResolver ? ((ResourcePatternResolver)this.resourceLoader).getResources(locationPattern) : super.getResources(locationPattern);
   }

   public void setClassLoader(@Nullable ClassLoader classLoader) {
      super.setClassLoader(classLoader);
      this.customClassLoader = true;
   }

   @Nullable
   public ClassLoader getClassLoader() {
      return this.resourceLoader != null && !this.customClassLoader ? this.resourceLoader.getClassLoader() : super.getClassLoader();
   }

   protected final void refreshBeanFactory() throws IllegalStateException {
      if (!this.refreshed.compareAndSet(false, true)) {
         throw new IllegalStateException("GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once");
      } else {
         this.beanFactory.setSerializationId(this.getId());
      }
   }

   protected void cancelRefresh(BeansException ex) {
      this.beanFactory.setSerializationId((String)null);
      super.cancelRefresh(ex);
   }

   protected final void closeBeanFactory() {
      this.beanFactory.setSerializationId((String)null);
   }

   public final ConfigurableListableBeanFactory getBeanFactory() {
      return this.beanFactory;
   }

   public final DefaultListableBeanFactory getDefaultListableBeanFactory() {
      return this.beanFactory;
   }

   public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
      this.assertBeanFactoryActive();
      return this.beanFactory;
   }

   public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
      this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
   }

   public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
      this.beanFactory.removeBeanDefinition(beanName);
   }

   public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
      return this.beanFactory.getBeanDefinition(beanName);
   }

   public boolean isBeanNameInUse(String beanName) {
      return this.beanFactory.isBeanNameInUse(beanName);
   }

   public void registerAlias(String beanName, String alias) {
      this.beanFactory.registerAlias(beanName, alias);
   }

   public void removeAlias(String alias) {
      this.beanFactory.removeAlias(alias);
   }

   public boolean isAlias(String beanName) {
      return this.beanFactory.isAlias(beanName);
   }

   public final void registerBean(Class beanClass, BeanDefinitionCustomizer... customizers) {
      this.registerBean((String)null, beanClass, (Supplier)null, customizers);
   }

   public final void registerBean(@Nullable String beanName, Class beanClass, BeanDefinitionCustomizer... customizers) {
      this.registerBean(beanName, beanClass, (Supplier)null, customizers);
   }

   public final void registerBean(Class beanClass, Supplier supplier, BeanDefinitionCustomizer... customizers) {
      this.registerBean((String)null, beanClass, supplier, customizers);
   }

   public void registerBean(@Nullable String beanName, Class beanClass, @Nullable Supplier supplier, BeanDefinitionCustomizer... customizers) {
      ClassDerivedBeanDefinition beanDefinition = new ClassDerivedBeanDefinition(beanClass);
      if (supplier != null) {
         beanDefinition.setInstanceSupplier(supplier);
      }

      BeanDefinitionCustomizer[] var6 = customizers;
      int var7 = customizers.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         BeanDefinitionCustomizer customizer = var6[var8];
         customizer.customize(beanDefinition);
      }

      String nameToUse = beanName != null ? beanName : beanClass.getName();
      this.registerBeanDefinition(nameToUse, beanDefinition);
   }

   private static class ClassDerivedBeanDefinition extends RootBeanDefinition {
      public ClassDerivedBeanDefinition(Class beanClass) {
         super(beanClass);
      }

      public ClassDerivedBeanDefinition(ClassDerivedBeanDefinition original) {
         super((RootBeanDefinition)original);
      }

      @Nullable
      public Constructor[] getPreferredConstructors() {
         Class clazz = this.getBeanClass();
         Constructor primaryCtor = BeanUtils.findPrimaryConstructor(clazz);
         if (primaryCtor != null) {
            return new Constructor[]{primaryCtor};
         } else {
            Constructor[] publicCtors = clazz.getConstructors();
            return publicCtors.length > 0 ? publicCtors : null;
         }
      }

      public RootBeanDefinition cloneBeanDefinition() {
         return new ClassDerivedBeanDefinition(this);
      }
   }
}
