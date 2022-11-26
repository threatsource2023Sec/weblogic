package com.googlecode.cqengine.query.option;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.OrderControlAttribute;
import com.googlecode.cqengine.attribute.OrderMissingFirstAttribute;
import com.googlecode.cqengine.attribute.OrderMissingLastAttribute;

public class AttributeOrder {
   private final Attribute attribute;
   private final boolean descending;

   public AttributeOrder(Attribute attribute, boolean descending) {
      this.attribute = attribute;
      this.descending = descending;
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public boolean isDescending() {
      return this.descending;
   }

   public String toString() {
      OrderControlAttribute orderControlAttribute;
      Attribute delegateAttribute;
      if (this.attribute instanceof OrderMissingLastAttribute) {
         orderControlAttribute = (OrderControlAttribute)this.attribute;
         delegateAttribute = orderControlAttribute.getDelegateAttribute();
         return this.descending ? "descending(missingLast(" + delegateAttribute.getObjectType().getSimpleName() + "." + delegateAttribute.getAttributeName() + "))" : "ascending(missingLast(" + delegateAttribute.getObjectType().getSimpleName() + "." + delegateAttribute.getAttributeName() + "))";
      } else if (this.attribute instanceof OrderMissingFirstAttribute) {
         orderControlAttribute = (OrderControlAttribute)this.attribute;
         delegateAttribute = orderControlAttribute.getDelegateAttribute();
         return this.descending ? "descending(missingFirst(" + delegateAttribute.getObjectType().getSimpleName() + "." + delegateAttribute.getAttributeName() + "))" : "ascending(missingFirst(" + delegateAttribute.getObjectType().getSimpleName() + "." + delegateAttribute.getAttributeName() + "))";
      } else {
         return this.descending ? "descending(" + this.attribute.getObjectType().getSimpleName() + "." + this.attribute.getAttributeName() + ")" : "ascending(" + this.attribute.getObjectType().getSimpleName() + "." + this.attribute.getAttributeName() + ")";
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof AttributeOrder)) {
         return false;
      } else {
         AttributeOrder that = (AttributeOrder)o;
         if (this.descending != that.descending) {
            return false;
         } else {
            return this.attribute.equals(that.attribute);
         }
      }
   }

   public int hashCode() {
      int result = this.attribute.hashCode();
      result = 31 * result + (this.descending ? 1 : 0);
      return result;
   }
}
