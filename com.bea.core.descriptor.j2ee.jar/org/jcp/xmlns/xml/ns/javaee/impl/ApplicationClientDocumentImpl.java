package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ApplicationClientDocument;
import org.jcp.xmlns.xml.ns.javaee.ApplicationClientType;

public class ApplicationClientDocumentImpl extends XmlComplexContentImpl implements ApplicationClientDocument {
   private static final long serialVersionUID = 1L;
   private static final QName APPLICATIONCLIENT$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "application-client");

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
