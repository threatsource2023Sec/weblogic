package com.bea.staxb.runtime.internal;

import com.bea.staxb.types.XMLGregorianCalendar;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.GDateSpecification;
import com.bea.xml.XmlException;

final class XMLCalendarTypeConverter extends BaseSimpleTypeConverter {
   private final int schemaType;

   XMLCalendarTypeConverter(int schemaType) {
      this.schemaType = schemaType;
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      return new XMLGregorianCalendar(context.getGDateValue());
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return new XMLGregorianCalendar(context.getAttributeGDateValue());
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         GDateSpecification gd = XsTypeConverter.getGDateValue(lexical_value, this.schemaType);
         return new XMLGregorianCalendar(gd);
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      return value.toString();
   }
}
