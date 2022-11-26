package com.bea.xbeanmarshal.buildtime.internal.bts;

import com.bea.xml.XmlException;

public class XmlBeanType extends BindingType {
   private static final long serialVersionUID = 1L;

   public XmlBeanType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }
}
