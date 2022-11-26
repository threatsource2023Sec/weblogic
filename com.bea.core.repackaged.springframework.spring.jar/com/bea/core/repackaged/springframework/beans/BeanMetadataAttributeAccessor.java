package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.AttributeAccessorSupport;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class BeanMetadataAttributeAccessor extends AttributeAccessorSupport implements BeanMetadataElement {
   @Nullable
   private Object source;

   public void setSource(@Nullable Object source) {
      this.source = source;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }

   public void addMetadataAttribute(BeanMetadataAttribute attribute) {
      super.setAttribute(attribute.getName(), attribute);
   }

   @Nullable
   public BeanMetadataAttribute getMetadataAttribute(String name) {
      return (BeanMetadataAttribute)super.getAttribute(name);
   }

   public void setAttribute(String name, @Nullable Object value) {
      super.setAttribute(name, new BeanMetadataAttribute(name, value));
   }

   @Nullable
   public Object getAttribute(String name) {
      BeanMetadataAttribute attribute = (BeanMetadataAttribute)super.getAttribute(name);
      return attribute != null ? attribute.getValue() : null;
   }

   @Nullable
   public Object removeAttribute(String name) {
      BeanMetadataAttribute attribute = (BeanMetadataAttribute)super.removeAttribute(name);
      return attribute != null ? attribute.getValue() : null;
   }
}
