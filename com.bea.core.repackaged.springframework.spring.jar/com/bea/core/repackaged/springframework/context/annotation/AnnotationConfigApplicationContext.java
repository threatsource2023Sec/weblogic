package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionCustomizer;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.bea.core.repackaged.springframework.core.env.ConfigurableEnvironment;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.function.Supplier;

public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {
   private final AnnotatedBeanDefinitionReader reader;
   private final ClassPathBeanDefinitionScanner scanner;

   public AnnotationConfigApplicationContext() {
      this.reader = new AnnotatedBeanDefinitionReader(this);
      this.scanner = new ClassPathBeanDefinitionScanner(this);
   }

   public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
      super(beanFactory);
      this.reader = new AnnotatedBeanDefinitionReader(this);
      this.scanner = new ClassPathBeanDefinitionScanner(this);
   }

   public AnnotationConfigApplicationContext(Class... componentClasses) {
      this();
      this.register(componentClasses);
      this.refresh();
   }

   public AnnotationConfigApplicationContext(String... basePackages) {
      this();
      this.scan(basePackages);
      this.refresh();
   }

   public void setEnvironment(ConfigurableEnvironment environment) {
      super.setEnvironment(environment);
      this.reader.setEnvironment(environment);
      this.scanner.setEnvironment(environment);
   }

   public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
      this.reader.setBeanNameGenerator(beanNameGenerator);
      this.scanner.setBeanNameGenerator(beanNameGenerator);
      this.getBeanFactory().registerSingleton("com.bea.core.repackaged.springframework.context.annotation.internalConfigurationBeanNameGenerator", beanNameGenerator);
   }

   public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
      this.reader.setScopeMetadataResolver(scopeMetadataResolver);
      this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
   }

   public void register(Class... componentClasses) {
      Assert.notEmpty((Object[])componentClasses, (String)"At least one component class must be specified");
      this.reader.register(componentClasses);
   }

   public void scan(String... basePackages) {
      Assert.notEmpty((Object[])basePackages, (String)"At least one base package must be specified");
      this.scanner.scan(basePackages);
   }

   public void registerBean(Class beanClass, Object... constructorArguments) {
      this.registerBean((String)null, beanClass, constructorArguments);
   }

   public void registerBean(@Nullable String beanName, Class beanClass, Object... constructorArguments) {
      this.reader.doRegisterBean(beanClass, (Supplier)null, beanName, (Class[])null, (bd) -> {
         Object[] var2 = constructorArguments;
         int var3 = constructorArguments.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object arg = var2[var4];
            bd.getConstructorArgumentValues().addGenericArgumentValue(arg);
         }

      });
   }

   public void registerBean(@Nullable String beanName, Class beanClass, @Nullable Supplier supplier, BeanDefinitionCustomizer... customizers) {
      this.reader.doRegisterBean(beanClass, supplier, beanName, (Class[])null, customizers);
   }
}
