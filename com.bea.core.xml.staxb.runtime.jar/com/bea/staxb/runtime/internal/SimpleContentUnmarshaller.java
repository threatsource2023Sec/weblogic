package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class SimpleContentUnmarshaller extends AttributeUnmarshaller {
   private final SimpleContentRuntimeBindingType simpleContentRuntimeBindingType;

   public SimpleContentUnmarshaller(SimpleContentRuntimeBindingType type) {
      super(type);
      this.simpleContentRuntimeBindingType = type;
   }

   protected void deserializeContents(Object inter, UnmarshalResult context) throws XmlException {
      RuntimeBindingProperty scprop = this.simpleContentRuntimeBindingType.getSimpleContentProperty();
      context.extractAndFillElementProp(scprop, inter);
   }
}
