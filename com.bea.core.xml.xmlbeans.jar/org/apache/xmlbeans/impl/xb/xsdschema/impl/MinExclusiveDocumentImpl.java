package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Facet;
import org.apache.xmlbeans.impl.xb.xsdschema.MinExclusiveDocument;

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
