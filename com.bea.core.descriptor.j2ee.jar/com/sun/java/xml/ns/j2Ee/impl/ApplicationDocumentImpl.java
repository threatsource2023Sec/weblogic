package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.ApplicationDocument;
import com.sun.java.xml.ns.j2Ee.ApplicationType;
import javax.xml.namespace.QName;

public class ApplicationDocumentImpl extends XmlComplexContentImpl implements ApplicationDocument {
   private static final long serialVersionUID = 1L;
   private static final QName APPLICATION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "application");

   public ApplicationDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ApplicationType getApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationType target = null;
         target = (ApplicationType)this.get_store().find_element_user(APPLICATION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setApplication(ApplicationType application) {
      this.generatedSetterHelperImpl(application, APPLICATION$0, 0, (short)1);
   }

   public ApplicationType addNewApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationType target = null;
         target = (ApplicationType)this.get_store().add_element_user(APPLICATION$0);
         return target;
      }
   }
}
