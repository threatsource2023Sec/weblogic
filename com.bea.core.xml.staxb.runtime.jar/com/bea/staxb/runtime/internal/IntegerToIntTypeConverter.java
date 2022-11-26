package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;
import java.math.BigInteger;

final class IntegerToIntTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      return BigIntegerToInteger(context.getBigIntegerValue());
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return BigIntegerToInteger(context.getAttributeBigIntegerValue());
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         return XsTypeConverter.lexInteger(lexical_value);
      } catch (NumberFormatException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Number val = (Number)value;
      return XsTypeConverter.printInt(val.intValue());
   }

   private static Object BigIntegerToInteger(BigInteger val) {
      int ival = val.intValue();
      return new Integer(ival);
   }
}
