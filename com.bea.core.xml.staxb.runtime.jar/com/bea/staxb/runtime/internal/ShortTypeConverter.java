package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class ShortTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      short val = context.getShortValue();
      return new Short(val);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      short val = context.getAttributeShortValue();
      return new Short(val);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         short b = XsTypeConverter.lexShort(lexical_value);
         return new Short(b);
      } catch (NumberFormatException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Short val = (Short)value;
      return XsTypeConverter.printShort(val);
   }
}
