package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.PrincipalNameDocument;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class PrincipalNameDocumentImpl extends XmlComplexContentImpl implements PrincipalNameDocument {
   private static final long serialVersionUID = 1L;
   private static final QName PRINCIPALNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "principal-name");

   public PrincipalNameDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PRINCIPALNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPrincipalName(String principalName) {
      this.generatedSetterHelperImpl(principalName, PRINCIPALNAME$0, 0, (short)1);
   }

   public String addNewPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PRINCIPALNAME$0);
         return target;
      }
   }
}
