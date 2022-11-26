package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.LengthDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.NumFacet;

public class LengthDocumentImpl extends XmlComplexContentImpl implements LengthDocument {
   private static final QName LENGTH$0 = new QName("http://www.w3.org/2001/XMLSchema", "length");

   public LengthDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public NumFacet getLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)LENGTH$0, 0);
         return target == null ? null : target;
      }
   }

   public void setLength(NumFacet length) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user((QName)LENGTH$0, 0);
         if (target == null) {
            target = (NumFacet)this.get_store().add_element_user(LENGTH$0);
         }

         target.set(length);
      }
   }

   public NumFacet addNewLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(LENGTH$0);
         return target;
      }
   }
}
