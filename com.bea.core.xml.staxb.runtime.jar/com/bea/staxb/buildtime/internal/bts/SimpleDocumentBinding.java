package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;

public class SimpleDocumentBinding extends BindingType {
   private static final long serialVersionUID = 1L;
   private XmlTypeName typeOfElement;

   public SimpleDocumentBinding() {
   }

   public SimpleDocumentBinding(BindingTypeName btname) {
      super(btname);
   }

   public XmlTypeName getTypeOfElement() {
      return this.typeOfElement;
   }

   public void setTypeOfElement(XmlTypeName typeOfElement) {
      this.typeOfElement = typeOfElement;
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }
}
