package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;

public class SimpleBindingType extends BindingType {
   private XmlTypeName asIfXmlType;
   private int whitespace = 0;
   private static final long serialVersionUID = 1L;

   public SimpleBindingType() {
   }

   public SimpleBindingType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public XmlTypeName getAsIfXmlType() {
      return this.asIfXmlType;
   }

   public void setAsIfXmlType(XmlTypeName asIfXmlType) {
      this.asIfXmlType = asIfXmlType;
   }

   public BindingTypeName getAsIfBindingTypeName() {
      if (this.getAsIfXmlType() == null) {
         throw new IllegalStateException("SimpleBindingType must have an asIfXmlType " + this);
      } else {
         return BindingTypeName.forPair(this.getName().getJavaName(), this.getAsIfXmlType());
      }
   }

   public int getWhitespace() {
      return this.whitespace;
   }

   public void setWhitespace(int ws) {
      switch (ws) {
         case 0:
         case 1:
         case 2:
         case 3:
            this.whitespace = ws;
            return;
         default:
            throw new IllegalArgumentException("invalid whitespace: " + ws);
      }
   }
}
