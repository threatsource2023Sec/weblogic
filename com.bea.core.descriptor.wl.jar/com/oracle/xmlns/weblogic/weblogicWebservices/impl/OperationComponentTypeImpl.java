package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebservices.OperationComponentType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WsatConfigType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class OperationComponentTypeImpl extends XmlComplexContentImpl implements OperationComponentType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "name");
   private static final QName WSATCONFIG$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "wsat-config");

   public OperationComponentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(String name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public String addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public WsatConfigType getWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsatConfigType target = null;
         target = (WsatConfigType)this.get_store().find_element_user(WSATCONFIG$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSATCONFIG$2) != 0;
      }
   }

   public void setWsatConfig(WsatConfigType wsatConfig) {
      this.generatedSetterHelperImpl(wsatConfig, WSATCONFIG$2, 0, (short)1);
   }

   public WsatConfigType addNewWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsatConfigType target = null;
         target = (WsatConfigType)this.get_store().add_element_user(WSATCONFIG$2);
         return target;
      }
   }

   public void unsetWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSATCONFIG$2, 0);
      }
   }
}
