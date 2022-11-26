package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Facet;
import org.apache.xmlbeans.impl.xb.xsdschema.MaxExclusiveDocument;

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
