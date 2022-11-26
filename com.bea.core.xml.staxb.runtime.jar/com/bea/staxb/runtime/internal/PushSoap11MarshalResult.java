package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.xml.XmlException;
import com.bea.xml.soap.SOAPArrayType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamWriter;

class PushSoap11MarshalResult extends PushSoapMarshalResult {
   PushSoap11MarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, XMLStreamWriter writer, String default_namespace_uri, MarshalOptions options, ObjectRefTable objectRefTable) throws XmlException {
      super(bindingLoader, typeTable, writer, default_namespace_uri, options, objectRefTable);
   }

   protected QName getRefQName() {
      return Soap11Constants.REF_NAME;
   }

   protected String getRefValue(int id) {
      return Soap11Constants.constructRefValueFromId(id);
   }

   protected QName getIdQName() {
      return Soap11Constants.ID_NAME;
   }

   protected String getIdValue(int id) {
      return Soap11Constants.constructIdValueFromId(id);
   }

   protected void writeSoapArrayAttributes(SoapArrayRuntimeBindingType rtt, int[] array_dims) throws XmlException {
      writeSoap11ArrayAttribute(this, rtt, array_dims);
   }

   static void writeSoap11ArrayAttribute(MarshalResult result, SoapArrayRuntimeBindingType rtt, int[] array_dims) throws XmlException {
      SOAPArrayType desc = rtt.getSoapArrayDescriptor();
      QName type_name = desc.getQName();
      if (type_name == null) {
         throw new AssertionError("null soap array type name for " + rtt);
      } else {
         CharSequence type_str = QNameTypeConverter.INSTANCE.print(type_name, result);
         result.fillAndAddAttribute(Soap11Constants.ARRAY_TYPE_NAME, type_str + desc.soap11DimensionString(array_dims));
      }
   }
}
