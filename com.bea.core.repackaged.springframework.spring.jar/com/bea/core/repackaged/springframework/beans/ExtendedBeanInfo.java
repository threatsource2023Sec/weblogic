package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class ExtendedBeanInfo implements BeanInfo {
   private static final Log logger = LogFactory.getLog(ExtendedBeanInfo.class);
   private final BeanInfo delegate;
   private final Set propertyDescriptors = new TreeSet(new PropertyDescriptorComparator());

   public ExtendedBeanInfo(BeanInfo delegate) {
      this.delegate = delegate;
      PropertyDescriptor[] var2 = delegate.getPropertyDescriptors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PropertyDescriptor pd = var2[var4];

         try {
            this.propertyDescriptors.add(pd instanceof IndexedPropertyDescriptor ? new SimpleIndexedPropertyDescriptor((IndexedPropertyDescriptor)pd) : new SimplePropertyDescriptor(pd));
         } catch (IntrospectionException var8) {
            if (logger.isDebugEnabled()) {
               logger.debug("Ignoring invalid bean property '" + pd.getName() + "': " + var8.getMessage());
            }
         }
      }

      MethodDescriptor[] methodDescriptors = delegate.getMethodDescriptors();
      if (methodDescriptors != null) {
         Iterator var10 = this.findCandidateWriteMethods(methodDescriptors).iterator();

         while(var10.hasNext()) {
            Method method = (Method)var10.next();

            try {
               this.handleCandidateWriteMethod(method);
            } catch (IntrospectionException var7) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Ignoring candidate write method [" + method + "]: " + var7.getMessage());
               }
            }
         }
      }

   }

   private List findCandidateWriteMethods(MethodDescriptor[] methodDescriptors) {
      List matches = new ArrayList();
      MethodDescriptor[] var3 = methodDescriptors;
      int var4 = methodDescriptors.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MethodDescriptor methodDescriptor = var3[var5];
         Method method = methodDescriptor.getMethod();
         if (isCandidateWriteMethod(method)) {
            matches.add(method);
         }
      }

      matches.sort((m1, m2) -> {
         return m2.toString().compareTo(m1.toString());
      });
      return matches;
   }

   public static boolean isCandidateWriteMethod(Method method) {
      String methodName = method.getName();
      int nParams = method.getParameterCount();
      return methodName.length() > 3 && methodName.startsWith("set") && Modifier.isPublic(method.getModifiers()) && (!Void.TYPE.isAssignableFrom(method.getReturnType()) || Modifier.isStatic(method.getModifiers())) && (nParams == 1 || nParams == 2 && Integer.TYPE == method.getParameterTypes()[0]);
   }

   private void handleCandidateWriteMethod(Method method) throws IntrospectionException {
      int nParams = method.getParameterCount();
      String propertyName = this.propertyNameFor(method);
      Class propertyType = method.getParameterTypes()[nParams - 1];
      PropertyDescriptor existingPd = this.findExistingPropertyDescriptor(propertyName, propertyType);
      if (nParams == 1) {
         if (existingPd == null) {
            this.propertyDescriptors.add(new SimplePropertyDescriptor(propertyName, (Method)null, method));
         } else {
            existingPd.setWriteMethod(method);
         }
      } else {
         if (nParams != 2) {
            throw new IllegalArgumentException("Write method must have exactly 1 or 2 parameters: " + method);
         }

         if (existingPd == null) {
            this.propertyDescriptors.add(new SimpleIndexedPropertyDescriptor(propertyName, (Method)null, (Method)null, (Method)null, method));
         } else if (existingPd instanceof IndexedPropertyDescriptor) {
            ((IndexedPropertyDescriptor)existingPd).setIndexedWriteMethod(method);
         } else {
            this.propertyDescriptors.remove(existingPd);
            this.propertyDescriptors.add(new SimpleIndexedPropertyDescriptor(propertyName, existingPd.getReadMethod(), existingPd.getWriteMethod(), (Method)null, method));
         }
      }

   }

   @Nullable
   private PropertyDescriptor findExistingPropertyDescriptor(String propertyName, Class propertyType) {
      Iterator var3 = this.propertyDescriptors.iterator();

      while(var3.hasNext()) {
         PropertyDescriptor pd = (PropertyDescriptor)var3.next();
         String candidateName = pd.getName();
         Class candidateType;
         if (pd instanceof IndexedPropertyDescriptor) {
            IndexedPropertyDescriptor ipd = (IndexedPropertyDescriptor)pd;
            candidateType = ipd.getIndexedPropertyType();
            if (candidateName.equals(propertyName) && (candidateType.equals(propertyType) || candidateType.equals(propertyType.getComponentType()))) {
               return pd;
            }
         } else {
            candidateType = pd.getPropertyType();
            if (candidateName.equals(propertyName) && (candidateType.equals(propertyType) || propertyType.equals(candidateType.getComponentType()))) {
               return pd;
            }
         }
      }

      return null;
   }

   private String propertyNameFor(Method method) {
      return Introspector.decapitalize(method.getName().substring(3));
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      return (PropertyDescriptor[])this.propertyDescriptors.toArray(new PropertyDescriptor[0]);
   }

   public BeanInfo[] getAdditionalBeanInfo() {
      return this.delegate.getAdditionalBeanInfo();
   }

   public BeanDescriptor getBeanDescriptor() {
      return this.delegate.getBeanDescriptor();
   }

   public int getDefaultEventIndex() {
      return this.delegate.getDefaultEventIndex();
   }

   public int getDefaultPropertyIndex() {
      return this.delegate.getDefaultPropertyIndex();
   }

   public EventSetDescriptor[] getEventSetDescriptors() {
      return this.delegate.getEventSetDescriptors();
   }

   public Image getIcon(int iconKind) {
      return this.delegate.getIcon(iconKind);
   }

   public MethodDescriptor[] getMethodDescriptors() {
      return this.delegate.getMethodDescriptors();
   }

   static class PropertyDescriptorComparator implements Comparator {
      public int compare(PropertyDescriptor desc1, PropertyDescriptor desc2) {
         String left = desc1.getName();
         String right = desc2.getName();
         byte[] leftBytes = left.getBytes();
         byte[] rightBytes = right.getBytes();

         for(int i = 0; i < left.length(); ++i) {
            if (right.length() == i) {
               return 1;
            }

            int result = leftBytes[i] - rightBytes[i];
            if (result != 0) {
               return result;
            }
         }

         return left.length() - right.length();
      }
   }

   static class SimpleIndexedPropertyDescriptor extends IndexedPropertyDescriptor {
      @Nullable
      private Method readMethod;
      @Nullable
      private Method writeMethod;
      @Nullable
      private Class propertyType;
      @Nullable
      private Method indexedReadMethod;
      @Nullable
      private Method indexedWriteMethod;
      @Nullable
      private Class indexedPropertyType;
      @Nullable
      private Class propertyEditorClass;

      public SimpleIndexedPropertyDescriptor(IndexedPropertyDescriptor original) throws IntrospectionException {
         this(original.getName(), original.getReadMethod(), original.getWriteMethod(), original.getIndexedReadMethod(), original.getIndexedWriteMethod());
         PropertyDescriptorUtils.copyNonMethodProperties(original, this);
      }

      public SimpleIndexedPropertyDescriptor(String propertyName, @Nullable Method readMethod, @Nullable Method writeMethod, @Nullable Method indexedReadMethod, Method indexedWriteMethod) throws IntrospectionException {
         super(propertyName, (Method)null, (Method)null, (Method)null, (Method)null);
         this.readMethod = readMethod;
         this.writeMethod = writeMethod;
         this.propertyType = PropertyDescriptorUtils.findPropertyType(readMethod, writeMethod);
         this.indexedReadMethod = indexedReadMethod;
         this.indexedWriteMethod = indexedWriteMethod;
         this.indexedPropertyType = PropertyDescriptorUtils.findIndexedPropertyType(propertyName, this.propertyType, indexedReadMethod, indexedWriteMethod);
      }

      @Nullable
      public Method getReadMethod() {
         return this.readMethod;
      }

      public void setReadMethod(@Nullable Method readMethod) {
         this.readMethod = readMethod;
      }

      @Nullable
      public Method getWriteMethod() {
         return this.writeMethod;
      }

      public void setWriteMethod(@Nullable Method writeMethod) {
         this.writeMethod = writeMethod;
      }

      @Nullable
      public Class getPropertyType() {
         if (this.propertyType == null) {
            try {
               this.propertyType = PropertyDescriptorUtils.findPropertyType(this.readMethod, this.writeMethod);
            } catch (IntrospectionException var2) {
            }
         }

         return this.propertyType;
      }

      @Nullable
      public Method getIndexedReadMethod() {
         return this.indexedReadMethod;
      }

      public void setIndexedReadMethod(@Nullable Method indexedReadMethod) throws IntrospectionException {
         this.indexedReadMethod = indexedReadMethod;
      }

      @Nullable
      public Method getIndexedWriteMethod() {
         return this.indexedWriteMethod;
      }

      public void setIndexedWriteMethod(@Nullable Method indexedWriteMethod) throws IntrospectionException {
         this.indexedWriteMethod = indexedWriteMethod;
      }

      @Nullable
      public Class getIndexedPropertyType() {
         if (this.indexedPropertyType == null) {
            try {
               this.indexedPropertyType = PropertyDescriptorUtils.findIndexedPropertyType(this.getName(), this.getPropertyType(), this.indexedReadMethod, this.indexedWriteMethod);
            } catch (IntrospectionException var2) {
            }
         }

         return this.indexedPropertyType;
      }

      @Nullable
      public Class getPropertyEditorClass() {
         return this.propertyEditorClass;
      }

      public void setPropertyEditorClass(@Nullable Class propertyEditorClass) {
         this.propertyEditorClass = propertyEditorClass;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof IndexedPropertyDescriptor)) {
            return false;
         } else {
            IndexedPropertyDescriptor otherPd = (IndexedPropertyDescriptor)other;
            return ObjectUtils.nullSafeEquals(this.getIndexedReadMethod(), otherPd.getIndexedReadMethod()) && ObjectUtils.nullSafeEquals(this.getIndexedWriteMethod(), otherPd.getIndexedWriteMethod()) && ObjectUtils.nullSafeEquals(this.getIndexedPropertyType(), otherPd.getIndexedPropertyType()) && PropertyDescriptorUtils.equals(this, otherPd);
         }
      }

      public int hashCode() {
         int hashCode = ObjectUtils.nullSafeHashCode((Object)this.getReadMethod());
         hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.getWriteMethod());
         hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.getIndexedReadMethod());
         hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.getIndexedWriteMethod());
         return hashCode;
      }

      public String toString() {
         return String.format("%s[name=%s, propertyType=%s, indexedPropertyType=%s, readMethod=%s, writeMethod=%s, indexedReadMethod=%s, indexedWriteMethod=%s]", this.getClass().getSimpleName(), this.getName(), this.getPropertyType(), this.getIndexedPropertyType(), this.readMethod, this.writeMethod, this.indexedReadMethod, this.indexedWriteMethod);
      }
   }

   static class SimplePropertyDescriptor extends PropertyDescriptor {
      @Nullable
      private Method readMethod;
      @Nullable
      private Method writeMethod;
      @Nullable
      private Class propertyType;
      @Nullable
      private Class propertyEditorClass;

      public SimplePropertyDescriptor(PropertyDescriptor original) throws IntrospectionException {
         this(original.getName(), original.getReadMethod(), original.getWriteMethod());
         PropertyDescriptorUtils.copyNonMethodProperties(original, this);
      }

      public SimplePropertyDescriptor(String propertyName, @Nullable Method readMethod, Method writeMethod) throws IntrospectionException {
         super(propertyName, (Method)null, (Method)null);
         this.readMethod = readMethod;
         this.writeMethod = writeMethod;
         this.propertyType = PropertyDescriptorUtils.findPropertyType(readMethod, writeMethod);
      }

      @Nullable
      public Method getReadMethod() {
         return this.readMethod;
      }

      public void setReadMethod(@Nullable Method readMethod) {
         this.readMethod = readMethod;
      }

      @Nullable
      public Method getWriteMethod() {
         return this.writeMethod;
      }

      public void setWriteMethod(@Nullable Method writeMethod) {
         this.writeMethod = writeMethod;
      }

      @Nullable
      public Class getPropertyType() {
         if (this.propertyType == null) {
            try {
               this.propertyType = PropertyDescriptorUtils.findPropertyType(this.readMethod, this.writeMethod);
            } catch (IntrospectionException var2) {
            }
         }

         return this.propertyType;
      }

      @Nullable
      public Class getPropertyEditorClass() {
         return this.propertyEditorClass;
      }

      public void setPropertyEditorClass(@Nullable Class propertyEditorClass) {
         this.propertyEditorClass = propertyEditorClass;
      }

      public boolean equals(Object other) {
         return this == other || other instanceof PropertyDescriptor && PropertyDescriptorUtils.equals(this, (PropertyDescriptor)other);
      }

      public int hashCode() {
         return ObjectUtils.nullSafeHashCode((Object)this.getReadMethod()) * 29 + ObjectUtils.nullSafeHashCode((Object)this.getWriteMethod());
      }

      public String toString() {
         return String.format("%s[name=%s, propertyType=%s, readMethod=%s, writeMethod=%s]", this.getClass().getSimpleName(), this.getName(), this.getPropertyType(), this.readMethod, this.writeMethod);
      }
   }
}
