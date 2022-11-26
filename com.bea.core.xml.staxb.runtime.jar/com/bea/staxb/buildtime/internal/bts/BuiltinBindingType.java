package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;

public class BuiltinBindingType extends BindingType {
   private static final long serialVersionUID = 1L;

   public BuiltinBindingType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }
}
