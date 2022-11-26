package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.MaxLengthDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.NumFacet;

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
