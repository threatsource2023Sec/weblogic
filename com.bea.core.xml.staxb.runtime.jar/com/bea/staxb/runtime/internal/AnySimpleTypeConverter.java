package com.bea.staxb.runtime.internal;

import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class AnySimpleTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getStringValue();
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeStringValue();
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return XsTypeConverter.lexString(lexical_value, result.getErrors());
   }

   public CharSequence print(Object value, MarshalResult result) {
      String val = (String)value;
      return XsTypeConverter.printString(val);
   }
}
