package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class SimpleTypeVisitor extends SimpleContentVisitor {
   public SimpleTypeVisitor(RuntimeBindingProperty property, Object obj, PullMarshalResult result) throws XmlException {
      super(property, obj, result);
   }

   protected void initAttributes() throws XmlException {
      if (this.getParentObject() == null) {
         this.marshalResult.addXsiNilAttribute();
         if (this.needsXsiType()) {
            this.marshalResult.addXsiTypeAttribute(this.getActualRuntimeBindingType());
         }
      } else if (this.needsXsiType()) {
         this.marshalResult.addXsiTypeAttribute(this.getActualRuntimeBindingType());
      }

   }
}
