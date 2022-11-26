package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;
import java.io.IOException;
import java.io.InputStream;

final class HexBinaryTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      InputStream val = context.getHexBinaryValue();

      try {
         return MarshalStreamUtils.inputStreamToBytes(val);
      } catch (IOException var4) {
         throw new XmlException(var4);
      }
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      InputStream val = context.getAttributeHexBinaryValue();

      try {
         return MarshalStreamUtils.inputStreamToBytes(val);
      } catch (IOException var4) {
         throw new XmlException(var4);
      }
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         return XsTypeConverter.lexHexBinary(lexical_value);
      } catch (InvalidLexicalValueException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      byte[] val = (byte[])((byte[])value);
      return XsTypeConverter.printHexBinary(val);
   }
}
