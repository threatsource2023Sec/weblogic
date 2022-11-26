package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.ChoiceDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.ExplicitGroup;

public class ChoiceDocumentImpl extends XmlComplexContentImpl implements ChoiceDocument {
   private static final QName CHOICE$0 = new QName("http://www.w3.org/2001/XMLSchema", "choice");

   public ChoiceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ExplicitGroup getChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setChoice(ExplicitGroup choice) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$0, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$0);
         }

         target.set(choice);
      }
   }

   public ExplicitGroup addNewChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$0);
         return target;
      }
   }
}
