package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;

public class ListArrayType extends BindingType {
   private BindingTypeName itemType;
   private static final long serialVersionUID = 1L;

   public ListArrayType() {
   }

   public ListArrayType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public BindingTypeName getItemType() {
      return this.itemType;
   }

   public void setItemType(BindingTypeName itemType) {
      this.itemType = itemType;
   }
}
