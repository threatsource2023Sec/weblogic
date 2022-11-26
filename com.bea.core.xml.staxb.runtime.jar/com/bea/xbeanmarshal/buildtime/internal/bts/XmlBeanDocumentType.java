package com.bea.xbeanmarshal.buildtime.internal.bts;

import com.bea.xml.XmlException;

public class XmlBeanDocumentType extends BindingType {
   private static final long serialVersionUID = 1L;

   public XmlBeanDocumentType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }
}
