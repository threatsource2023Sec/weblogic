package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding;
import com.bea.ns.staxb.bindingConfig.x90.XmlSignature;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import javax.xml.namespace.QName;

public class SimpleDocumentBindingImpl extends BindingTypeImpl implements SimpleDocumentBinding {
   private static final long serialVersionUID = 1L;
   private static final QName TYPEOFELEMENT$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "type-of-element");

   public SimpleDocumentBindingImpl(SchemaType sType) {
      super(sType);
   }

   public String getTypeOfElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPEOFELEMENT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlSignature xgetTypeOfElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(TYPEOFELEMENT$0, 0);
         return target;
      }
   }

   public void setTypeOfElement(String typeOfElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPEOFELEMENT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPEOFELEMENT$0);
         }

         target.setStringValue(typeOfElement);
      }
   }

   public void xsetTypeOfElement(XmlSignature typeOfElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(TYPEOFELEMENT$0, 0);
         if (target == null) {
            target = (XmlSignature)this.get_store().add_element_user(TYPEOFELEMENT$0);
         }

         target.set(typeOfElement);
      }
   }
}
