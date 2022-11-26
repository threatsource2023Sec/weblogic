package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionPropertiesType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectionPropertiesTypeImpl extends XmlComplexContentImpl implements ConnectionPropertiesType {
   private static final long serialVersionUID = 1L;
   private static final QName USERNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "user-name");
   private static final QName PASSWORD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "password");
   private static final QName URL$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "url");
   private static final QName DRIVERCLASSNAME$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "driver-class-name");
   private static final QName CONNECTIONPARAMS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "connection-params");

   public ConnectionPropertiesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         return target;
      }
   }

   public boolean isSetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USERNAME$0) != 0;
      }
   }

   public void setUserName(String userName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USERNAME$0);
         }

         target.setStringValue(userName);
      }
   }

   public void xsetUserName(XmlString userName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(USERNAME$0);
         }

         target.set(userName);
      }
   }

   public void unsetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USERNAME$0, 0);
      }
   }

   public String getPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORD$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORD$2, 0);
         return target;
      }
   }

   public boolean isSetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PASSWORD$2) != 0;
      }
   }

   public void setPassword(String password) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORD$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PASSWORD$2);
         }

         target.setStringValue(password);
      }
   }

   public void xsetPassword(XmlString password) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORD$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PASSWORD$2);
         }

         target.set(password);
      }
   }

   public void unsetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PASSWORD$2, 0);
      }
   }

   public String getUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URL$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URL$4, 0);
         return target;
      }
   }

   public boolean isSetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URL$4) != 0;
      }
   }

   public void setUrl(String url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URL$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(URL$4);
         }

         target.setStringValue(url);
      }
   }

   public void xsetUrl(XmlString url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URL$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(URL$4);
         }

         target.set(url);
      }
   }

   public void unsetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URL$4, 0);
      }
   }

   public String getDriverClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DRIVERCLASSNAME$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDriverClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DRIVERCLASSNAME$6, 0);
         return target;
      }
   }

   public boolean isSetDriverClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DRIVERCLASSNAME$6) != 0;
      }
   }

   public void setDriverClassName(String driverClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DRIVERCLASSNAME$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DRIVERCLASSNAME$6);
         }

         target.setStringValue(driverClassName);
      }
   }

   public void xsetDriverClassName(XmlString driverClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DRIVERCLASSNAME$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DRIVERCLASSNAME$6);
         }

         target.set(driverClassName);
      }
   }

   public void unsetDriverClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DRIVERCLASSNAME$6, 0);
      }
   }

   public ConnectionParamsType[] getConnectionParamsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONPARAMS$8, targetList);
         ConnectionParamsType[] result = new ConnectionParamsType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionParamsType getConnectionParamsArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionParamsType target = null;
         target = (ConnectionParamsType)this.get_store().find_element_user(CONNECTIONPARAMS$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConnectionParamsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONPARAMS$8);
      }
   }

   public void setConnectionParamsArray(ConnectionParamsType[] connectionParamsArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionParamsArray, CONNECTIONPARAMS$8);
   }

   public void setConnectionParamsArray(int i, ConnectionParamsType connectionParams) {
      this.generatedSetterHelperImpl(connectionParams, CONNECTIONPARAMS$8, i, (short)2);
   }

   public ConnectionParamsType insertNewConnectionParams(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionParamsType target = null;
         target = (ConnectionParamsType)this.get_store().insert_element_user(CONNECTIONPARAMS$8, i);
         return target;
      }
   }

   public ConnectionParamsType addNewConnectionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionParamsType target = null;
         target = (ConnectionParamsType)this.get_store().add_element_user(CONNECTIONPARAMS$8);
         return target;
      }
   }

   public void removeConnectionParams(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONPARAMS$8, i);
      }
   }
}
