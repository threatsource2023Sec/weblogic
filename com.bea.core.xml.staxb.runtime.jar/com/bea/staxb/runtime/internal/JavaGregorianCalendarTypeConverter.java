package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.GDateSpecification;
import com.bea.xml.XmlException;
import java.util.GregorianCalendar;

final class JavaGregorianCalendarTypeConverter extends BaseSimpleTypeConverter {
   private final int schemaType;

   JavaGregorianCalendarTypeConverter(int schemaType) {
      this.schemaType = schemaType;
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getCalendarValue();
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeCalendarValue();
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         GDateSpecification gd = XsTypeConverter.getGDateValue(lexical_value, this.schemaType);
         return gd.getCalendar();
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      GregorianCalendar cal = (GregorianCalendar)value;
      GDateSpecification gd = XsTypeConverter.getGDateValue(cal, this.schemaType);
      return gd.toString();
   }
}
