package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class ByteTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      byte val = context.getByteValue();
      return new Byte(val);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      byte val = context.getAttributeByteValue();
      return new Byte(val);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         byte b = XsTypeConverter.lexByte(lexical_value);
         return new Byte(b);
      } catch (NumberFormatException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Byte val = (Byte)value;
      return XsTypeConverter.printByte(val);
   }
}
