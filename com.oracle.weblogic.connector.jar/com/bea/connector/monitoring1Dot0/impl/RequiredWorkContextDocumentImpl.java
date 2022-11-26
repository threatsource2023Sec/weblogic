package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.RequiredWorkContextDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class RequiredWorkContextDocumentImpl extends XmlComplexContentImpl implements RequiredWorkContextDocument {
   private static final long serialVersionUID = 1L;
   private static final QName REQUIREDWORKCONTEXT$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "required-work-context");

   public RequiredWorkContextDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getRequiredWorkContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIREDWORKCONTEXT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRequiredWorkContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REQUIREDWORKCONTEXT$0, 0);
         return target;
      }
   }

   public void setRequiredWorkContext(String requiredWorkContext) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIREDWORKCONTEXT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIREDWORKCONTEXT$0);
         }

         target.setStringValue(requiredWorkContext);
      }
   }

   public void xsetRequiredWorkContext(XmlString requiredWorkContext) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REQUIREDWORKCONTEXT$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REQUIREDWORKCONTEXT$0);
         }

         target.set(requiredWorkContext);
      }
   }
}
