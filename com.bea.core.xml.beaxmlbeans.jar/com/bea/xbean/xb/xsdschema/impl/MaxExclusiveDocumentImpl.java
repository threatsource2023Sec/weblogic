package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.Facet;
import com.bea.xbean.xb.xsdschema.MaxExclusiveDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class MaxExclusiveDocumentImpl extends XmlComplexContentImpl implements MaxExclusiveDocument {
   private static final QName MAXEXCLUSIVE$0 = new QName("http://www.w3.org/2001/XMLSchema", "maxExclusive");

   public MaxExclusiveDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Facet getMaxExclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MAXEXCLUSIVE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMaxExclusive(Facet maxExclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MAXEXCLUSIVE$0, 0);
         if (target == null) {
            target = (Facet)this.get_store().add_element_user(MAXEXCLUSIVE$0);
         }

         target.set(maxExclusive);
      }
   }

   public Facet addNewMaxExclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MAXEXCLUSIVE$0);
         return target;
      }
   }
}
