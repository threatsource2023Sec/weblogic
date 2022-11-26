package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingType;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

public final class AnyRuntimeBindingType extends AnyTypeRuntimeBindingType {
   static final QName ANY_NAME = new QName("http://www.w3.org/2001/XMLSchema", "any");
   public static final BindingTypeName ANY_TYPE_NAME = createSoapElementTypeName();

   private static BindingTypeName createSoapElementTypeName() {
      JavaTypeName java_type = JavaTypeName.forClassName(SOAPElement.class.getName());
      XmlTypeName xml_type = XmlTypeName.forTypeNamed(ANY_NAME);
      return BindingTypeName.forPair(java_type, xml_type);
   }

   AnyRuntimeBindingType(BuiltinBindingType type, TypeConverter converter) throws XmlException {
      super(type, converter);
   }

   QName getQName() {
      return ANY_NAME;
   }

   BindingTypeName getSoapElementTypeName() {
      return ANY_TYPE_NAME;
   }
}
