package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class RootBeanDefinition extends AbstractBeanDefinition {
   @Nullable
   private BeanDefinitionHolder decoratedDefinition;
   @Nullable
   private AnnotatedElement qualifiedElement;
   boolean allowCaching = true;
   boolean isFactoryMethodUnique = false;
   @Nullable
   volatile ResolvableType targetType;
   @Nullable
   volatile Class resolvedTargetType;
   @Nullable
   volatile ResolvableType factoryMethodReturnType;
   @Nullable
   volatile Method factoryMethodToIntrospect;
   final Object constructorArgumentLock = new Object();
   @Nullable
   Executable resolvedConstructorOrFactoryMethod;
   boolean constructorArgumentsResolved = false;
   @Nullable
   Object[] resolvedConstructorArguments;
   @Nullable
   Object[] preparedConstructorArguments;
   final Object postProcessingLock = new Object();
   boolean postProcessed = false;
   @Nullable
   volatile Boolean beforeInstantiationResolved;
   @Nullable
   private Set externallyManagedConfigMembers;
   @Nullable
   private Set externallyManagedInitMethods;
   @Nullable
   private Set externallyManagedDestroyMethods;

   public RootBeanDefinition() {
   }

   public RootBeanDefinition(@Nullable Class beanClass) {
      this.setBeanClass(beanClass);
   }

   public RootBeanDefinition(@Nullable Class beanClass, @Nullable Supplier instanceSupplier) {
      this.setBeanClass(beanClass);
      this.setInstanceSupplier(instanceSupplier);
   }

   public RootBeanDefinition(@Nullable Class beanClass, String scope, @Nullable Supplier instanceSupplier) {
      this.setBeanClass(beanClass);
      this.setScope(scope);
      this.setInstanceSupplier(instanceSupplier);
   }

   public RootBeanDefinition(@Nullable Class beanClass, int autowireMode, boolean dependencyCheck) {
      this.setBeanClass(beanClass);
      this.setAutowireMode(autowireMode);
      if (dependencyCheck && this.getResolvedAutowireMode() != 3) {
         this.setDependencyCheck(1);
      }

   }

   public RootBeanDefinition(@Nullable Class beanClass, @Nullable ConstructorArgumentValues cargs, @Nullable MutablePropertyValues pvs) {
      super(cargs, pvs);
      this.setBeanClass(beanClass);
   }

   public RootBeanDefinition(String beanClassName) {
      this.setBeanClassName(beanClassName);
   }

   public RootBeanDefinition(String beanClassName, ConstructorArgumentValues cargs, MutablePropertyValues pvs) {
      super(cargs, pvs);
      this.setBeanClassName(beanClassName);
   }

   public RootBeanDefinition(RootBeanDefinition original) {
      super(original);
      this.decoratedDefinition = original.decoratedDefinition;
      this.qualifiedElement = original.qualifiedElement;
      this.allowCaching = original.allowCaching;
      this.isFactoryMethodUnique = original.isFactoryMethodUnique;
      this.targetType = original.targetType;
   }

   RootBeanDefinition(BeanDefinition original) {
      super(original);
   }

   public String getParentName() {
      return null;
   }

   public void setParentName(@Nullable String parentName) {
      if (parentName != null) {
         throw new IllegalArgumentException("Root bean cannot be changed into a child bean with parent reference");
      }
   }

   public void setDecoratedDefinition(@Nullable BeanDefinitionHolder decoratedDefinition) {
      this.decoratedDefinition = decoratedDefinition;
   }

   @Nullable
   public BeanDefinitionHolder getDecoratedDefinition() {
      return this.decoratedDefinition;
   }

   public void setQualifiedElement(@Nullable AnnotatedElement qualifiedElement) {
      this.qualifiedElement = qualifiedElement;
   }

   @Nullable
   public AnnotatedElement getQualifiedElement() {
      return this.qualifiedElement;
   }

   public void setTargetType(ResolvableType targetType) {
      this.targetType = targetType;
   }

   public void setTargetType(@Nullable Class targetType) {
      this.targetType = targetType != null ? ResolvableType.forClass(targetType) : null;
   }

   @Nullable
   public Class getTargetType() {
      if (this.resolvedTargetType != null) {
         return this.resolvedTargetType;
      } else {
         ResolvableType targetType = this.targetType;
         return targetType != null ? targetType.resolve() : null;
      }
   }

   public ResolvableType getResolvableType() {
      ResolvableType targetType = this.targetType;
      return targetType != null ? targetType : ResolvableType.forClass(this.getBeanClass());
   }

   @Nullable
   public Constructor[] getPreferredConstructors() {
      return null;
   }

   public void setUniqueFactoryMethodName(String name) {
      Assert.hasText(name, "Factory method name must not be empty");
      this.setFactoryMethodName(name);
      this.isFactoryMethodUnique = true;
   }

   public boolean isFactoryMethod(Method candidate) {
      return candidate.getName().equals(this.getFactoryMethodName());
   }

   @Nullable
   public Method getResolvedFactoryMethod() {
      return this.factoryMethodToIntrospect;
   }

   public void registerExternallyManagedConfigMember(Member configMember) {
      synchronized(this.postProcessingLock) {
         if (this.externallyManagedConfigMembers == null) {
            this.externallyManagedConfigMembers = new HashSet(1);
         }

         this.externallyManagedConfigMembers.add(configMember);
      }
   }

   public boolean isExternallyManagedConfigMember(Member configMember) {
      synchronized(this.postProcessingLock) {
         return this.externallyManagedConfigMembers != null && this.externallyManagedConfigMembers.contains(configMember);
      }
   }

   public void registerExternallyManagedInitMethod(String initMethod) {
      synchronized(this.postProcessingLock) {
         if (this.externallyManagedInitMethods == null) {
            this.externallyManagedInitMethods = new HashSet(1);
         }

         this.externallyManagedInitMethods.add(initMethod);
      }
   }

   public boolean isExternallyManagedInitMethod(String initMethod) {
      synchronized(this.postProcessingLock) {
         return this.externallyManagedInitMethods != null && this.externallyManagedInitMethods.contains(initMethod);
      }
   }

   public void registerExternallyManagedDestroyMethod(String destroyMethod) {
      synchronized(this.postProcessingLock) {
         if (this.externallyManagedDestroyMethods == null) {
            this.externallyManagedDestroyMethods = new HashSet(1);
         }

         this.externallyManagedDestroyMethods.add(destroyMethod);
      }
   }

   public boolean isExternallyManagedDestroyMethod(String destroyMethod) {
      synchronized(this.postProcessingLock) {
         return this.externallyManagedDestroyMethods != null && this.externallyManagedDestroyMethods.contains(destroyMethod);
      }
   }

   public RootBeanDefinition cloneBeanDefinition() {
      return new RootBeanDefinition(this);
   }

   public boolean equals(Object other) {
      return this == other || other instanceof RootBeanDefinition && super.equals(other);
   }

   public String toString() {
      return "Root bean: " + super.toString();
   }
}
