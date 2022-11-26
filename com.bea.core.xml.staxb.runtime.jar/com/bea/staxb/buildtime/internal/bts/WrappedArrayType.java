package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

public class WrappedArrayType extends BindingType {
   private QName itemName;
   private BindingTypeName itemType;
   private boolean itemNillable;
   private static final long serialVersionUID = 1L;

   public WrappedArrayType() {
   }

   public WrappedArrayType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public QName getItemName() {
      return this.itemName;
   }

   public void setItemName(QName itemName) {
      this.itemName = itemName;
   }

   public BindingTypeName getItemType() {
      return this.itemType;
   }

   public void setItemType(BindingTypeName itemType) {
      this.itemType = itemType;
   }

   public boolean isItemNillable() {
      return this.itemNillable;
   }

   public void setItemNillable(boolean nillable) {
      this.itemNillable = nillable;
   }
}
