package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class LongTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      long val = context.getLongValue();
      return new Long(val);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      long val = context.getAttributeLongValue();
      return new Long(val);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         long b = XsTypeConverter.lexLong(lexical_value);
         return new Long(b);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Long val = (Long)value;
      return XsTypeConverter.printLong(val);
   }
}
