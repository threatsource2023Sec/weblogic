package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

final class CollapseStringTypeConverter extends StringTypeConverter {
   private static final TypeConverter INSTANCE = new CollapseStringTypeConverter();

   static TypeConverter getInstance() {
      return INSTANCE;
   }

   private CollapseStringTypeConverter() {
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeStringValue(3);
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      return context.getStringValue(3);
   }
}
