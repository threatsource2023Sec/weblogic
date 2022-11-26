package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.LinkRefType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class LinkRefTypeImpl extends XmlComplexContentImpl implements LinkRefType {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONFACTORYNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connection-factory-name");
   private static final QName RALINKREF$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "ra-link-ref");

   public LinkRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(CONNECTIONFACTORYNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionFactoryName(String connectionFactoryName) {
      this.generatedSetterHelperImpl(connectionFactoryName, CONNECTIONFACTORYNAME$0, 0, (short)1);
   }

   public String addNewConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(CONNECTIONFACTORYNAME$0);
         return target;
      }
   }

   public String getRaLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RALINKREF$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRaLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RALINKREF$2) != 0;
      }
   }

   public void setRaLinkRef(String raLinkRef) {
      this.generatedSetterHelperImpl(raLinkRef, RALINKREF$2, 0, (short)1);
   }

   public String addNewRaLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RALINKREF$2);
         return target;
      }
   }

   public void unsetRaLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RALINKREF$2, 0);
      }
   }
}
