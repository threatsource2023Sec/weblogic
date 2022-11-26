package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanIsNotAFactoryException;
import com.bea.core.repackaged.springframework.beans.factory.BeanNotOfRequiredTypeException;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.ObjectProvider;
import com.bea.core.repackaged.springframework.beans.factory.SmartFactoryBean;
import com.bea.core.repackaged.springframework.core.OrderComparator;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class StaticListableBeanFactory implements ListableBeanFactory {
   private final Map beans;

   public StaticListableBeanFactory() {
      this.beans = new LinkedHashMap();
   }

   public StaticListableBeanFactory(Map beans) {
      Assert.notNull(beans, (String)"Beans Map must not be null");
      this.beans = beans;
   }

   public void addBean(String name, Object bean) {
      this.beans.put(name, bean);
   }

   public Object getBean(String name) throws BeansException {
      String beanName = BeanFactoryUtils.transformedBeanName(name);
      Object bean = this.beans.get(beanName);
      if (bean == null) {
         throw new NoSuchBeanDefinitionException(beanName, "Defined beans are [" + StringUtils.collectionToCommaDelimitedString(this.beans.keySet()) + "]");
      } else if (BeanFactoryUtils.isFactoryDereference(name) && !(bean instanceof FactoryBean)) {
         throw new BeanIsNotAFactoryException(beanName, bean.getClass());
      } else if (bean instanceof FactoryBean && !BeanFactoryUtils.isFactoryDereference(name)) {
         try {
            Object exposedObject = ((FactoryBean)bean).getObject();
            if (exposedObject == null) {
               throw new BeanCreationException(beanName, "FactoryBean exposed null object");
            } else {
               return exposedObject;
            }
         } catch (Exception var5) {
            throw new BeanCreationException(beanName, "FactoryBean threw exception on object creation", var5);
         }
      } else {
         return bean;
      }
   }

   public Object getBean(String name, @Nullable Class requiredType) throws BeansException {
      Object bean = this.getBean(name);
      if (requiredType != null && !requiredType.isInstance(bean)) {
         throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
      } else {
         return bean;
      }
   }

   public Object getBean(String name, Object... args) throws BeansException {
      if (!ObjectUtils.isEmpty(args)) {
         throw new UnsupportedOperationException("StaticListableBeanFactory does not support explicit bean creation arguments");
      } else {
         return this.getBean(name);
      }
   }

   public Object getBean(Class requiredType) throws BeansException {
      String[] beanNames = this.getBeanNamesForType(requiredType);
      if (beanNames.length == 1) {
         return this.getBean(beanNames[0], requiredType);
      } else if (beanNames.length > 1) {
         throw new NoUniqueBeanDefinitionException(requiredType, beanNames);
      } else {
         throw new NoSuchBeanDefinitionException(requiredType);
      }
   }

   public Object getBean(Class requiredType, Object... args) throws BeansException {
      if (!ObjectUtils.isEmpty(args)) {
         throw new UnsupportedOperationException("StaticListableBeanFactory does not support explicit bean creation arguments");
      } else {
         return this.getBean(requiredType);
      }
   }

   public ObjectProvider getBeanProvider(Class requiredType) throws BeansException {
      return this.getBeanProvider(ResolvableType.forRawClass(requiredType));
   }

   public ObjectProvider getBeanProvider(final ResolvableType requiredType) {
      return new ObjectProvider() {
         public Object getObject() throws BeansException {
            String[] beanNames = StaticListableBeanFactory.this.getBeanNamesForType(requiredType);
            if (beanNames.length == 1) {
               return StaticListableBeanFactory.this.getBean(beanNames[0], requiredType);
            } else if (beanNames.length > 1) {
               throw new NoUniqueBeanDefinitionException(requiredType, beanNames);
            } else {
               throw new NoSuchBeanDefinitionException(requiredType);
            }
         }

         public Object getObject(Object... args) throws BeansException {
            String[] beanNames = StaticListableBeanFactory.this.getBeanNamesForType(requiredType);
            if (beanNames.length == 1) {
               return StaticListableBeanFactory.this.getBean(beanNames[0], args);
            } else if (beanNames.length > 1) {
               throw new NoUniqueBeanDefinitionException(requiredType, beanNames);
            } else {
               throw new NoSuchBeanDefinitionException(requiredType);
            }
         }

         @Nullable
         public Object getIfAvailable() throws BeansException {
            String[] beanNames = StaticListableBeanFactory.this.getBeanNamesForType(requiredType);
            if (beanNames.length == 1) {
               return StaticListableBeanFactory.this.getBean(beanNames[0]);
            } else if (beanNames.length > 1) {
               throw new NoUniqueBeanDefinitionException(requiredType, beanNames);
            } else {
               return null;
            }
         }

         @Nullable
         public Object getIfUnique() throws BeansException {
            String[] beanNames = StaticListableBeanFactory.this.getBeanNamesForType(requiredType);
            return beanNames.length == 1 ? StaticListableBeanFactory.this.getBean(beanNames[0]) : null;
         }

         public Stream stream() {
            return Arrays.stream(StaticListableBeanFactory.this.getBeanNamesForType(requiredType)).map((name) -> {
               return StaticListableBeanFactory.this.getBean(name);
            });
         }

         public Stream orderedStream() {
            return this.stream().sorted(OrderComparator.INSTANCE);
         }
      };
   }

   public boolean containsBean(String name) {
      return this.beans.containsKey(name);
   }

   public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
      Object bean = this.getBean(name);
      return bean instanceof FactoryBean && ((FactoryBean)bean).isSingleton();
   }

   public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
      Object bean = this.getBean(name);
      return bean instanceof SmartFactoryBean && ((SmartFactoryBean)bean).isPrototype() || bean instanceof FactoryBean && !((FactoryBean)bean).isSingleton();
   }

   public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
      Class type = this.getType(name);
      return type != null && typeToMatch.isAssignableFrom(type);
   }

   public boolean isTypeMatch(String name, @Nullable Class typeToMatch) throws NoSuchBeanDefinitionException {
      Class type = this.getType(name);
      return typeToMatch == null || type != null && typeToMatch.isAssignableFrom(type);
   }

   public Class getType(String name) throws NoSuchBeanDefinitionException {
      String beanName = BeanFactoryUtils.transformedBeanName(name);
      Object bean = this.beans.get(beanName);
      if (bean == null) {
         throw new NoSuchBeanDefinitionException(beanName, "Defined beans are [" + StringUtils.collectionToCommaDelimitedString(this.beans.keySet()) + "]");
      } else {
         return bean instanceof FactoryBean && !BeanFactoryUtils.isFactoryDereference(name) ? ((FactoryBean)bean).getObjectType() : bean.getClass();
      }
   }

   public String[] getAliases(String name) {
      return new String[0];
   }

   public boolean containsBeanDefinition(String name) {
      return this.beans.containsKey(name);
   }

   public int getBeanDefinitionCount() {
      return this.beans.size();
   }

   public String[] getBeanDefinitionNames() {
      return StringUtils.toStringArray((Collection)this.beans.keySet());
   }

   public String[] getBeanNamesForType(@Nullable ResolvableType type) {
      boolean isFactoryType = false;
      if (type != null) {
         Class resolved = type.resolve();
         if (resolved != null && FactoryBean.class.isAssignableFrom(resolved)) {
            isFactoryType = true;
         }
      }

      List matches = new ArrayList();
      Iterator var4 = this.beans.entrySet().iterator();

      while(true) {
         String name;
         Class objectType;
         do {
            label39:
            do {
               while(var4.hasNext()) {
                  Map.Entry entry = (Map.Entry)var4.next();
                  name = (String)entry.getKey();
                  Object beanInstance = entry.getValue();
                  if (beanInstance instanceof FactoryBean && !isFactoryType) {
                     objectType = ((FactoryBean)beanInstance).getObjectType();
                     continue label39;
                  }

                  if (type == null || type.isInstance(beanInstance)) {
                     matches.add(name);
                  }
               }

               return StringUtils.toStringArray((Collection)matches);
            } while(objectType == null);
         } while(type != null && !type.isAssignableFrom(objectType));

         matches.add(name);
      }
   }

   public String[] getBeanNamesForType(@Nullable Class type) {
      return this.getBeanNamesForType(ResolvableType.forClass(type));
   }

   public String[] getBeanNamesForType(@Nullable Class type, boolean includeNonSingletons, boolean allowEagerInit) {
      return this.getBeanNamesForType(ResolvableType.forClass(type));
   }

   public Map getBeansOfType(@Nullable Class type) throws BeansException {
      return this.getBeansOfType(type, true, true);
   }

   public Map getBeansOfType(@Nullable Class type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
      boolean isFactoryType = type != null && FactoryBean.class.isAssignableFrom(type);
      Map matches = new LinkedHashMap();
      Iterator var6 = this.beans.entrySet().iterator();

      while(true) {
         String beanName;
         Class objectType;
         do {
            FactoryBean factory;
            do {
               label43:
               do {
                  while(var6.hasNext()) {
                     Map.Entry entry = (Map.Entry)var6.next();
                     beanName = (String)entry.getKey();
                     Object beanInstance = entry.getValue();
                     if (beanInstance instanceof FactoryBean && !isFactoryType) {
                        factory = (FactoryBean)beanInstance;
                        objectType = factory.getObjectType();
                        continue label43;
                     }

                     if (type == null || type.isInstance(beanInstance)) {
                        if (isFactoryType) {
                           beanName = "&" + beanName;
                        }

                        matches.put(beanName, beanInstance);
                     }
                  }

                  return matches;
               } while(!includeNonSingletons && !factory.isSingleton());
            } while(objectType == null);
         } while(type != null && !type.isAssignableFrom(objectType));

         matches.put(beanName, this.getBean(beanName, type));
      }
   }

   public String[] getBeanNamesForAnnotation(Class annotationType) {
      List results = new ArrayList();
      Iterator var3 = this.beans.keySet().iterator();

      while(var3.hasNext()) {
         String beanName = (String)var3.next();
         if (this.findAnnotationOnBean(beanName, annotationType) != null) {
            results.add(beanName);
         }
      }

      return StringUtils.toStringArray((Collection)results);
   }

   public Map getBeansWithAnnotation(Class annotationType) throws BeansException {
      Map results = new LinkedHashMap();
      Iterator var3 = this.beans.keySet().iterator();

      while(var3.hasNext()) {
         String beanName = (String)var3.next();
         if (this.findAnnotationOnBean(beanName, annotationType) != null) {
            results.put(beanName, this.getBean(beanName));
         }
      }

      return results;
   }

   @Nullable
   public Annotation findAnnotationOnBean(String beanName, Class annotationType) throws NoSuchBeanDefinitionException {
      Class beanType = this.getType(beanName);
      return beanType != null ? AnnotationUtils.findAnnotation(beanType, annotationType) : null;
   }
}
