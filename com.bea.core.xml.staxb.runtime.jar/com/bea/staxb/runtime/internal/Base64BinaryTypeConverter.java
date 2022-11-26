package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;
import java.io.IOException;
import java.io.InputStream;

final class Base64BinaryTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      InputStream val = context.getBase64Value();

      try {
         return MarshalStreamUtils.inputStreamToBytes(val);
      } catch (IOException var4) {
         throw new XmlException(var4);
      }
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      InputStream val = context.getAttributeBase64Value();

      try {
         return MarshalStreamUtils.inputStreamToBytes(val);
      } catch (IOException var4) {
         throw new XmlException(var4);
      }
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         return XsTypeConverter.lexBase64Binary(lexical_value);
      } catch (InvalidLexicalValueException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      byte[] val = (byte[])((byte[])value);
      return XsTypeConverter.printBase64Binary(val);
   }
}
