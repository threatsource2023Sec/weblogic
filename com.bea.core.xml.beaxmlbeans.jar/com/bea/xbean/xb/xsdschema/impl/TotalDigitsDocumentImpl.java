package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.TotalDigitsDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class TotalDigitsDocumentImpl extends XmlComplexContentImpl implements TotalDigitsDocument {
   private static final QName TOTALDIGITS$0 = new QName("http://www.w3.org/2001/XMLSchema", "totalDigits");

   public TotalDigitsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public TotalDigitsDocument.TotalDigits getTotalDigits() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TotalDigitsDocument.TotalDigits target = null;
         target = (TotalDigitsDocument.TotalDigits)this.get_store().find_element_user((QName)TOTALDIGITS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setTotalDigits(TotalDigitsDocument.TotalDigits totalDigits) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TotalDigitsDocument.TotalDigits target = null;
         target = (TotalDigitsDocument.TotalDigits)this.get_store().find_element_user((QName)TOTALDIGITS$0, 0);
         if (target == null) {
            target = (TotalDigitsDocument.TotalDigits)this.get_store().add_element_user(TOTALDIGITS$0);
         }

         target.set(totalDigits);
      }
   }

   public TotalDigitsDocument.TotalDigits addNewTotalDigits() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TotalDigitsDocument.TotalDigits target = null;
         target = (TotalDigitsDocument.TotalDigits)this.get_store().add_element_user(TOTALDIGITS$0);
         return target;
      }
   }

   public static class TotalDigitsImpl extends NumFacetImpl implements TotalDigitsDocument.TotalDigits {
      public TotalDigitsImpl(SchemaType sType) {
         super(sType);
      }
   }
}
