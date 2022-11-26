package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.PropertyValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanDefinitionVisitor {
   @Nullable
   private StringValueResolver valueResolver;

   public BeanDefinitionVisitor(StringValueResolver valueResolver) {
      Assert.notNull(valueResolver, (String)"StringValueResolver must not be null");
      this.valueResolver = valueResolver;
   }

   protected BeanDefinitionVisitor() {
   }

   public void visitBeanDefinition(BeanDefinition beanDefinition) {
      this.visitParentName(beanDefinition);
      this.visitBeanClassName(beanDefinition);
      this.visitFactoryBeanName(beanDefinition);
      this.visitFactoryMethodName(beanDefinition);
      this.visitScope(beanDefinition);
      if (beanDefinition.hasPropertyValues()) {
         this.visitPropertyValues(beanDefinition.getPropertyValues());
      }

      if (beanDefinition.hasConstructorArgumentValues()) {
         ConstructorArgumentValues cas = beanDefinition.getConstructorArgumentValues();
         this.visitIndexedArgumentValues(cas.getIndexedArgumentValues());
         this.visitGenericArgumentValues(cas.getGenericArgumentValues());
      }

   }

   protected void visitParentName(BeanDefinition beanDefinition) {
      String parentName = beanDefinition.getParentName();
      if (parentName != null) {
         String resolvedName = this.resolveStringValue(parentName);
         if (!parentName.equals(resolvedName)) {
            beanDefinition.setParentName(resolvedName);
         }
      }

   }

   protected void visitBeanClassName(BeanDefinition beanDefinition) {
      String beanClassName = beanDefinition.getBeanClassName();
      if (beanClassName != null) {
         String resolvedName = this.resolveStringValue(beanClassName);
         if (!beanClassName.equals(resolvedName)) {
            beanDefinition.setBeanClassName(resolvedName);
         }
      }

   }

   protected void visitFactoryBeanName(BeanDefinition beanDefinition) {
      String factoryBeanName = beanDefinition.getFactoryBeanName();
      if (factoryBeanName != null) {
         String resolvedName = this.resolveStringValue(factoryBeanName);
         if (!factoryBeanName.equals(resolvedName)) {
            beanDefinition.setFactoryBeanName(resolvedName);
         }
      }

   }

   protected void visitFactoryMethodName(BeanDefinition beanDefinition) {
      String factoryMethodName = beanDefinition.getFactoryMethodName();
      if (factoryMethodName != null) {
         String resolvedName = this.resolveStringValue(factoryMethodName);
         if (!factoryMethodName.equals(resolvedName)) {
            beanDefinition.setFactoryMethodName(resolvedName);
         }
      }

   }

   protected void visitScope(BeanDefinition beanDefinition) {
      String scope = beanDefinition.getScope();
      if (scope != null) {
         String resolvedScope = this.resolveStringValue(scope);
         if (!scope.equals(resolvedScope)) {
            beanDefinition.setScope(resolvedScope);
         }
      }

   }

   protected void visitPropertyValues(MutablePropertyValues pvs) {
      PropertyValue[] pvArray = pvs.getPropertyValues();
      PropertyValue[] var3 = pvArray;
      int var4 = pvArray.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyValue pv = var3[var5];
         Object newVal = this.resolveValue(pv.getValue());
         if (!ObjectUtils.nullSafeEquals(newVal, pv.getValue())) {
            pvs.add(pv.getName(), newVal);
         }
      }

   }

   protected void visitIndexedArgumentValues(Map ias) {
      Iterator var2 = ias.values().iterator();

      while(var2.hasNext()) {
         ConstructorArgumentValues.ValueHolder valueHolder = (ConstructorArgumentValues.ValueHolder)var2.next();
         Object newVal = this.resolveValue(valueHolder.getValue());
         if (!ObjectUtils.nullSafeEquals(newVal, valueHolder.getValue())) {
            valueHolder.setValue(newVal);
         }
      }

   }

   protected void visitGenericArgumentValues(List gas) {
      Iterator var2 = gas.iterator();

      while(var2.hasNext()) {
         ConstructorArgumentValues.ValueHolder valueHolder = (ConstructorArgumentValues.ValueHolder)var2.next();
         Object newVal = this.resolveValue(valueHolder.getValue());
         if (!ObjectUtils.nullSafeEquals(newVal, valueHolder.getValue())) {
            valueHolder.setValue(newVal);
         }
      }

   }

   @Nullable
   protected Object resolveValue(@Nullable Object value) {
      if (value instanceof BeanDefinition) {
         this.visitBeanDefinition((BeanDefinition)value);
      } else if (value instanceof BeanDefinitionHolder) {
         this.visitBeanDefinition(((BeanDefinitionHolder)value).getBeanDefinition());
      } else {
         String stringValue;
         if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference)value;
            stringValue = this.resolveStringValue(ref.getBeanName());
            if (stringValue == null) {
               return null;
            }

            if (!stringValue.equals(ref.getBeanName())) {
               return new RuntimeBeanReference(stringValue);
            }
         } else if (value instanceof RuntimeBeanNameReference) {
            RuntimeBeanNameReference ref = (RuntimeBeanNameReference)value;
            stringValue = this.resolveStringValue(ref.getBeanName());
            if (stringValue == null) {
               return null;
            }

            if (!stringValue.equals(ref.getBeanName())) {
               return new RuntimeBeanNameReference(stringValue);
            }
         } else if (value instanceof Object[]) {
            this.visitArray((Object[])((Object[])value));
         } else if (value instanceof List) {
            this.visitList((List)value);
         } else if (value instanceof Set) {
            this.visitSet((Set)value);
         } else if (value instanceof Map) {
            this.visitMap((Map)value);
         } else if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue)value;
            stringValue = typedStringValue.getValue();
            if (stringValue != null) {
               String visitedString = this.resolveStringValue(stringValue);
               typedStringValue.setValue(visitedString);
            }
         } else if (value instanceof String) {
            return this.resolveStringValue((String)value);
         }
      }

      return value;
   }

   protected void visitArray(Object[] arrayVal) {
      for(int i = 0; i < arrayVal.length; ++i) {
         Object elem = arrayVal[i];
         Object newVal = this.resolveValue(elem);
         if (!ObjectUtils.nullSafeEquals(newVal, elem)) {
            arrayVal[i] = newVal;
         }
      }

   }

   protected void visitList(List listVal) {
      for(int i = 0; i < listVal.size(); ++i) {
         Object elem = listVal.get(i);
         Object newVal = this.resolveValue(elem);
         if (!ObjectUtils.nullSafeEquals(newVal, elem)) {
            listVal.set(i, newVal);
         }
      }

   }

   protected void visitSet(Set setVal) {
      Set newContent = new LinkedHashSet();
      boolean entriesModified = false;

      Object elem;
      int elemHash;
      Object newVal;
      int newValHash;
      for(Iterator var4 = setVal.iterator(); var4.hasNext(); entriesModified = entriesModified || newVal != elem || newValHash != elemHash) {
         elem = var4.next();
         elemHash = elem != null ? elem.hashCode() : 0;
         newVal = this.resolveValue(elem);
         newValHash = newVal != null ? newVal.hashCode() : 0;
         newContent.add(newVal);
      }

      if (entriesModified) {
         setVal.clear();
         setVal.addAll(newContent);
      }

   }

   protected void visitMap(Map mapVal) {
      Map newContent = new LinkedHashMap();
      boolean entriesModified = false;

      Object key;
      int keyHash;
      Object newKey;
      int newKeyHash;
      Object val;
      Object newVal;
      for(Iterator var4 = mapVal.entrySet().iterator(); var4.hasNext(); entriesModified = entriesModified || newVal != val || newKey != key || newKeyHash != keyHash) {
         Map.Entry entry = (Map.Entry)var4.next();
         key = entry.getKey();
         keyHash = key != null ? key.hashCode() : 0;
         newKey = this.resolveValue(key);
         newKeyHash = newKey != null ? newKey.hashCode() : 0;
         val = entry.getValue();
         newVal = this.resolveValue(val);
         newContent.put(newKey, newVal);
      }

      if (entriesModified) {
         mapVal.clear();
         mapVal.putAll(newContent);
      }

   }

   @Nullable
   protected String resolveStringValue(String strVal) {
      if (this.valueResolver == null) {
         throw new IllegalStateException("No StringValueResolver specified - pass a resolver object into the constructor or override the 'resolveStringValue' method");
      } else {
         String resolvedValue = this.valueResolver.resolveStringValue(strVal);
         return strVal.equals(resolvedValue) ? strVal : resolvedValue;
      }
   }
}
