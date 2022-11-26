package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.collections.StringList;
import com.bea.xml.XmlException;

final class StringListArrayConverter extends BaseSimpleTypeConverter {
   private static final Class STR_ARRAY_TYPE = (new String[0]).getClass();
   private static final char SPACE = ' ';
   private static final TypeUnmarshaller STR_UNMARSHALLER = new StringTypeConverter();

   protected Object getObject(UnmarshalResult context) throws XmlException {
      String str = context.getStringValue();
      return ListArrayConverter.unmarshalListString(new StringList(), str, STR_UNMARSHALLER, context);
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      String str = context.getAttributeStringValue();
      return ListArrayConverter.unmarshalListString(new StringList(), str, STR_UNMARSHALLER, context);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return ListArrayConverter.unmarshalListString(new StringList(), lexical_value.toString(), STR_UNMARSHALLER, result);
   }

   public CharSequence print(Object value, MarshalResult result) {
      String[] val = (String[])((String[])value);
      int alen = val.length;
      if (alen == 0) {
         return "";
      } else {
         StringBuffer buf = new StringBuffer();
         buf.append(val[0]);

         for(int i = 1; i < alen; ++i) {
            buf.append(' ');
            buf.append(val[i]);
         }

         return buf;
      }
   }
}
