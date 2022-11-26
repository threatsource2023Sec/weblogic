package com.bea.staxb.runtime.internal;

import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class AnyUriToStringTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getAnyUriValue();
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeAnyUriValue();
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return lexical_value.toString();
   }

   public CharSequence print(Object value, MarshalResult result) {
      String val = (String)value;
      return XsTypeConverter.printString(val);
   }
}
