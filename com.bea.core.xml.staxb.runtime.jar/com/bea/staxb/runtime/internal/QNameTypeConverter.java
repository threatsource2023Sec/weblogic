package com.bea.staxb.runtime.internal;

import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

final class QNameTypeConverter extends BaseSimpleTypeConverter {
   static final QNameTypeConverter INSTANCE = new QNameTypeConverter();

   protected Object getObject(UnmarshalResult context) throws XmlException {
      QName val = context.getQNameValue();
      return val;
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return context.getAttributeQNameValue();
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      return XsTypeConverter.lexQName(lexical_value, result.getNamespaceContext());
   }

   public CharSequence print(Object value, MarshalResult result) throws XmlException {
      assert value != null;

      QName val = (QName)value;
      String uri = val.getNamespaceURI();
      if (uri.length() > 0) {
         result.ensureElementPrefix(uri);
      }

      return XsTypeConverter.printQName(val, result.getNamespaceContext(), result.getErrorCollection());
   }
}
