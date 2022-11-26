package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;

public class TypedStringValue implements BeanMetadataElement {
   @Nullable
   private String value;
   @Nullable
   private volatile Object targetType;
   @Nullable
   private Object source;
   @Nullable
   private String specifiedTypeName;
   private volatile boolean dynamic;

   public TypedStringValue(@Nullable String value) {
      this.setValue(value);
   }

   public TypedStringValue(@Nullable String value, Class targetType) {
      this.setValue(value);
      this.setTargetType(targetType);
   }

   public TypedStringValue(@Nullable String value, String targetTypeName) {
      this.setValue(value);
      this.setTargetTypeName(targetTypeName);
   }

   public void setValue(@Nullable String value) {
      this.value = value;
   }

   @Nullable
   public String getValue() {
      return this.value;
   }

   public void setTargetType(Class targetType) {
      Assert.notNull(targetType, (String)"'targetType' must not be null");
      this.targetType = targetType;
   }

   public Class getTargetType() {
      Object targetTypeValue = this.targetType;
      if (!(targetTypeValue instanceof Class)) {
         throw new IllegalStateException("Typed String value does not carry a resolved target type");
      } else {
         return (Class)targetTypeValue;
      }
   }

   public void setTargetTypeName(@Nullable String targetTypeName) {
      this.targetType = targetTypeName;
   }

   @Nullable
   public String getTargetTypeName() {
      Object targetTypeValue = this.targetType;
      return targetTypeValue instanceof Class ? ((Class)targetTypeValue).getName() : (String)targetTypeValue;
   }

   public boolean hasTargetType() {
      return this.targetType instanceof Class;
   }

   @Nullable
   public Class resolveTargetType(@Nullable ClassLoader classLoader) throws ClassNotFoundException {
      String typeName = this.getTargetTypeName();
      if (typeName == null) {
         return null;
      } else {
         Class resolvedClass = ClassUtils.forName(typeName, classLoader);
         this.targetType = resolvedClass;
         return resolvedClass;
      }
   }

   public void setSource(@Nullable Object source) {
      this.source = source;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }

   public void setSpecifiedTypeName(@Nullable String specifiedTypeName) {
      this.specifiedTypeName = specifiedTypeName;
   }

   @Nullable
   public String getSpecifiedTypeName() {
      return this.specifiedTypeName;
   }

   public void setDynamic() {
      this.dynamic = true;
   }

   public boolean isDynamic() {
      return this.dynamic;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof TypedStringValue)) {
         return false;
      } else {
         TypedStringValue otherValue = (TypedStringValue)other;
         return ObjectUtils.nullSafeEquals(this.value, otherValue.value) && ObjectUtils.nullSafeEquals(this.targetType, otherValue.targetType);
      }
   }

   public int hashCode() {
      return ObjectUtils.nullSafeHashCode((Object)this.value) * 29 + ObjectUtils.nullSafeHashCode(this.targetType);
   }

   public String toString() {
      return "TypedStringValue: value [" + this.value + "], target type [" + this.targetType + "]";
   }
}
