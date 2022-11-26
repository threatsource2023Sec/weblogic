package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class JaxrpcEnumMarsahller implements TypeMarshaller {
   private final JaxrpcEnumRuntimeBindingType enumType;

   public JaxrpcEnumMarsahller(JaxrpcEnumRuntimeBindingType rtt) {
      this.enumType = rtt;
   }

   public CharSequence print(Object value, MarshalResult result) throws XmlException {
      return this.enumType.print(value, result);
   }
}
