package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class DoubleTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      double val = context.getDoubleValue();
      return new Double(val);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      double val = context.getAttributeDoubleValue();
      return new Double(val);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         double f = XsTypeConverter.lexDouble(lexical_value);
         return new Double(f);
      } catch (NumberFormatException var5) {
         throw new InvalidLexicalValueException(var5, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Double val = (Double)value;
      return XsTypeConverter.printDouble(val);
   }
}
