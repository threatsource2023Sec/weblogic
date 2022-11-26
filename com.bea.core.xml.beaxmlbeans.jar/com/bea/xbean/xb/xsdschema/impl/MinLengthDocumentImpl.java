package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.MinLengthDocument;
import com.bea.xbean.xb.xsdschema.NumFacet;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class MinLengthDocumentImpl extends XmlComplexContentImpl implements MinLengthDocument {
   private static final QName MINLENGTH$0 = new QName("http://www.w3.org/2001/XMLSchema", "minLength");

   public MinLengthDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public NumFacet getMinLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)MINLENGTH$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMinLength(NumFacet minLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)MINLENGTH$0, 0);
         if (target == null) {
            target = (NumFacet)this.get_store().add_element_user(MINLENGTH$0);
         }

         target.set(minLength);
      }
   }

   public NumFacet addNewMinLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(MINLENGTH$0);
         return target;
      }
   }
}
