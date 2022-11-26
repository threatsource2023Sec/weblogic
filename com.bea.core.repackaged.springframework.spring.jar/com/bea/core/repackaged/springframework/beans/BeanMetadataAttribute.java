package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;

public class BeanMetadataAttribute implements BeanMetadataElement {
   private final String name;
   @Nullable
   private final Object value;
   @Nullable
   private Object source;

   public BeanMetadataAttribute(String name, @Nullable Object value) {
      Assert.notNull(name, (String)"Name must not be null");
      this.name = name;
      this.value = value;
   }

   public String getName() {
      return this.name;
   }

   @Nullable
   public Object getValue() {
      return this.value;
   }

   public void setSource(@Nullable Object source) {
      this.source = source;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof BeanMetadataAttribute)) {
         return false;
      } else {
         BeanMetadataAttribute otherMa = (BeanMetadataAttribute)other;
         return this.name.equals(otherMa.name) && ObjectUtils.nullSafeEquals(this.value, otherMa.value) && ObjectUtils.nullSafeEquals(this.source, otherMa.source);
      }
   }

   public int hashCode() {
      return this.name.hashCode() * 29 + ObjectUtils.nullSafeHashCode(this.value);
   }

   public String toString() {
      return "metadata attribute '" + this.name + "'";
   }
}
