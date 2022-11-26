package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.ComplexTypeDocument;
import com.bea.xbean.xb.xsdschema.TopLevelComplexType;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class ComplexTypeDocumentImpl extends XmlComplexContentImpl implements ComplexTypeDocument {
   private static final QName COMPLEXTYPE$0 = new QName("http://www.w3.org/2001/XMLSchema", "complexType");

   public ComplexTypeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public TopLevelComplexType getComplexType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelComplexType target = null;
         target = (TopLevelComplexType)this.get_store().find_element_user((QName)COMPLEXTYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setComplexType(TopLevelComplexType complexType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelComplexType target = null;
         target = (TopLevelComplexType)this.get_store().find_element_user((QName)COMPLEXTYPE$0, 0);
         if (target == null) {
            target = (TopLevelComplexType)this.get_store().add_element_user(COMPLEXTYPE$0);
         }

         target.set(complexType);
      }
   }

   public TopLevelComplexType addNewComplexType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelComplexType target = null;
         target = (TopLevelComplexType)this.get_store().add_element_user(COMPLEXTYPE$0);
         return target;
      }
   }
}
