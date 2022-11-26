package com.bea.xbeanmarshal.buildtime.internal.bts;

import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import javax.xml.namespace.QName;

public class WrappedArrayType extends BindingType {
   private QName itemName;
   private BindingTypeName itemType;
   private boolean itemNillable;
   public static final WrappedArrayType BEA_ANY_ARRAY = createGenericWrappedArrayType(XmlObject.class.getName());
   public static final WrappedArrayType APACHE_ANY_ARRAY = createGenericWrappedArrayType("org.apache.xmlbeans.XmlObject");
   private static final long serialVersionUID = 1L;

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

   private static WrappedArrayType createGenericWrappedArrayType(String arrayComponentClassName) {
      JavaTypeName arrayJtn = JavaTypeName.forString(arrayComponentClassName + "[]");
      XmlTypeName arrayXtn = XmlTypeName.forTypeNamed(new QName("http://com.bea/wls92/com/bea/staxb/buildtime/internal/bts", "arrayOfXmlObject".intern()));
      BindingTypeName arrayBtn = BindingTypeName.forPair(arrayJtn, arrayXtn);
      JavaTypeName arrayItemJtn = arrayJtn.getArrayTypeMinus1Dim(1);
      XmlTypeName arrayItemXtn = XmlTypeName.forTypeNamed(new QName("http://www.w3.org/2001/XMLSchema".intern(), "anyType".intern()));
      BindingTypeName arrayItemBtn = BindingTypeName.forPair(arrayItemJtn, arrayItemXtn);
      XmlTypeName arrayItemElementXtn = XmlTypeName.forElementWildCardElement();
      WrappedArrayType wat = new WrappedArrayType(arrayBtn);
      wat.setItemName(arrayItemElementXtn.getQName());
      wat.setItemType(arrayItemBtn);
      wat.setItemNillable(true);
      return wat;
   }

   public static WrappedArrayType wrappedArrayTypeFor(Class arrayComponent) {
      return XmlObject.class.isAssignableFrom(arrayComponent) ? BEA_ANY_ARRAY : APACHE_ANY_ARRAY;
   }
}
