package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class JavaCharTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      String s = context.getStringValue();
      return this.getCharValue(s, context);
   }

   private Character getCharValue(CharSequence s, UnmarshalResult context) {
      if (s.length() < 1) {
         context.addError("invalid data for java character", context.getLocation());
         return null;
      } else {
         return new Character(s.charAt(0));
      }
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      String s = context.getAttributeStringValue();
      return this.getCharValue(s, context);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return this.getCharValue(lexical_value, result);
   }

   public CharSequence print(Object value, MarshalResult result) {
      Character ch = (Character)value;
      char[] ca = new char[]{ch};
      return new String(ca);
   }
}
