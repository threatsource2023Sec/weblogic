package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.Facet;
import com.bea.xbean.xb.xsdschema.MinExclusiveDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class MinExclusiveDocumentImpl extends XmlComplexContentImpl implements MinExclusiveDocument {
   private static final QName MINEXCLUSIVE$0 = new QName("http://www.w3.org/2001/XMLSchema", "minExclusive");

   public MinExclusiveDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Facet getMinExclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MINEXCLUSIVE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMinExclusive(Facet minExclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MINEXCLUSIVE$0, 0);
         if (target == null) {
            target = (Facet)this.get_store().add_element_user(MINEXCLUSIVE$0);
         }

         target.set(minExclusive);
      }
   }

   public Facet addNewMinExclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MINEXCLUSIVE$0);
         return target;
      }
   }
}
