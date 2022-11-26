package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.ExplicitGroup;
import com.bea.xbean.xb.xsdschema.SequenceDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class SequenceDocumentImpl extends XmlComplexContentImpl implements SequenceDocument {
   private static final QName SEQUENCE$0 = new QName("http://www.w3.org/2001/XMLSchema", "sequence");

   public SequenceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ExplicitGroup getSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSequence(ExplicitGroup sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$0, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$0);
         }

         target.set(sequence);
      }
   }

   public ExplicitGroup addNewSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$0);
         return target;
      }
   }
}
