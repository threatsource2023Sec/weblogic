package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.ApplicationClientDocument;
import com.sun.java.xml.ns.j2Ee.ApplicationClientType;
import javax.xml.namespace.QName;

public class ApplicationClientDocumentImpl extends XmlComplexContentImpl implements ApplicationClientDocument {
   private static final long serialVersionUID = 1L;
   private static final QName APPLICATIONCLIENT$0 = new QName("http://java.sun.com/xml/ns/j2ee", "application-client");

   public ApplicationClientDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ApplicationClientType getApplicationClient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationClientType target = null;
         target = (ApplicationClientType)this.get_store().find_element_user(APPLICATIONCLIENT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setApplicationClient(ApplicationClientType applicationClient) {
      this.generatedSetterHelperImpl(applicationClient, APPLICATIONCLIENT$0, 0, (short)1);
   }

   public ApplicationClientType addNewApplicationClient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationClientType target = null;
         target = (ApplicationClientType)this.get_store().add_element_user(APPLICATIONCLIENT$0);
         return target;
      }
   }
}
