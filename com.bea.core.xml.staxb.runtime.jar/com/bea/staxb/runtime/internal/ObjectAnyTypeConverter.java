package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import javax.xml.soap.SOAPElement;

public final class ObjectAnyTypeConverter implements TypeConverter, GenericXmlMarshaller {
   public Object unmarshal(UnmarshalResult result) throws XmlException {
      assert result.getXsiType() == null : " xsi-type is " + result.getXsiType();

      SOAPElement se = result.getCurrentSOAPElementNode();
      result.skipElement();
      return se;
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result) {
      throw new UnsupportedOperationException("not used: " + this);
   }

   public Object unmarshalAttribute(UnmarshalResult result) throws XmlException {
      throw new AssertionError("unused");
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      throw new AssertionError("unused");
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) {
   }

   public CharSequence print(Object value, MarshalResult result) throws XmlException {
      throw new XmlException("don't know how to print " + value.getClass());
   }

   public void marshalGenericXml(MarshalResult result, Object obj) throws XmlException {
      throw new XmlException("don't know how to marshal " + obj.getClass());
   }
}
