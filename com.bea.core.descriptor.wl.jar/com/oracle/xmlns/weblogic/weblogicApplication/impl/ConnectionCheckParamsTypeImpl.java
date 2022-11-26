package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionCheckParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import javax.xml.namespace.QName;

public class ConnectionCheckParamsTypeImpl extends XmlComplexContentImpl implements ConnectionCheckParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName TABLENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "table-name");
   private static final QName CHECKONRESERVEENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "check-on-reserve-enabled");
   private static final QName CHECKONRELEASEENABLED$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "check-on-release-enabled");
   private static final QName REFRESHMINUTES$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "refresh-minutes");
   private static final QName CHECKONCREATEENABLED$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "check-on-create-enabled");
   private static final QName CONNECTIONRESERVETIMEOUTSECONDS$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "connection-reserve-timeout-seconds");
   private static final QName CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "connection-creation-retry-frequency-seconds");
   private static final QName INACTIVECONNECTIONTIMEOUTSECONDS$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "inactive-connection-timeout-seconds");
   private static final QName TESTFREQUENCYSECONDS$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "test-frequency-seconds");
   private static final QName INITSQL$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "init-sql");

   public ConnectionCheckParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$0, 0);
         return target;
      }
   }

   public boolean isSetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLENAME$0) != 0;
      }
   }

   public void setTableName(String tableName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLENAME$0);
         }

         target.setStringValue(tableName);
      }
   }

   public void xsetTableName(XmlString tableName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLENAME$0);
         }

         target.set(tableName);
      }
   }

   public void unsetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLENAME$0, 0);
      }
   }

   public TrueFalseType getCheckOnReserveEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CHECKONRESERVEENABLED$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCheckOnReserveEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHECKONRESERVEENABLED$2) != 0;
      }
   }

   public void setCheckOnReserveEnabled(TrueFalseType checkOnReserveEnabled) {
      this.generatedSetterHelperImpl(checkOnReserveEnabled, CHECKONRESERVEENABLED$2, 0, (short)1);
   }

   public TrueFalseType addNewCheckOnReserveEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CHECKONRESERVEENABLED$2);
         return target;
      }
   }

   public void unsetCheckOnReserveEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHECKONRESERVEENABLED$2, 0);
      }
   }

   public TrueFalseType getCheckOnReleaseEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CHECKONRELEASEENABLED$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCheckOnReleaseEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHECKONRELEASEENABLED$4) != 0;
      }
   }

   public void setCheckOnReleaseEnabled(TrueFalseType checkOnReleaseEnabled) {
      this.generatedSetterHelperImpl(checkOnReleaseEnabled, CHECKONRELEASEENABLED$4, 0, (short)1);
   }

   public TrueFalseType addNewCheckOnReleaseEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CHECKONRELEASEENABLED$4);
         return target;
      }
   }

   public void unsetCheckOnReleaseEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHECKONRELEASEENABLED$4, 0);
      }
   }

   public int getRefreshMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REFRESHMINUTES$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetRefreshMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(REFRESHMINUTES$6, 0);
         return target;
      }
   }

   public boolean isSetRefreshMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REFRESHMINUTES$6) != 0;
      }
   }

   public void setRefreshMinutes(int refreshMinutes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REFRESHMINUTES$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REFRESHMINUTES$6);
         }

         target.setIntValue(refreshMinutes);
      }
   }

   public void xsetRefreshMinutes(XmlInt refreshMinutes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(REFRESHMINUTES$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(REFRESHMINUTES$6);
         }

         target.set(refreshMinutes);
      }
   }

   public void unsetRefreshMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REFRESHMINUTES$6, 0);
      }
   }

   public TrueFalseType getCheckOnCreateEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CHECKONCREATEENABLED$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCheckOnCreateEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHECKONCREATEENABLED$8) != 0;
      }
   }

   public void setCheckOnCreateEnabled(TrueFalseType checkOnCreateEnabled) {
      this.generatedSetterHelperImpl(checkOnCreateEnabled, CHECKONCREATEENABLED$8, 0, (short)1);
   }

   public TrueFalseType addNewCheckOnCreateEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CHECKONCREATEENABLED$8);
         return target;
      }
   }

   public void unsetCheckOnCreateEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHECKONCREATEENABLED$8, 0);
      }
   }

   public int getConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$10, 0);
         return target;
      }
   }

   public boolean isSetConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONRESERVETIMEOUTSECONDS$10) != 0;
      }
   }

   public void setConnectionReserveTimeoutSeconds(int connectionReserveTimeoutSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONRESERVETIMEOUTSECONDS$10);
         }

         target.setIntValue(connectionReserveTimeoutSeconds);
      }
   }

   public void xsetConnectionReserveTimeoutSeconds(XmlInt connectionReserveTimeoutSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CONNECTIONRESERVETIMEOUTSECONDS$10);
         }

         target.set(connectionReserveTimeoutSeconds);
      }
   }

   public void unsetConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONRESERVETIMEOUTSECONDS$10, 0);
      }
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12, 0);
         return target;
      }
   }

   public boolean isSetConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12) != 0;
      }
   }

   public void setConnectionCreationRetryFrequencySeconds(int connectionCreationRetryFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12);
         }

         target.setIntValue(connectionCreationRetryFrequencySeconds);
      }
   }

   public void xsetConnectionCreationRetryFrequencySeconds(XmlInt connectionCreationRetryFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12);
         }

         target.set(connectionCreationRetryFrequencySeconds);
      }
   }

   public void unsetConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$12, 0);
      }
   }

   public int getInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$14, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$14, 0);
         return target;
      }
   }

   public boolean isSetInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INACTIVECONNECTIONTIMEOUTSECONDS$14) != 0;
      }
   }

   public void setInactiveConnectionTimeoutSeconds(int inactiveConnectionTimeoutSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$14);
         }

         target.setIntValue(inactiveConnectionTimeoutSeconds);
      }
   }

   public void xsetInactiveConnectionTimeoutSeconds(XmlInt inactiveConnectionTimeoutSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$14, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$14);
         }

         target.set(inactiveConnectionTimeoutSeconds);
      }
   }

   public void unsetInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INACTIVECONNECTIONTIMEOUTSECONDS$14, 0);
      }
   }

   public int getTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTFREQUENCYSECONDS$16, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(TESTFREQUENCYSECONDS$16, 0);
         return target;
      }
   }

   public boolean isSetTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TESTFREQUENCYSECONDS$16) != 0;
      }
   }

   public void setTestFrequencySeconds(int testFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTFREQUENCYSECONDS$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TESTFREQUENCYSECONDS$16);
         }

         target.setIntValue(testFrequencySeconds);
      }
   }

   public void xsetTestFrequencySeconds(XmlInt testFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(TESTFREQUENCYSECONDS$16, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(TESTFREQUENCYSECONDS$16);
         }

         target.set(testFrequencySeconds);
      }
   }

   public void unsetTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TESTFREQUENCYSECONDS$16, 0);
      }
   }

   public String getInitSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITSQL$18, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetInitSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITSQL$18, 0);
         return target;
      }
   }

   public boolean isSetInitSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITSQL$18) != 0;
      }
   }

   public void setInitSql(String initSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITSQL$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INITSQL$18);
         }

         target.setStringValue(initSql);
      }
   }

   public void xsetInitSql(XmlString initSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITSQL$18, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INITSQL$18);
         }

         target.set(initSql);
      }
   }

   public void unsetInitSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITSQL$18, 0);
      }
   }
}
