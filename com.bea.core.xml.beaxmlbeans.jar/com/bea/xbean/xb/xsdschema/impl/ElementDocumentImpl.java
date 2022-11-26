package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.ElementDocument;
import com.bea.xbean.xb.xsdschema.TopLevelElement;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class ElementDocumentImpl extends XmlComplexContentImpl implements ElementDocument {
   private static final QName ELEMENT$0 = new QName("http://www.w3.org/2001/XMLSchema", "element");

   public ElementDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public TopLevelElement getElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelElement target = null;
         target = (TopLevelElement)this.get_store().find_element_user((QName)ELEMENT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setElement(TopLevelElement element) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelElement target = null;
         target = (TopLevelElement)this.get_store().find_element_user((QName)ELEMENT$0, 0);
         if (target == null) {
            target = (TopLevelElement)this.get_store().add_element_user(ELEMENT$0);
         }

         target.set(element);
      }
   }

   public TopLevelElement addNewElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopLevelElement target = null;
         target = (TopLevelElement)this.get_store().add_element_user(ELEMENT$0);
         return target;
      }
   }
}
