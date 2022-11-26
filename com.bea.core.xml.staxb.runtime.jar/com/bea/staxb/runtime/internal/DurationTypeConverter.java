package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xml.GDuration;
import com.bea.xml.XmlException;

final class DurationTypeConverter extends BaseSimpleTypeConverter {
   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getGDurationValue();
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeGDurationValue();
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         return new GDuration(lexical_value);
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      GDuration cal = (GDuration)value;
      return cal.toString();
   }
}
