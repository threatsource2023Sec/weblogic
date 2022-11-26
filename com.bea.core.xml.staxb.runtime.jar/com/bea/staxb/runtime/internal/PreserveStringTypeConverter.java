package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class PreserveStringTypeConverter extends StringTypeConverter {
   private static final TypeConverter INSTANCE = new PreserveStringTypeConverter();

   static TypeConverter getInstance() {
      return INSTANCE;
   }

   private PreserveStringTypeConverter() {
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeStringValue(1);
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getStringValue(1);
   }
}
