package com.oracle.xmlns.weblogic.jdbcDataSource.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.jdbcDataSource.ConnectionPoolParamsType;
import com.oracle.xmlns.weblogic.jdbcDataSource.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import com.sun.java.xml.ns.javaee.XsdPositiveIntegerType;
import javax.xml.namespace.QName;

public class ConnectionPoolParamsTypeImpl extends XmlComplexContentImpl implements ConnectionPoolParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName INITIALCAPACITY$0 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "initial-capacity");
   private static final QName MAXCAPACITY$2 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "max-capacity");
   private static final QName CAPACITYINCREMENT$4 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "capacity-increment");
   private static final QName SHRINKINGENABLED$6 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "shrinking-enabled");
   private static final QName SHRINKFREQUENCYSECONDS$8 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "shrink-frequency-seconds");
   private static final QName HIGHESTNUMWAITERS$10 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "highest-num-waiters");
   private static final QName HIGHESTNUMUNAVAILABLE$12 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "highest-num-unavailable");
   private static final QName CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "connection-creation-retry-frequency-seconds");
   private static final QName CONNECTIONRESERVETIMEOUTSECONDS$16 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "connection-reserve-timeout-seconds");
   private static final QName TESTFREQUENCYSECONDS$18 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "test-frequency-seconds");
   private static final QName TESTCONNECTIONSONCREATE$20 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "test-connections-on-create");
   private static final QName TESTCONNECTIONSONRELEASE$22 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "test-connections-on-release");
   private static final QName TESTCONNECTIONSONRESERVE$24 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "test-connections-on-reserve");
   private static final QName PROFILEHARVESTFREQUENCYSECONDS$26 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "profile-harvest-frequency-seconds");
   private static final QName IGNOREINUSECONNECTIONSENABLED$28 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "ignore-in-use-connections-enabled");
   private static final QName ID$30 = new QName("", "id");

   public ConnectionPoolParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALCAPACITY$0) != 0;
      }
   }

   public void setInitialCapacity(XsdNonNegativeIntegerType initialCapacity) {
      this.generatedSetterHelperImpl(initialCapacity, INITIALCAPACITY$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(INITIALCAPACITY$0);
         return target;
      }
   }

   public void unsetInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALCAPACITY$0, 0);
      }
   }

   public XsdNonNegativeIntegerType getMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCAPACITY$2) != 0;
      }
   }

   public void setMaxCapacity(XsdNonNegativeIntegerType maxCapacity) {
      this.generatedSetterHelperImpl(maxCapacity, MAXCAPACITY$2, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXCAPACITY$2);
         return target;
      }
   }

   public void unsetMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCAPACITY$2, 0);
      }
   }

   public XsdPositiveIntegerType getCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CAPACITYINCREMENT$4) != 0;
      }
   }

   public void setCapacityIncrement(XsdPositiveIntegerType capacityIncrement) {
      this.generatedSetterHelperImpl(capacityIncrement, CAPACITYINCREMENT$4, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(CAPACITYINCREMENT$4);
         return target;
      }
   }

   public void unsetCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CAPACITYINCREMENT$4, 0);
      }
   }

   public TrueFalseType getShrinkingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SHRINKINGENABLED$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetShrinkingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHRINKINGENABLED$6) != 0;
      }
   }

   public void setShrinkingEnabled(TrueFalseType shrinkingEnabled) {
      this.generatedSetterHelperImpl(shrinkingEnabled, SHRINKINGENABLED$6, 0, (short)1);
   }

   public TrueFalseType addNewShrinkingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SHRINKINGENABLED$6);
         return target;
      }
   }

   public void unsetShrinkingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHRINKINGENABLED$6, 0);
      }
   }

   public XsdNonNegativeIntegerType getShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHRINKFREQUENCYSECONDS$8) != 0;
      }
   }

   public void setShrinkFrequencySeconds(XsdNonNegativeIntegerType shrinkFrequencySeconds) {
      this.generatedSetterHelperImpl(shrinkFrequencySeconds, SHRINKFREQUENCYSECONDS$8, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(SHRINKFREQUENCYSECONDS$8);
         return target;
      }
   }

   public void unsetShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHRINKFREQUENCYSECONDS$8, 0);
      }
   }

   public XsdNonNegativeIntegerType getHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(HIGHESTNUMWAITERS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HIGHESTNUMWAITERS$10) != 0;
      }
   }

   public void setHighestNumWaiters(XsdNonNegativeIntegerType highestNumWaiters) {
      this.generatedSetterHelperImpl(highestNumWaiters, HIGHESTNUMWAITERS$10, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(HIGHESTNUMWAITERS$10);
         return target;
      }
   }

   public void unsetHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HIGHESTNUMWAITERS$10, 0);
      }
   }

   public XsdNonNegativeIntegerType getHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HIGHESTNUMUNAVAILABLE$12) != 0;
      }
   }

   public void setHighestNumUnavailable(XsdNonNegativeIntegerType highestNumUnavailable) {
      this.generatedSetterHelperImpl(highestNumUnavailable, HIGHESTNUMUNAVAILABLE$12, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(HIGHESTNUMUNAVAILABLE$12);
         return target;
      }
   }

   public void unsetHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HIGHESTNUMUNAVAILABLE$12, 0);
      }
   }

   public XsdIntegerType getConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14) != 0;
      }
   }

   public void setConnectionCreationRetryFrequencySeconds(XsdIntegerType connectionCreationRetryFrequencySeconds) {
      this.generatedSetterHelperImpl(connectionCreationRetryFrequencySeconds, CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14, 0, (short)1);
   }

   public XsdIntegerType addNewConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14);
         return target;
      }
   }

   public void unsetConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14, 0);
      }
   }

   public XsdIntegerType getConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONRESERVETIMEOUTSECONDS$16) != 0;
      }
   }

   public void setConnectionReserveTimeoutSeconds(XsdIntegerType connectionReserveTimeoutSeconds) {
      this.generatedSetterHelperImpl(connectionReserveTimeoutSeconds, CONNECTIONRESERVETIMEOUTSECONDS$16, 0, (short)1);
   }

   public XsdIntegerType addNewConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16);
         return target;
      }
   }

   public void unsetConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONRESERVETIMEOUTSECONDS$16, 0);
      }
   }

   public XsdNonNegativeIntegerType getTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(TESTFREQUENCYSECONDS$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TESTFREQUENCYSECONDS$18) != 0;
      }
   }

   public void setTestFrequencySeconds(XsdNonNegativeIntegerType testFrequencySeconds) {
      this.generatedSetterHelperImpl(testFrequencySeconds, TESTFREQUENCYSECONDS$18, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(TESTFREQUENCYSECONDS$18);
         return target;
      }
   }

   public void unsetTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TESTFREQUENCYSECONDS$18, 0);
      }
   }

   public TrueFalseType getTestConnectionsOnCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(TESTCONNECTIONSONCREATE$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTestConnectionsOnCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TESTCONNECTIONSONCREATE$20) != 0;
      }
   }

   public void setTestConnectionsOnCreate(TrueFalseType testConnectionsOnCreate) {
      this.generatedSetterHelperImpl(testConnectionsOnCreate, TESTCONNECTIONSONCREATE$20, 0, (short)1);
   }

   public TrueFalseType addNewTestConnectionsOnCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(TESTCONNECTIONSONCREATE$20);
         return target;
      }
   }

   public void unsetTestConnectionsOnCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TESTCONNECTIONSONCREATE$20, 0);
      }
   }

   public TrueFalseType getTestConnectionsOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(TESTCONNECTIONSONRELEASE$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTestConnectionsOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TESTCONNECTIONSONRELEASE$22) != 0;
      }
   }

   public void setTestConnectionsOnRelease(TrueFalseType testConnectionsOnRelease) {
      this.generatedSetterHelperImpl(testConnectionsOnRelease, TESTCONNECTIONSONRELEASE$22, 0, (short)1);
   }

   public TrueFalseType addNewTestConnectionsOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(TESTCONNECTIONSONRELEASE$22);
         return target;
      }
   }

   public void unsetTestConnectionsOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TESTCONNECTIONSONRELEASE$22, 0);
      }
   }

   public TrueFalseType getTestConnectionsOnReserve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(TESTCONNECTIONSONRESERVE$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTestConnectionsOnReserve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TESTCONNECTIONSONRESERVE$24) != 0;
      }
   }

   public void setTestConnectionsOnReserve(TrueFalseType testConnectionsOnReserve) {
      this.generatedSetterHelperImpl(testConnectionsOnReserve, TESTCONNECTIONSONRESERVE$24, 0, (short)1);
   }

   public TrueFalseType addNewTestConnectionsOnReserve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(TESTCONNECTIONSONRESERVE$24);
         return target;
      }
   }

   public void unsetTestConnectionsOnReserve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TESTCONNECTIONSONRESERVE$24, 0);
      }
   }

   public XsdNonNegativeIntegerType getProfileHarvestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(PROFILEHARVESTFREQUENCYSECONDS$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProfileHarvestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROFILEHARVESTFREQUENCYSECONDS$26) != 0;
      }
   }

   public void setProfileHarvestFrequencySeconds(XsdNonNegativeIntegerType profileHarvestFrequencySeconds) {
      this.generatedSetterHelperImpl(profileHarvestFrequencySeconds, PROFILEHARVESTFREQUENCYSECONDS$26, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewProfileHarvestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(PROFILEHARVESTFREQUENCYSECONDS$26);
         return target;
      }
   }

   public void unsetProfileHarvestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROFILEHARVESTFREQUENCYSECONDS$26, 0);
      }
   }

   public TrueFalseType getIgnoreInUseConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(IGNOREINUSECONNECTIONSENABLED$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIgnoreInUseConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IGNOREINUSECONNECTIONSENABLED$28) != 0;
      }
   }

   public void setIgnoreInUseConnectionsEnabled(TrueFalseType ignoreInUseConnectionsEnabled) {
      this.generatedSetterHelperImpl(ignoreInUseConnectionsEnabled, IGNOREINUSECONNECTIONSENABLED$28, 0, (short)1);
   }

   public TrueFalseType addNewIgnoreInUseConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(IGNOREINUSECONNECTIONSENABLED$28);
         return target;
      }
   }

   public void unsetIgnoreInUseConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IGNOREINUSECONNECTIONSENABLED$28, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$30);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$30);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$30) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$30);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$30);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$30);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$30);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$30);
      }
   }
}
