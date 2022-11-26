package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.FractionDigitsDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.NumFacet;

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
