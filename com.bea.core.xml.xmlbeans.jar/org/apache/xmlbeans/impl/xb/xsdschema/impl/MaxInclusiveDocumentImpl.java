package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Facet;
import org.apache.xmlbeans.impl.xb.xsdschema.MaxInclusiveDocument;

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
