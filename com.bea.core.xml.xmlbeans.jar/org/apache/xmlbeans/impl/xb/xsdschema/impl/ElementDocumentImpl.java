package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.ElementDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelElement;

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
