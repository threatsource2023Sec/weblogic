package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.TotalDigitsDocument;

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
