package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class IntTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      int val = context.getIntValue();
      return new Integer(val);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      int val = context.getAttributeIntValue();
      return new Integer(val);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         int f = XsTypeConverter.lexInt(lexical_value);
         return new Integer(f);
      } catch (NumberFormatException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Integer val = (Integer)value;
      return XsTypeConverter.printInt(val);
   }
}
