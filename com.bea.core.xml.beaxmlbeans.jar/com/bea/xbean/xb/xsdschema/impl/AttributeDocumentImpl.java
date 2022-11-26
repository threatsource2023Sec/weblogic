package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.AttributeDocument;
import com.bea.xbean.xb.xsdschema.TopLevelAttribute;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

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
