package com.googlecode.cqengine.attribute;

public abstract class OrderControlAttribute extends SimpleAttribute {
   protected final Attribute delegateAttribute;

   protected OrderControlAttribute(Attribute delegateAttribute, String delegateAttributeName) {
      super(delegateAttribute.getObjectType(), Integer.class, delegateAttributeName);
      if (delegateAttribute instanceof OrderControlAttribute) {
         throw new IllegalArgumentException("Delegate attribute cannot also be an OrderControlAttribute: " + delegateAttribute);
      } else {
         this.delegateAttribute = delegateAttribute;
      }
   }

   public Attribute getDelegateAttribute() {
      return this.delegateAttribute;
   }
}
