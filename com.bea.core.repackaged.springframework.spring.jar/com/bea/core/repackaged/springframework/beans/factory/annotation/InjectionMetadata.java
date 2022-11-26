package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class InjectionMetadata {
   private static final Log logger = LogFactory.getLog(InjectionMetadata.class);
   private final Class targetClass;
   private final Collection injectedElements;
   @Nullable
   private volatile Set checkedElements;

   public InjectionMetadata(Class targetClass, Collection elements) {
      this.targetClass = targetClass;
      this.injectedElements = elements;
   }

   public void checkConfigMembers(RootBeanDefinition beanDefinition) {
      Set checkedElements = new LinkedHashSet(this.injectedElements.size());
      Iterator var3 = this.injectedElements.iterator();

      while(var3.hasNext()) {
         InjectedElement element = (InjectedElement)var3.next();
         Member member = element.getMember();
         if (!beanDefinition.isExternallyManagedConfigMember(member)) {
            beanDefinition.registerExternallyManagedConfigMember(member);
            checkedElements.add(element);
            if (logger.isTraceEnabled()) {
               logger.trace("Registered injected element on class [" + this.targetClass.getName() + "]: " + element);
            }
         }
      }

      this.checkedElements = checkedElements;
   }

   public void inject(Object target, @Nullable String beanName, @Nullable PropertyValues pvs) throws Throwable {
      Collection checkedElements = this.checkedElements;
      Collection elementsToIterate = checkedElements != null ? checkedElements : this.injectedElements;
      InjectedElement element;
      if (!((Collection)elementsToIterate).isEmpty()) {
         for(Iterator var6 = ((Collection)elementsToIterate).iterator(); var6.hasNext(); element.inject(target, beanName, pvs)) {
            element = (InjectedElement)var6.next();
            if (logger.isTraceEnabled()) {
               logger.trace("Processing injected element of bean '" + beanName + "': " + element);
            }
         }
      }

   }

   public void clear(@Nullable PropertyValues pvs) {
      Collection checkedElements = this.checkedElements;
      Collection elementsToIterate = checkedElements != null ? checkedElements : this.injectedElements;
      if (!((Collection)elementsToIterate).isEmpty()) {
         Iterator var4 = ((Collection)elementsToIterate).iterator();

         while(var4.hasNext()) {
            InjectedElement element = (InjectedElement)var4.next();
            element.clearPropertySkipping(pvs);
         }
      }

   }

   public static boolean needsRefresh(@Nullable InjectionMetadata metadata, Class clazz) {
      return metadata == null || metadata.targetClass != clazz;
   }

   public abstract static class InjectedElement {
      protected final Member member;
      protected final boolean isField;
      @Nullable
      protected final PropertyDescriptor pd;
      @Nullable
      protected volatile Boolean skip;

      protected InjectedElement(Member member, @Nullable PropertyDescriptor pd) {
         this.member = member;
         this.isField = member instanceof Field;
         this.pd = pd;
      }

      public final Member getMember() {
         return this.member;
      }

      protected final Class getResourceType() {
         if (this.isField) {
            return ((Field)this.member).getType();
         } else {
            return this.pd != null ? this.pd.getPropertyType() : ((Method)this.member).getParameterTypes()[0];
         }
      }

      protected final void checkResourceType(Class resourceType) {
         Class fieldType;
         if (this.isField) {
            fieldType = ((Field)this.member).getType();
            if (!resourceType.isAssignableFrom(fieldType) && !fieldType.isAssignableFrom(resourceType)) {
               throw new IllegalStateException("Specified field type [" + fieldType + "] is incompatible with resource type [" + resourceType.getName() + "]");
            }
         } else {
            fieldType = this.pd != null ? this.pd.getPropertyType() : ((Method)this.member).getParameterTypes()[0];
            if (!resourceType.isAssignableFrom(fieldType) && !fieldType.isAssignableFrom(resourceType)) {
               throw new IllegalStateException("Specified parameter type [" + fieldType + "] is incompatible with resource type [" + resourceType.getName() + "]");
            }
         }

      }

      protected void inject(Object target, @Nullable String requestingBeanName, @Nullable PropertyValues pvs) throws Throwable {
         if (this.isField) {
            Field field = (Field)this.member;
            ReflectionUtils.makeAccessible(field);
            field.set(target, this.getResourceToInject(target, requestingBeanName));
         } else {
            if (this.checkPropertySkipping(pvs)) {
               return;
            }

            try {
               Method method = (Method)this.member;
               ReflectionUtils.makeAccessible(method);
               method.invoke(target, this.getResourceToInject(target, requestingBeanName));
            } catch (InvocationTargetException var5) {
               throw var5.getTargetException();
            }
         }

      }

      protected boolean checkPropertySkipping(@Nullable PropertyValues pvs) {
         Boolean skip = this.skip;
         if (skip != null) {
            return skip;
         } else if (pvs == null) {
            this.skip = false;
            return false;
         } else {
            synchronized(pvs) {
               skip = this.skip;
               if (skip != null) {
                  return skip;
               } else {
                  if (this.pd != null) {
                     if (pvs.contains(this.pd.getName())) {
                        this.skip = true;
                        return true;
                     }

                     if (pvs instanceof MutablePropertyValues) {
                        ((MutablePropertyValues)pvs).registerProcessedProperty(this.pd.getName());
                     }
                  }

                  this.skip = false;
                  return false;
               }
            }
         }
      }

      protected void clearPropertySkipping(@Nullable PropertyValues pvs) {
         if (pvs != null) {
            synchronized(pvs) {
               if (Boolean.FALSE.equals(this.skip) && this.pd != null && pvs instanceof MutablePropertyValues) {
                  ((MutablePropertyValues)pvs).clearProcessedProperty(this.pd.getName());
               }

            }
         }
      }

      @Nullable
      protected Object getResourceToInject(Object target, @Nullable String requestingBeanName) {
         return null;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof InjectedElement)) {
            return false;
         } else {
            InjectedElement otherElement = (InjectedElement)other;
            return this.member.equals(otherElement.member);
         }
      }

      public int hashCode() {
         return this.member.getClass().hashCode() * 29 + this.member.getName().hashCode();
      }

      public String toString() {
         return this.getClass().getSimpleName() + " for " + this.member;
      }
   }
}
