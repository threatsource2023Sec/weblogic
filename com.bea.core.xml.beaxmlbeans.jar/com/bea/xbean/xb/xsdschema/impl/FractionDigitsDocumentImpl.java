package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.FractionDigitsDocument;
import com.bea.xbean.xb.xsdschema.NumFacet;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class FractionDigitsDocumentImpl extends XmlComplexContentImpl implements FractionDigitsDocument {
   private static final QName FRACTIONDIGITS$0 = new QName("http://www.w3.org/2001/XMLSchema", "fractionDigits");

   public FractionDigitsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public NumFacet getFractionDigits() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)FRACTIONDIGITS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setFractionDigits(NumFacet fractionDigits) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)FRACTIONDIGITS$0, 0);
         if (target == null) {
            target = (NumFacet)this.get_store().add_element_user(FRACTIONDIGITS$0);
         }

         target.set(fractionDigits);
      }
   }

   public NumFacet addNewFractionDigits() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(FRACTIONDIGITS$0);
         return target;
      }
   }
}
