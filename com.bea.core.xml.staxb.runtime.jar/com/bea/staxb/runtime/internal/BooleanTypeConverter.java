package com.bea.staxb.runtime.internal;

import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class BooleanTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      boolean b = context.getBooleanValue();
      return b;
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      boolean b = context.getAttributeBooleanValue();
      return b;
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      boolean b = XsTypeConverter.lexBoolean(lexical_value);
      return b;
   }

   public CharSequence print(Object value, MarshalResult result) {
      Boolean b = (Boolean)value;
      return XsTypeConverter.printBoolean(b);
   }
}
