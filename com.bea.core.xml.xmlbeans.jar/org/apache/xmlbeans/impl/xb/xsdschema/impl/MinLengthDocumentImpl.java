package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.MinLengthDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.NumFacet;

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
