package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SimpleDriverDataSourceType;
import javax.xml.namespace.QName;

public class SimpleDriverDataSourceTypeImpl extends DriverDataSourceTypeImpl implements SimpleDriverDataSourceType {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONUSERNAME$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-user-name");
   private static final QName LOGINTIMEOUT$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "login-timeout");
   private static final QName CONNECTIONPASSWORD$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-password");
   private static final QName CONNECTIONURL$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-url");
   private static final QName CONNECTIONDRIVERNAME$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-driver-name");

   public SimpleDriverDataSourceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONUSERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$0, 0);
         return target;
      }
   }

   public boolean isNilConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONUSERNAME$0) != 0;
      }
   }

   public void setConnectionUserName(String connectionUserName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONUSERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONUSERNAME$0);
         }

         target.setStringValue(connectionUserName);
      }
   }

   public void xsetConnectionUserName(XmlString connectionUserName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONUSERNAME$0);
         }

         target.set(connectionUserName);
      }
   }

   public void setNilConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONUSERNAME$0);
         }

         target.setNil();
      }
   }

   public void unsetConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONUSERNAME$0, 0);
      }
   }

   public int getLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGINTIMEOUT$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOGINTIMEOUT$2, 0);
         return target;
      }
   }

   public boolean isSetLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGINTIMEOUT$2) != 0;
      }
   }

   public void setLoginTimeout(int loginTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGINTIMEOUT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGINTIMEOUT$2);
         }

         target.setIntValue(loginTimeout);
      }
   }

   public void xsetLoginTimeout(XmlInt loginTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOGINTIMEOUT$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(LOGINTIMEOUT$2);
         }

         target.set(loginTimeout);
      }
   }

   public void unsetLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGINTIMEOUT$2, 0);
      }
   }

   public String getConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONPASSWORD$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$4, 0);
         return target;
      }
   }

   public boolean isNilConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONPASSWORD$4) != 0;
      }
   }

   public void setConnectionPassword(String connectionPassword) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONPASSWORD$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONPASSWORD$4);
         }

         target.setStringValue(connectionPassword);
      }
   }

   public void xsetConnectionPassword(XmlString connectionPassword) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONPASSWORD$4);
         }

         target.set(connectionPassword);
      }
   }

   public void setNilConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONPASSWORD$4);
         }

         target.setNil();
      }
   }

   public void unsetConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONPASSWORD$4, 0);
      }
   }

   public String getConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         return target;
      }
   }

   public boolean isNilConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONURL$6) != 0;
      }
   }

   public void setConnectionUrl(String connectionUrl) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONURL$6);
         }

         target.setStringValue(connectionUrl);
      }
   }

   public void xsetConnectionUrl(XmlString connectionUrl) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONURL$6);
         }

         target.set(connectionUrl);
      }
   }

   public void setNilConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONURL$6);
         }

         target.setNil();
      }
   }

   public void unsetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONURL$6, 0);
      }
   }

   public String getConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONDRIVERNAME$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$8, 0);
         return target;
      }
   }

   public boolean isNilConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONDRIVERNAME$8) != 0;
      }
   }

   public void setConnectionDriverName(String connectionDriverName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONDRIVERNAME$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONDRIVERNAME$8);
         }

         target.setStringValue(connectionDriverName);
      }
   }

   public void xsetConnectionDriverName(XmlString connectionDriverName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONDRIVERNAME$8);
         }

         target.set(connectionDriverName);
      }
   }

   public void setNilConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONDRIVERNAME$8);
         }

         target.setNil();
      }
   }

   public void unsetConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONDRIVERNAME$8, 0);
      }
   }
}
