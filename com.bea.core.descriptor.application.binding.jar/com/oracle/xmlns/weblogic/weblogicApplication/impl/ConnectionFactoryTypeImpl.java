package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionFactoryType;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionPropertiesType;
import javax.xml.namespace.QName;

public class ConnectionFactoryTypeImpl extends XmlComplexContentImpl implements ConnectionFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName FACTORYNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "factory-name");
   private static final QName CONNECTIONPROPERTIES$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "connection-properties");

   public ConnectionFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FACTORYNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FACTORYNAME$0, 0);
         return target;
      }
   }

   public boolean isSetFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FACTORYNAME$0) != 0;
      }
   }

   public void setFactoryName(String factoryName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FACTORYNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FACTORYNAME$0);
         }

         target.setStringValue(factoryName);
      }
   }

   public void xsetFactoryName(XmlString factoryName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FACTORYNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FACTORYNAME$0);
         }

         target.set(factoryName);
      }
   }

   public void unsetFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FACTORYNAME$0, 0);
      }
   }

   public ConnectionPropertiesType getConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionPropertiesType target = null;
         target = (ConnectionPropertiesType)this.get_store().find_element_user(CONNECTIONPROPERTIES$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONPROPERTIES$2) != 0;
      }
   }

   public void setConnectionProperties(ConnectionPropertiesType connectionProperties) {
      this.generatedSetterHelperImpl(connectionProperties, CONNECTIONPROPERTIES$2, 0, (short)1);
   }

   public ConnectionPropertiesType addNewConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionPropertiesType target = null;
         target = (ConnectionPropertiesType)this.get_store().add_element_user(CONNECTIONPROPERTIES$2);
         return target;
      }
   }

   public void unsetConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONPROPERTIES$2, 0);
      }
   }
}
