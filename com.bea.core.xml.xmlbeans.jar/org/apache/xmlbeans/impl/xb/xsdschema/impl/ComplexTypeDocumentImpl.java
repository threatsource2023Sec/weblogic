package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.ComplexTypeDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelComplexType;

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
