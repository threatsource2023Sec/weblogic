package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Keybase;
import org.apache.xmlbeans.impl.xb.xsdschema.UniqueDocument;

public class UniqueDocumentImpl extends XmlComplexContentImpl implements UniqueDocument {
   private static final QName UNIQUE$0 = new QName("http://www.w3.org/2001/XMLSchema", "unique");

   public UniqueDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Keybase getUnique() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user((QName)UNIQUE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setUnique(Keybase unique) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user((QName)UNIQUE$0, 0);
         if (target == null) {
            target = (Keybase)this.get_store().add_element_user(UNIQUE$0);
         }

         target.set(unique);
      }
   }

   public Keybase addNewUnique() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().add_element_user(UNIQUE$0);
         return target;
      }
   }
}
