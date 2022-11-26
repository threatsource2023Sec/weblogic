package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.Facet;
import com.bea.xbean.xb.xsdschema.MinInclusiveDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class MinInclusiveDocumentImpl extends XmlComplexContentImpl implements MinInclusiveDocument {
   private static final QName MININCLUSIVE$0 = new QName("http://www.w3.org/2001/XMLSchema", "minInclusive");

   public MinInclusiveDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Facet getMinInclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MININCLUSIVE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMinInclusive(Facet minInclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MININCLUSIVE$0, 0);
         if (target == null) {
            target = (Facet)this.get_store().add_element_user(MININCLUSIVE$0);
         }

         target.set(minInclusive);
      }
   }

   public Facet addNewMinInclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MININCLUSIVE$0);
         return target;
      }
   }
}
