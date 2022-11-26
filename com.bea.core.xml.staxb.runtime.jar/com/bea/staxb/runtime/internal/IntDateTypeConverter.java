package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.GDate;
import com.bea.xml.GDateBuilder;
import com.bea.xml.GDateSpecification;
import com.bea.xml.XmlException;

final class IntDateTypeConverter extends BaseSimpleTypeConverter {
   private final int schemaType;

   IntDateTypeConverter(int schemaType) {
      this.schemaType = schemaType;
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      GDate gdate = context.getGDateValue();
      return this.extractIntValue(gdate);
   }

   private Object extractIntValue(GDateSpecification gdate) {
      int val;
      switch (this.schemaType) {
         case 18:
            val = gdate.getYear();
            break;
         case 19:
         default:
            throw new AssertionError("inapplicable type: " + this.schemaType);
         case 20:
            val = gdate.getDay();
            break;
         case 21:
            val = gdate.getMonth();
      }

      return new Integer(val);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      GDate gdate = context.getAttributeGDateValue();
      return this.extractIntValue(gdate);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         GDateSpecification gd = XsTypeConverter.getGDateValue(lexical_value, this.schemaType);
         return this.extractIntValue(gd);
      } catch (IllegalArgumentException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) throws XmlException {
      int i = (Integer)value;

      try {
         GDateBuilder b = new GDateBuilder();
         switch (this.schemaType) {
            case 18:
               b.setYear(i);
               break;
            case 19:
            default:
               throw new AssertionError("inapplicable type: " + this.schemaType);
            case 20:
               b.setDay(i);
               break;
            case 21:
               b.setMonth(i);
         }

         b.setBuiltinTypeCode(this.schemaType);
         return b.toString();
      } catch (IllegalArgumentException var5) {
         throw new XmlException(var5);
      }
   }
}
