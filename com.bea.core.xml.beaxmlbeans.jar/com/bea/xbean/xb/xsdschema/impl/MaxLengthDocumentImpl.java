package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.MaxLengthDocument;
import com.bea.xbean.xb.xsdschema.NumFacet;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class MaxLengthDocumentImpl extends XmlComplexContentImpl implements MaxLengthDocument {
   private static final QName MAXLENGTH$0 = new QName("http://www.w3.org/2001/XMLSchema", "maxLength");

   public MaxLengthDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public NumFacet getMaxLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)MAXLENGTH$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMaxLength(NumFacet maxLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)MAXLENGTH$0, 0);
         if (target == null) {
            target = (NumFacet)this.get_store().add_element_user(MAXLENGTH$0);
         }

         target.set(maxLength);
      }
   }

   public NumFacet addNewMaxLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(MAXLENGTH$0);
         return target;
      }
   }
}
