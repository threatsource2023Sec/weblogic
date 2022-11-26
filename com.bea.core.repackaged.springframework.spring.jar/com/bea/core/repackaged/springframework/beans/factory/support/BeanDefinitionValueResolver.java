package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanNameReference;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.config.TypedStringValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

class BeanDefinitionValueResolver {
   private final AbstractBeanFactory beanFactory;
   private final String beanName;
   private final BeanDefinition beanDefinition;
   private final TypeConverter typeConverter;

   public BeanDefinitionValueResolver(AbstractBeanFactory beanFactory, String beanName, BeanDefinition beanDefinition, TypeConverter typeConverter) {
      this.beanFactory = beanFactory;
      this.beanName = beanName;
      this.beanDefinition = beanDefinition;
      this.typeConverter = typeConverter;
   }

   @Nullable
   public Object resolveValueIfNecessary(Object argName, @Nullable Object value) {
      if (value instanceof RuntimeBeanReference) {
         RuntimeBeanReference ref = (RuntimeBeanReference)value;
         return this.resolveReference(argName, ref);
      } else if (value instanceof RuntimeBeanNameReference) {
         String refName = ((RuntimeBeanNameReference)value).getBeanName();
         refName = String.valueOf(this.doEvaluate(refName));
         if (!this.beanFactory.containsBean(refName)) {
            throw new BeanDefinitionStoreException("Invalid bean name '" + refName + "' in bean reference for " + argName);
         } else {
            return refName;
         }
      } else if (value instanceof BeanDefinitionHolder) {
         BeanDefinitionHolder bdHolder = (BeanDefinitionHolder)value;
         return this.resolveInnerBean(argName, bdHolder.getBeanName(), bdHolder.getBeanDefinition());
      } else if (value instanceof BeanDefinition) {
         BeanDefinition bd = (BeanDefinition)value;
         String innerBeanName = "(inner bean)#" + ObjectUtils.getIdentityHexString(bd);
         return this.resolveInnerBean(argName, innerBeanName, bd);
      } else if (value instanceof ManagedArray) {
         ManagedArray array = (ManagedArray)value;
         Class elementType = array.resolvedElementType;
         if (elementType == null) {
            String elementTypeName = array.getElementTypeName();
            if (StringUtils.hasText(elementTypeName)) {
               try {
                  elementType = ClassUtils.forName(elementTypeName, this.beanFactory.getBeanClassLoader());
                  array.resolvedElementType = elementType;
               } catch (Throwable var7) {
                  throw new BeanCreationException(this.beanDefinition.getResourceDescription(), this.beanName, "Error resolving array type for " + argName, var7);
               }
            } else {
               elementType = Object.class;
            }
         }

         return this.resolveManagedArray(argName, (List)value, elementType);
      } else if (value instanceof ManagedList) {
         return this.resolveManagedList(argName, (List)value);
      } else if (value instanceof ManagedSet) {
         return this.resolveManagedSet(argName, (Set)value);
      } else if (value instanceof ManagedMap) {
         return this.resolveManagedMap(argName, (Map)value);
      } else if (value instanceof ManagedProperties) {
         Properties original = (Properties)value;
         Properties copy = new Properties();
         original.forEach((propKey, propValue) -> {
            if (propKey instanceof TypedStringValue) {
               propKey = this.evaluate((TypedStringValue)propKey);
            }

            if (propValue instanceof TypedStringValue) {
               propValue = this.evaluate((TypedStringValue)propValue);
            }

            if (propKey != null && propValue != null) {
               copy.put(propKey, propValue);
            } else {
               throw new BeanCreationException(this.beanDefinition.getResourceDescription(), this.beanName, "Error converting Properties key/value pair for " + argName + ": resolved to null");
            }
         });
         return copy;
      } else if (value instanceof TypedStringValue) {
         TypedStringValue typedStringValue = (TypedStringValue)value;
         Object valueObject = this.evaluate(typedStringValue);

         try {
            Class resolvedTargetType = this.resolveTargetType(typedStringValue);
            return resolvedTargetType != null ? this.typeConverter.convertIfNecessary(valueObject, resolvedTargetType) : valueObject;
         } catch (Throwable var8) {
            throw new BeanCreationException(this.beanDefinition.getResourceDescription(), this.beanName, "Error converting typed String value for " + argName, var8);
         }
      } else {
         return value instanceof NullBean ? null : this.evaluate(value);
      }
   }

   @Nullable
   protected Object evaluate(TypedStringValue value) {
      Object result = this.doEvaluate(value.getValue());
      if (!ObjectUtils.nullSafeEquals(result, value.getValue())) {
         value.setDynamic();
      }

      return result;
   }

   @Nullable
   protected Object evaluate(@Nullable Object value) {
      if (value instanceof String) {
         return this.doEvaluate((String)value);
      } else if (value instanceof String[]) {
         String[] values = (String[])((String[])value);
         boolean actuallyResolved = false;
         Object[] resolvedValues = new Object[values.length];

         for(int i = 0; i < values.length; ++i) {
            String originalValue = values[i];
            Object resolvedValue = this.doEvaluate(originalValue);
            if (resolvedValue != originalValue) {
               actuallyResolved = true;
            }

            resolvedValues[i] = resolvedValue;
         }

         return actuallyResolved ? resolvedValues : values;
      } else {
         return value;
      }
   }

