package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;
import java.net.URI;
import java.net.URISyntaxException;

final class AnyUriToUriTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      String uri_val = context.getAnyUriValue();
      return stringToUri(uri_val, context);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      String uri_val = context.getAttributeAnyUriValue();
      return stringToUri(uri_val, context);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return stringToUri(lexical_value.toString(), result);
   }

   public CharSequence print(Object value, MarshalResult result) {
      URI val = (URI)value;
      return XsTypeConverter.printString(val.toString());
   }

   private static Object stringToUri(String uri_val, UnmarshalResult context) {
      try {
         return new URI(uri_val);
      } catch (URISyntaxException var3) {
         throw new InvalidLexicalValueException(var3, context.getLocation());
      }
   }
}
