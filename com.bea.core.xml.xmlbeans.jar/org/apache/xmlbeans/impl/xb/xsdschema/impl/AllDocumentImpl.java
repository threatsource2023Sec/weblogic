package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.apache.xmlbeans.impl.xb.xsdschema.AllDocument;

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
