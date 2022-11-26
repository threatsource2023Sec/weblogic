package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.All;
import com.bea.xbean.xb.xsdschema.AllDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class AllDocumentImpl extends XmlComplexContentImpl implements AllDocument {
   private static final QName ALL$0 = new QName("http://www.w3.org/2001/XMLSchema", "all");

   public AllDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public All getAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAll(All all) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$0, 0);
         if (target == null) {
            target = (All)this.get_store().add_element_user(ALL$0);
         }

         target.set(all);
      }
   }

   public All addNewAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().add_element_user(ALL$0);
         return target;
      }
   }
}
