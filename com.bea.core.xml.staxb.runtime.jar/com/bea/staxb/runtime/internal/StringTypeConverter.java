package com.bea.staxb.runtime.internal;

import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

class StringTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getStringValue();
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeStringValue();
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return XsTypeConverter.lexString(lexical_value);
   }

   public CharSequence print(Object value, MarshalResult result) {
      assert value instanceof String : "expected String type, not " + value.getClass();

      String val = (String)value;
      return XsTypeConverter.printString(val);
   }
}
