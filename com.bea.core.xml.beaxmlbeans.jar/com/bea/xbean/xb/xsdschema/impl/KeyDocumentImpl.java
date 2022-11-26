package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.KeyDocument;
import com.bea.xbean.xb.xsdschema.Keybase;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class KeyDocumentImpl extends XmlComplexContentImpl implements KeyDocument {
   private static final QName KEY$0 = new QName("http://www.w3.org/2001/XMLSchema", "key");

   public KeyDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Keybase getKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user((QName)KEY$0, 0);
         return target == null ? null : target;
      }
   }

   public void setKey(Keybase key) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user((QName)KEY$0, 0);
         if (target == null) {
            target = (Keybase)this.get_store().add_element_user(KEY$0);
         }

         target.set(key);
      }
   }

   public Keybase addNewKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().add_element_user(KEY$0);
         return target;
      }
   }
}
