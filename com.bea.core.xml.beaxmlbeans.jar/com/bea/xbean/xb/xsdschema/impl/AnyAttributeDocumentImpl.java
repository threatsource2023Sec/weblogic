package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.AnyAttributeDocument;
import com.bea.xbean.xb.xsdschema.Wildcard;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class AnyAttributeDocumentImpl extends XmlComplexContentImpl implements AnyAttributeDocument {
   private static final QName ANYATTRIBUTE$0 = new QName("http://www.w3.org/2001/XMLSchema", "anyAttribute");

   public AnyAttributeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Wildcard getAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAnyAttribute(Wildcard anyAttribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$0, 0);
         if (target == null) {
            target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$0);
         }

         target.set(anyAttribute);
      }
   }

   public Wildcard addNewAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$0);
         return target;
      }
   }
}
