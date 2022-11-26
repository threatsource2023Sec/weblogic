package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleTypeDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelSimpleType;

public class SimpleTypeDocumentImpl extends XmlComplexContentImpl implements SimpleTypeDocument {
   private static final QName SIMPLETYPE$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");

   public SimpleTypeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public TopLevelSimpleType getSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelSimpleType target = null;
         target = (TopLevelSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSimpleType(TopLevelSimpleType simpleType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelSimpleType target = null;
         target = (TopLevelSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
         if (target == null) {
            target = (TopLevelSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
         }

         target.set(simpleType);
      }
   }

   public TopLevelSimpleType addNewSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelSimpleType target = null;
         target = (TopLevelSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
         return target;
      }
   }
}
