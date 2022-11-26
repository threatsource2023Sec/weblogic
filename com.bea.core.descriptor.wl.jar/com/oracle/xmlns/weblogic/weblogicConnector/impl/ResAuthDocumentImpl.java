package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.ResAuthDocument;
import com.sun.java.xml.ns.javaee.ResAuthType;
import javax.xml.namespace.QName;

public class ResAuthDocumentImpl extends XmlComplexContentImpl implements ResAuthDocument {
   private static final long serialVersionUID = 1L;
   private static final QName RESAUTH$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "res-auth");

   public ResAuthDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ResAuthType getResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResAuthType target = null;
         target = (ResAuthType)this.get_store().find_element_user(RESAUTH$0, 0);
         return target == null ? null : target;
      }
   }

   public void setResAuth(ResAuthType resAuth) {
      this.generatedSetterHelperImpl(resAuth, RESAUTH$0, 0, (short)1);
   }

   public ResAuthType addNewResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResAuthType target = null;
         target = (ResAuthType)this.get_store().add_element_user(RESAUTH$0);
         return target;
      }
   }
}
