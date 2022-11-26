package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.EnumerationDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.NoFixedFacet;

public class EnumerationDocumentImpl extends XmlComplexContentImpl implements EnumerationDocument {
   private static final QName ENUMERATION$0 = new QName("http://www.w3.org/2001/XMLSchema", "enumeration");

   public EnumerationDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public NoFixedFacet getEnumeration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoFixedFacet target = null;
         target = (NoFixedFacet)this.get_store().find_element_user((QName)ENUMERATION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEnumeration(NoFixedFacet enumeration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoFixedFacet target = null;
         target = (NoFixedFacet)this.get_store().find_element_user((QName)ENUMERATION$0, 0);
         if (target == null) {
            target = (NoFixedFacet)this.get_store().add_element_user(ENUMERATION$0);
         }

         target.set(enumeration);
      }
   }

   public NoFixedFacet addNewEnumeration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoFixedFacet target = null;
         target = (NoFixedFacet)this.get_store().add_element_user(ENUMERATION$0);
         return target;
      }
   }
}
