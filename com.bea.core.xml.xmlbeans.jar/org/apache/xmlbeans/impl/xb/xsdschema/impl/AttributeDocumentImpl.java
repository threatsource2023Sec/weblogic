package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.AttributeDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelAttribute;

public class AttributeDocumentImpl extends XmlComplexContentImpl implements AttributeDocument {
   private static final QName ATTRIBUTE$0 = new QName("http://www.w3.org/2001/XMLSchema", "attribute");

   public AttributeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public TopLevelAttribute getAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelAttribute target = null;
         target = (TopLevelAttribute)this.get_store().find_element_user((QName)ATTRIBUTE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAttribute(TopLevelAttribute attribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelAttribute target = null;
         target = (TopLevelAttribute)this.get_store().find_element_user((QName)ATTRIBUTE$0, 0);
         if (target == null) {
            target = (TopLevelAttribute)this.get_store().add_element_user(ATTRIBUTE$0);
         }

         target.set(attribute);
      }
   }

   public TopLevelAttribute addNewAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelAttribute target = null;
         target = (TopLevelAttribute)this.get_store().add_element_user(ATTRIBUTE$0);
         return target;
      }
   }
}
