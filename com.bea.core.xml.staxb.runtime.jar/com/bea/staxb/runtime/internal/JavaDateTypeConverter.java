package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.GDateSpecification;
import com.bea.xml.XmlException;
import java.util.Date;

final class JavaDateTypeConverter extends BaseSimpleTypeConverter {
   private final int schemaType;

   JavaDateTypeConverter(int schemaType) {
      this.schemaType = schemaType;
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getDateValue();
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeDateValue();
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         GDateSpecification gd = XsTypeConverter.getGDateValue(lexical_value, this.schemaType);
         return gd.getDate();
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Date d = (Date)value;
      GDateSpecification gd = XsTypeConverter.getGDateValue(d, this.schemaType);
      return gd.toString();
   }
}
