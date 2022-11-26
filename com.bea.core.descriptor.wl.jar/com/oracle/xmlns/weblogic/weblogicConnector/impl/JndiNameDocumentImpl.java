package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.JndiNameDocument;
import com.sun.java.xml.ns.javaee.JndiNameType;
import javax.xml.namespace.QName;

public class JndiNameDocumentImpl extends XmlComplexContentImpl implements JndiNameDocument {
   private static final long serialVersionUID = 1L;
   private static final QName JNDINAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "jndi-name");

   public JndiNameDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public JndiNameType getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(JNDINAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJndiName(JndiNameType jndiName) {
      this.generatedSetterHelperImpl(jndiName, JNDINAME$0, 0, (short)1);
   }

   public JndiNameType addNewJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(JNDINAME$0);
         return target;
      }
   }
}
