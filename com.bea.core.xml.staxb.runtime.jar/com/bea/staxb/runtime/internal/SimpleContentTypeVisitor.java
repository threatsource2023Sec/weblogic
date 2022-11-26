package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class SimpleContentTypeVisitor extends SimpleContentVisitor {
   private final SimpleContentRuntimeBindingType type = (SimpleContentRuntimeBindingType)this.getActualRuntimeBindingType();

   public SimpleContentTypeVisitor(RuntimeBindingProperty property, Object obj, PullMarshalResult result) throws XmlException {
      super(property, obj, result);
   }

   protected void initAttributes() throws XmlException {
      ByNameTypeVisitor.initAttributesInternal(this, this.type, this.getMaxAttributePropCount(), this.marshalResult);
   }

   private int getMaxAttributePropCount() {
      return this.getParentObject() == null ? 0 : this.type.getAttributePropertyCount();
   }
}
