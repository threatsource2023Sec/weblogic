package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BuiltinBindingType;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

final class QNameRuntimeBindingType extends BuiltinRuntimeBindingType {
   static final QName QNAME_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "QName".intern());

   QNameRuntimeBindingType(BuiltinBindingType type, TypeConverter converter) throws XmlException {
      super(type, converter);
   }

   protected boolean canUseDefaultNamespace(Object obj) throws XmlException {
      if (obj == null) {
         return true;
      } else {
         assert obj instanceof QName;

         QName qn = (QName)obj;
         return !"".equals(qn.getNamespaceURI());
      }
   }
}
