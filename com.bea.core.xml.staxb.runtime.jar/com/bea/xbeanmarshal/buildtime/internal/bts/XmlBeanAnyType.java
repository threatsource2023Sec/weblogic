package com.bea.xbeanmarshal.buildtime.internal.bts;

import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;

public class XmlBeanAnyType extends BindingType {
   private static final long serialVersionUID = 1L;
   public static final XmlBeanAnyType BEA = new XmlBeanAnyType(BindingTypeName.forPair(JavaTypeName.forClassName(XmlObject.class.getName()), XmlTypeName.forElementWildCardElement()));
   public static final XmlBeanAnyType APACHE = new XmlBeanAnyType(BindingTypeName.forPair(JavaTypeName.forClassName("org.apache.xmlbeans.XmlObject"), XmlTypeName.forElementWildCardElement()));

   private XmlBeanAnyType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }
}
