package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationPoolParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionFactoryType;
import com.oracle.xmlns.weblogic.weblogicApplication.DriverParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.JdbcConnectionPoolType;
import javax.xml.namespace.QName;

public class JdbcConnectionPoolTypeImpl extends XmlComplexContentImpl implements JdbcConnectionPoolType {
   private static final long serialVersionUID = 1L;
   private static final QName DATASOURCEJNDINAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "data-source-jndi-name");
   private static final QName CONNECTIONFACTORY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "connection-factory");
   private static final QName POOLPARAMS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "pool-params");
   private static final QName DRIVERPARAMS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "driver-params");
   private static final QName ACLNAME$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "acl-name");

   public JdbcConnectionPoolTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATASOURCEJNDINAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATASOURCEJNDINAME$0, 0);
         return target;
      }
   }

   public void setDataSourceJndiName(String dataSourceJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATASOURCEJNDINAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DATASOURCEJNDINAME$0);
         }

         target.setStringValue(dataSourceJndiName);
      }
   }

   public void xsetDataSourceJndiName(XmlString dataSourceJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATASOURCEJNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DATASOURCEJNDINAME$0);
         }

         target.set(dataSourceJndiName);
      }
   }

   public ConnectionFactoryType getConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryType target = null;
         target = (ConnectionFactoryType)this.get_store().find_element_user(CONNECTIONFACTORY$2, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionFactory(ConnectionFactoryType connectionFactory) {
      this.generatedSetterHelperImpl(connectionFactory, CONNECTIONFACTORY$2, 0, (short)1);
   }

   public ConnectionFactoryType addNewConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryType target = null;
         target = (ConnectionFactoryType)this.get_store().add_element_user(CONNECTIONFACTORY$2);
         return target;
      }
   }

   public ApplicationPoolParamsType getPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationPoolParamsType target = null;
         target = (ApplicationPoolParamsType)this.get_store().find_element_user(POOLPARAMS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOLPARAMS$4) != 0;
      }
   }

   public void setPoolParams(ApplicationPoolParamsType poolParams) {
      this.generatedSetterHelperImpl(poolParams, POOLPARAMS$4, 0, (short)1);
   }

   public ApplicationPoolParamsType addNewPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationPoolParamsType target = null;
         target = (ApplicationPoolParamsType)this.get_store().add_element_user(POOLPARAMS$4);
         return target;
      }
   }

   public void unsetPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOLPARAMS$4, 0);
      }
   }

   public DriverParamsType getDriverParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DriverParamsType target = null;
         target = (DriverParamsType)this.get_store().find_element_user(DRIVERPARAMS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDriverParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DRIVERPARAMS$6) != 0;
      }
   }

   public void setDriverParams(DriverParamsType driverParams) {
      this.generatedSetterHelperImpl(driverParams, DRIVERPARAMS$6, 0, (short)1);
   }

   public DriverParamsType addNewDriverParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DriverParamsType target = null;
         target = (DriverParamsType)this.get_store().add_element_user(DRIVERPARAMS$6);
         return target;
      }
   }

   public void unsetDriverParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DRIVERPARAMS$6, 0);
      }
   }

   public String getAclName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACLNAME$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAclName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACLNAME$8, 0);
         return target;
      }
   }

   public boolean isSetAclName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACLNAME$8) != 0;
      }
   }

   public void setAclName(String aclName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACLNAME$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ACLNAME$8);
         }

         target.setStringValue(aclName);
      }
   }

   public void xsetAclName(XmlString aclName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACLNAME$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ACLNAME$8);
         }

         target.set(aclName);
      }
   }

   public void unsetAclName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACLNAME$8, 0);
      }
   }
}
