package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.xml.XmlException;
import javax.xml.stream.XMLStreamWriter;

final class LiteralPushMarshalResult extends PushMarshalResult {
   LiteralPushMarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, XMLStreamWriter writer, String default_namespace_uri, MarshalOptions options) throws XmlException {
      super(bindingLoader, typeTable, writer, default_namespace_uri, options);
   }

   protected void writeSoapArrayAttributes(SoapArrayRuntimeBindingType rtt, int[] array_dims) throws XmlException {
      PushSoap11MarshalResult.writeSoap11ArrayAttribute(this, rtt, array_dims);
   }
}
