package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.Facet;
import com.bea.xbean.xb.xsdschema.MaxInclusiveDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class MaxInclusiveDocumentImpl extends XmlComplexContentImpl implements MaxInclusiveDocument {
   private static final QName MAXINCLUSIVE$0 = new QName("http://www.w3.org/2001/XMLSchema", "maxInclusive");

   public MaxInclusiveDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Facet getMaxInclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MAXINCLUSIVE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMaxInclusive(Facet maxInclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user((QName)MAXINCLUSIVE$0, 0);
         if (target == null) {
            target = (Facet)this.get_store().add_element_user(MAXINCLUSIVE$0);
         }

         target.set(maxInclusive);
      }
   }

   public Facet addNewMaxInclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MAXINCLUSIVE$0);
         return target;
      }
   }
}
