package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.ArrayUtils;
import com.bea.staxb.runtime.internal.util.collections.Accumulator;
import com.bea.staxb.runtime.internal.util.collections.AccumulatorFactory;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xml.XmlException;
import java.util.Iterator;

final class ListArrayConverter extends BaseSimpleTypeConverter {
   private final ListArrayRuntimeBindingType listType;
   private static final char SPACE = ' ';

   ListArrayConverter(ListArrayRuntimeBindingType rtt) {
      this.listType = rtt;
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      String str = context.getStringValue();
      return this.getObjectFromListContent(str, context);
   }

   public Object unmarshalAttribute(UnmarshalResult result) throws XmlException {
      String str = result.getAttributeStringValue();
      return this.getObjectFromListContent(str, result);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return this.getObjectFromListContent(lexical_value.toString(), result);
   }

   public CharSequence print(Object value, MarshalResult result) throws XmlException {
      Iterator itr = ArrayUtils.getCollectionIterator(value);
      if (!itr.hasNext()) {
         return "";
      } else {
         RuntimeBindingProperty item_prop = this.listType.getItemProperty();
         StringBuffer buf = new StringBuffer();
         Object first = itr.next();
         if (first != null) {
            CharSequence lex = item_prop.getLexical(first, result);
            buf.append(lex);
         }

         while(itr.hasNext()) {
            Object item = itr.next();
            if (item != null) {
               CharSequence lex = item_prop.getLexical(item, result);
               buf.append(' ');
               buf.append(lex);
            }
         }

         return buf;
      }
   }

   protected Object getObjectFromListContent(String str, UnmarshalResult context) throws XmlException {
      RuntimeBindingProperty item_prop = this.listType.getItemProperty();
      Class list_java_type = this.listType.getJavaType();
      Class item_java_type = item_prop.getRuntimeBindingType().getJavaType();
      TypeUnmarshaller item_um = item_prop.getRuntimeBindingType().getUnmarshaller();
      return unmarshalListString(str, list_java_type, item_java_type, item_um, context);
   }

   protected static Object unmarshalListString(CharSequence str, Class list_java_type, Class item_java_type, TypeUnmarshaller item_um, UnmarshalResult context) throws XmlException {
      Accumulator accum = AccumulatorFactory.createAccumulator(list_java_type, item_java_type);
      return unmarshalListString(accum, str, item_um, context);
   }

   protected static Object unmarshalListString(Accumulator accum, CharSequence str, TypeUnmarshaller item_um, UnmarshalResult context) throws XmlException {
      String lex = str.toString();
      String[] strings = XmlListImpl.split_list(lex);
      int i = 0;

      for(int alen = strings.length; i < alen; ++i) {
         String s = strings[i];
         Object val = item_um.unmarshalAttribute((CharSequence)s, context);
         accum.append(val);
      }

      return accum.getFinalArray();
   }
}