   @Nullable
   private Object doEvaluate(@Nullable String value) {
      return this.beanFactory.evaluateBeanDefinitionString(value, this.beanDefinition);
   }

   @Nullable
   protected Class resolveTargetType(TypedStringValue value) throws ClassNotFoundException {
      return value.hasTargetType() ? value.getTargetType() : value.resolveTargetType(this.beanFactory.getBeanClassLoader());
   }

   @Nullable
   private Object resolveReference(Object argName, RuntimeBeanReference ref) {
      try {
         String refName = ref.getBeanName();
         refName = String.valueOf(this.doEvaluate(refName));
         Object bean;
         if (ref.isToParent()) {
            if (this.beanFactory.getParentBeanFactory() == null) {
               throw new BeanCreationException(this.beanDefinition.getResourceDescription(), this.beanName, "Can't resolve reference to bean '" + refName + "' in parent factory: no parent factory available");
            }

            bean = this.beanFactory.getParentBeanFactory().getBean(refName);
         } else {
            bean = this.beanFactory.getBean(refName);
            this.beanFactory.registerDependentBean(refName, this.beanName);
         }

         if (bean instanceof NullBean) {
            bean = null;
         }

         return bean;
      } catch (BeansException var5) {
         throw new BeanCreationException(this.beanDefinition.getResourceDescription(), this.beanName, "Cannot resolve reference to bean '" + ref.getBeanName() + "' while setting " + argName, var5);
      }
   }

   @Nullable
   private Object resolveInnerBean(Object argName, String innerBeanName, BeanDefinition innerBd) {
      RootBeanDefinition mbd = null;

      try {
         mbd = this.beanFactory.getMergedBeanDefinition(innerBeanName, innerBd, this.beanDefinition);
         String actualInnerBeanName = innerBeanName;
         if (mbd.isSingleton()) {
            actualInnerBeanName = this.adaptInnerBeanName(innerBeanName);
         }

         this.beanFactory.registerContainedBean(actualInnerBeanName, this.beanName);
         String[] dependsOn = mbd.getDependsOn();
         if (dependsOn != null) {
            String[] var7 = dependsOn;
            int var8 = dependsOn.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String dependsOnBean = var7[var9];
               this.beanFactory.registerDependentBean(dependsOnBean, actualInnerBeanName);
               this.beanFactory.getBean(dependsOnBean);
            }
         }

         Object innerBean = this.beanFactory.createBean(actualInnerBeanName, mbd, (Object[])null);
         if (innerBean instanceof FactoryBean) {
            boolean synthetic = mbd.isSynthetic();
            innerBean = this.beanFactory.getObjectFromFactoryBean((FactoryBean)innerBean, actualInnerBeanName, !synthetic);
         }

         if (innerBean instanceof NullBean) {
            innerBean = null;
         }

         return innerBean;
      } catch (BeansException var11) {
         throw new BeanCreationException(this.beanDefinition.getResourceDescription(), this.beanName, "Cannot create inner bean '" + innerBeanName + "' " + (mbd != null && mbd.getBeanClassName() != null ? "of type [" + mbd.getBeanClassName() + "] " : "") + "while setting " + argName, var11);
      }
   }

   private String adaptInnerBeanName(String innerBeanName) {
      String actualInnerBeanName = innerBeanName;

      for(int counter = 0; this.beanFactory.isBeanNameInUse(actualInnerBeanName); actualInnerBeanName = innerBeanName + "#" + counter) {
         ++counter;
      }

      return actualInnerBeanName;
   }

   private Object resolveManagedArray(Object argName, List ml, Class elementType) {
      Object resolved = Array.newInstance(elementType, ml.size());

      for(int i = 0; i < ml.size(); ++i) {
         Array.set(resolved, i, this.resolveValueIfNecessary(new KeyedArgName(argName, i), ml.get(i)));
      }

      return resolved;
   }

   private List resolveManagedList(Object argName, List ml) {
      List resolved = new ArrayList(ml.size());

      for(int i = 0; i < ml.size(); ++i) {
         resolved.add(this.resolveValueIfNecessary(new KeyedArgName(argName, i), ml.get(i)));
      }

      return resolved;
   }

   private Set resolveManagedSet(Object argName, Set ms) {
      Set resolved = new LinkedHashSet(ms.size());
      int i = 0;

      for(Iterator var5 = ms.iterator(); var5.hasNext(); ++i) {
         Object m = var5.next();
         resolved.add(this.resolveValueIfNecessary(new KeyedArgName(argName, i), m));
      }

      return resolved;
   }

   private Map resolveManagedMap(Object argName, Map mm) {
      Map resolved = new LinkedHashMap(mm.size());
      mm.forEach((key, value) -> {
         Object resolvedKey = this.resolveValueIfNecessary(argName, key);
         Object resolvedValue = this.resolveValueIfNecessary(new KeyedArgName(argName, key), value);
         resolved.put(resolvedKey, resolvedValue);
      });
      return resolved;
   }

   private static class KeyedArgName {
      private final Object argName;
      private final Object key;

      public KeyedArgName(Object argName, Object key) {
         this.argName = argName;
         this.key = key;
      }

      public String toString() {
         return this.argName + " with key " + "[" + this.key + "]";
      }
   }
}
