package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class SimpleContentBeanMarshaller implements TypeMarshaller {
   private final SimpleContentRuntimeBindingType simpleContentType;

   public SimpleContentBeanMarshaller(SimpleContentRuntimeBindingType rtt) throws XmlException {
      this.simpleContentType = rtt;
      RuntimeBindingType content_rtt = rtt.getSimpleContentProperty().getRuntimeBindingType();
      TypeMarshaller marshaller = content_rtt.getMarshaller();
      if (marshaller == null) {
         String e = "failed to find marshaller for " + content_rtt.getBindingType();
         throw new AssertionError(e);
      }
   }

   public CharSequence print(Object value, MarshalResult result) throws XmlException {
      RuntimeBindingProperty simple_content_prop = this.simpleContentType.getSimpleContentProperty();
      Object simple_content_val = simple_content_prop.getValue(value);
      RuntimeBindingType actual_prop_rtt = simple_content_prop.getActualRuntimeType(simple_content_val, result);
      return actual_prop_rtt.getMarshaller().print(simple_content_val, result);
   }
}
