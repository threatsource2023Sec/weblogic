package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TcpTransportType;
import javax.xml.namespace.QName;

public class TcpTransportTypeImpl extends PersistenceServerTypeImpl implements TcpTransportType {
   private static final long serialVersionUID = 1L;
   private static final QName SOTIMEOUT$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "so-timeout");
   private static final QName HOST$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "host");
   private static final QName PORT$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "port");

   public TcpTransportTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getSoTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOTIMEOUT$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetSoTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SOTIMEOUT$0, 0);
         return target;
      }
   }

   public boolean isSetSoTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SOTIMEOUT$0) != 0;
      }
   }

   public void setSoTimeout(int soTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOTIMEOUT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SOTIMEOUT$0);
         }

         target.setIntValue(soTimeout);
      }
   }

   public void xsetSoTimeout(XmlInt soTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SOTIMEOUT$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(SOTIMEOUT$0);
         }

         target.set(soTimeout);
      }
   }

   public void unsetSoTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SOTIMEOUT$0, 0);
      }
   }

   public String getHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HOST$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HOST$2, 0);
         return target;
      }
   }

   public boolean isNilHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HOST$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOST$2) != 0;
      }
   }

   public void setHost(String host) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HOST$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HOST$2);
         }

         target.setStringValue(host);
      }
   }

   public void xsetHost(XmlString host) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HOST$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(HOST$2);
         }

         target.set(host);
      }
   }

   public void setNilHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HOST$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(HOST$2);
         }

         target.setNil();
      }
   }

   public void unsetHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOST$2, 0);
      }
   }

   public int getPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORT$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PORT$4, 0);
         return target;
      }
   }

   public boolean isSetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORT$4) != 0;
      }
   }

   public void setPort(int port) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORT$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PORT$4);
         }

         target.setIntValue(port);
      }
   }

   public void xsetPort(XmlInt port) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PORT$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(PORT$4);
         }

         target.set(port);
      }
   }

   public void unsetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORT$4, 0);
      }
   }
}
