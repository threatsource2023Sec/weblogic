package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class ReplaceStringTypeConverter extends StringTypeConverter {
   private static final TypeConverter INSTANCE = new ReplaceStringTypeConverter();

   static TypeConverter getInstance() {
      return INSTANCE;
   }

   private ReplaceStringTypeConverter() {
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeStringValue(2);
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getStringValue(2);
   }
}
