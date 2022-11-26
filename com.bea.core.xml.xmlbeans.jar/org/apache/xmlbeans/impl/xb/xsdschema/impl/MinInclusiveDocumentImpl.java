package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Facet;
import org.apache.xmlbeans.impl.xb.xsdschema.MinInclusiveDocument;

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
