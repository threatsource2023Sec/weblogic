package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.xml.XmlException;
import com.bea.xml.soap.SOAPArrayType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamWriter;

public class PushSoap12MarshalResult extends PushSoap11MarshalResult {
   PushSoap12MarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, XMLStreamWriter writer, String defaultNamespaceUri, MarshalOptions options, ObjectRefTable objectRefTable) throws XmlException {
      super(bindingLoader, typeTable, writer, defaultNamespaceUri, options, objectRefTable);
   }

   protected void writeSoapArrayAttributes(SoapArrayRuntimeBindingType rtt, int[] array_dims) throws XmlException {
      writeSoap12ArrayAttribute(this, rtt, array_dims);
   }

   static void writeSoap12ArrayAttribute(MarshalResult result, SoapArrayRuntimeBindingType rtt, int[] array_dims) throws XmlException {
      SOAPArrayType desc = rtt.getSoapArrayDescriptor();
      QName type_name = desc.getQName();
      if (type_name == null) {
         throw new AssertionError("null soap array type name for " + rtt);
      } else {
         CharSequence type_str = QNameTypeConverter.INSTANCE.print(type_name, result);
         result.fillAndAddAttribute(Soap11Constants.SOAP12_ARRAY_TYPE_NAME, type_str.toString());
         result.fillAndAddAttribute(Soap11Constants.SOAP12_ARRAYSIZE, String.valueOf(desc.soap12DimensionString(array_dims)));
      }
   }
}
