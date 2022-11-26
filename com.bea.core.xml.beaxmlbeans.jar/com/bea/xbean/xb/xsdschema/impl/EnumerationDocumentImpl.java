package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.EnumerationDocument;
import com.bea.xbean.xb.xsdschema.NoFixedFacet;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

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
