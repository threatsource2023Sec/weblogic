package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectionPoolParamsType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInteger;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class ConnectionPoolParamsTypeImpl extends XmlComplexContentImpl implements ConnectionPoolParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName INITIALCAPACITY$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "initial-capacity");
   private static final QName MAXCAPACITY$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "max-capacity");
   private static final QName CAPACITYINCREMENT$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "capacity-increment");
   private static final QName SHRINKINGENABLED$6 = new QName("http://www.bea.com/connector/monitoring1dot0", "shrinking-enabled");
   private static final QName SHRINKFREQUENCYSECONDS$8 = new QName("http://www.bea.com/connector/monitoring1dot0", "shrink-frequency-seconds");
   private static final QName HIGHESTNUMWAITERS$10 = new QName("http://www.bea.com/connector/monitoring1dot0", "highest-num-waiters");
   private static final QName HIGHESTNUMUNAVAILABLE$12 = new QName("http://www.bea.com/connector/monitoring1dot0", "highest-num-unavailable");
   private static final QName CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-creation-retry-frequency-seconds");
   private static final QName CONNECTIONRESERVETIMEOUTSECONDS$16 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-reserve-timeout-seconds");
   private static final QName TESTFREQUENCYSECONDS$18 = new QName("http://www.bea.com/connector/monitoring1dot0", "test-frequency-seconds");
   private static final QName TESTCONNECTIONSONCREATE$20 = new QName("http://www.bea.com/connector/monitoring1dot0", "test-connections-on-create");
   private static final QName TESTCONNECTIONSONRELEASE$22 = new QName("http://www.bea.com/connector/monitoring1dot0", "test-connections-on-release");
   private static final QName TESTCONNECTIONSONRESERVE$24 = new QName("http://www.bea.com/connector/monitoring1dot0", "test-connections-on-reserve");
   private static final QName PROFILEHARVESTFREQUENCYSECONDS$26 = new QName("http://www.bea.com/connector/monitoring1dot0", "profile-harvest-frequency-seconds");
   private static final QName IGNOREINUSECONNECTIONSENABLED$28 = new QName("http://www.bea.com/connector/monitoring1dot0", "ignore-in-use-connections-enabled");
   private static final QName MATCHCONNECTIONSSUPPORTED$30 = new QName("http://www.bea.com/connector/monitoring1dot0", "match-connections-supported");
   private static final QName USEFIRSTAVAILABLE$32 = new QName("http://www.bea.com/connector/monitoring1dot0", "use-first-available");

   public ConnectionPoolParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public BigInteger getInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         return target;
      }
   }

   public void setInitialCapacity(BigInteger initialCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INITIALCAPACITY$0);
         }

         target.setBigIntegerValue(initialCapacity);
      }
   }

   public void xsetInitialCapacity(XmlInteger initialCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(INITIALCAPACITY$0);
         }

         target.set(initialCapacity);
      }
   }

   public BigInteger getMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         return target;
      }
   }

   public void setMaxCapacity(BigInteger maxCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXCAPACITY$2);
         }

         target.setBigIntegerValue(maxCapacity);
      }
   }

   public void xsetMaxCapacity(XmlInteger maxCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(MAXCAPACITY$2);
         }

         target.set(maxCapacity);
      }
   }

   public BigInteger getCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         return target;
      }
   }

   public void setCapacityIncrement(BigInteger capacityIncrement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CAPACITYINCREMENT$4);
         }

         target.setBigIntegerValue(capacityIncrement);
      }
   }

   public void xsetCapacityIncrement(XmlInteger capacityIncrement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(CAPACITYINCREMENT$4);
         }

         target.set(capacityIncrement);
      }
   }

   public boolean getShrinkingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKINGENABLED$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetShrinkingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SHRINKINGENABLED$6, 0);
         return target;
      }
   }

   public void setShrinkingEnabled(boolean shrinkingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKINGENABLED$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHRINKINGENABLED$6);
         }

         target.setBooleanValue(shrinkingEnabled);
      }
   }

   public void xsetShrinkingEnabled(XmlBoolean shrinkingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SHRINKINGENABLED$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SHRINKINGENABLED$6);
         }

         target.set(shrinkingEnabled);
      }
   }

   public BigInteger getShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$8, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$8, 0);
         return target;
      }
   }

   public void setShrinkFrequencySeconds(BigInteger shrinkFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHRINKFREQUENCYSECONDS$8);
         }

         target.setBigIntegerValue(shrinkFrequencySeconds);
      }
   }

   public void xsetShrinkFrequencySeconds(XmlInteger shrinkFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$8, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(SHRINKFREQUENCYSECONDS$8);
         }

         target.set(shrinkFrequencySeconds);
      }
   }

   public BigInteger getHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMWAITERS$10, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(HIGHESTNUMWAITERS$10, 0);
         return target;
      }
   }

   public void setHighestNumWaiters(BigInteger highestNumWaiters) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMWAITERS$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HIGHESTNUMWAITERS$10);
         }

         target.setBigIntegerValue(highestNumWaiters);
      }
   }

   public void xsetHighestNumWaiters(XmlInteger highestNumWaiters) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(HIGHESTNUMWAITERS$10, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(HIGHESTNUMWAITERS$10);
         }

         target.set(highestNumWaiters);
      }
   }

   public BigInteger getHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$12, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$12, 0);
         return target;
      }
   }

   public void setHighestNumUnavailable(BigInteger highestNumUnavailable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HIGHESTNUMUNAVAILABLE$12);
         }

         target.setBigIntegerValue(highestNumUnavailable);
      }
   }

   public void xsetHighestNumUnavailable(XmlInteger highestNumUnavailable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$12, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(HIGHESTNUMUNAVAILABLE$12);
         }

         target.set(highestNumUnavailable);
      }
   }

   public BigInteger getConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetConnectionCreationRetryFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14, 0);
         return target;
      }
   }

   public void setConnectionCreationRetryFrequencySeconds(BigInteger connectionCreationRetryFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14);
         }

         target.setBigIntegerValue(connectionCreationRetryFrequencySeconds);
      }
   }

   public void xsetConnectionCreationRetryFrequencySeconds(XmlInteger connectionCreationRetryFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(CONNECTIONCREATIONRETRYFREQUENCYSECONDS$14);
         }

         target.set(connectionCreationRetryFrequencySeconds);
      }
   }

   public BigInteger getConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetConnectionReserveTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16, 0);
         return target;
      }
   }

   public void setConnectionReserveTimeoutSeconds(BigInteger connectionReserveTimeoutSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16);
         }

         target.setBigIntegerValue(connectionReserveTimeoutSeconds);
      }
   }

   public void xsetConnectionReserveTimeoutSeconds(XmlInteger connectionReserveTimeoutSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(CONNECTIONRESERVETIMEOUTSECONDS$16);
         }

         target.set(connectionReserveTimeoutSeconds);
      }
   }

   public BigInteger getTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTFREQUENCYSECONDS$18, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetTestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(TESTFREQUENCYSECONDS$18, 0);
         return target;
      }
   }

   public void setTestFrequencySeconds(BigInteger testFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTFREQUENCYSECONDS$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TESTFREQUENCYSECONDS$18);
         }

         target.setBigIntegerValue(testFrequencySeconds);
      }
   }

   public void xsetTestFrequencySeconds(XmlInteger testFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(TESTFREQUENCYSECONDS$18, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(TESTFREQUENCYSECONDS$18);
         }

         target.set(testFrequencySeconds);
      }
   }

   public boolean getTestConnectionsOnCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTCONNECTIONSONCREATE$20, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetTestConnectionsOnCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TESTCONNECTIONSONCREATE$20, 0);
         return target;
      }
   }

   public void setTestConnectionsOnCreate(boolean testConnectionsOnCreate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTCONNECTIONSONCREATE$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TESTCONNECTIONSONCREATE$20);
         }

         target.setBooleanValue(testConnectionsOnCreate);
      }
   }

   public void xsetTestConnectionsOnCreate(XmlBoolean testConnectionsOnCreate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TESTCONNECTIONSONCREATE$20, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(TESTCONNECTIONSONCREATE$20);
         }

         target.set(testConnectionsOnCreate);
      }
   }

   public boolean getTestConnectionsOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTCONNECTIONSONRELEASE$22, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetTestConnectionsOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TESTCONNECTIONSONRELEASE$22, 0);
         return target;
      }
   }

   public void setTestConnectionsOnRelease(boolean testConnectionsOnRelease) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTCONNECTIONSONRELEASE$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TESTCONNECTIONSONRELEASE$22);
         }

         target.setBooleanValue(testConnectionsOnRelease);
      }
   }

   public void xsetTestConnectionsOnRelease(XmlBoolean testConnectionsOnRelease) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TESTCONNECTIONSONRELEASE$22, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(TESTCONNECTIONSONRELEASE$22);
         }

         target.set(testConnectionsOnRelease);
      }
   }

   public boolean getTestConnectionsOnReserve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTCONNECTIONSONRESERVE$24, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetTestConnectionsOnReserve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TESTCONNECTIONSONRESERVE$24, 0);
         return target;
      }
   }

   public void setTestConnectionsOnReserve(boolean testConnectionsOnReserve) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TESTCONNECTIONSONRESERVE$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TESTCONNECTIONSONRESERVE$24);
         }

         target.setBooleanValue(testConnectionsOnReserve);
      }
   }

   public void xsetTestConnectionsOnReserve(XmlBoolean testConnectionsOnReserve) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TESTCONNECTIONSONRESERVE$24, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(TESTCONNECTIONSONRESERVE$24);
         }

         target.set(testConnectionsOnReserve);
      }
   }

   public BigInteger getProfileHarvestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROFILEHARVESTFREQUENCYSECONDS$26, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetProfileHarvestFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(PROFILEHARVESTFREQUENCYSECONDS$26, 0);
         return target;
      }
   }

   public void setProfileHarvestFrequencySeconds(BigInteger profileHarvestFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROFILEHARVESTFREQUENCYSECONDS$26, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PROFILEHARVESTFREQUENCYSECONDS$26);
         }

         target.setBigIntegerValue(profileHarvestFrequencySeconds);
      }
   }

   public void xsetProfileHarvestFrequencySeconds(XmlInteger profileHarvestFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(PROFILEHARVESTFREQUENCYSECONDS$26, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(PROFILEHARVESTFREQUENCYSECONDS$26);
         }

         target.set(profileHarvestFrequencySeconds);
      }
   }

   public boolean getIgnoreInUseConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNOREINUSECONNECTIONSENABLED$28, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIgnoreInUseConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNOREINUSECONNECTIONSENABLED$28, 0);
         return target;
      }
   }

   public void setIgnoreInUseConnectionsEnabled(boolean ignoreInUseConnectionsEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNOREINUSECONNECTIONSENABLED$28, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IGNOREINUSECONNECTIONSENABLED$28);
         }

         target.setBooleanValue(ignoreInUseConnectionsEnabled);
      }
   }

   public void xsetIgnoreInUseConnectionsEnabled(XmlBoolean ignoreInUseConnectionsEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNOREINUSECONNECTIONSENABLED$28, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(IGNOREINUSECONNECTIONSENABLED$28);
         }

         target.set(ignoreInUseConnectionsEnabled);
      }
   }

   public boolean getMatchConnectionsSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MATCHCONNECTIONSSUPPORTED$30, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMatchConnectionsSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MATCHCONNECTIONSSUPPORTED$30, 0);
         return target;
      }
   }

   public void setMatchConnectionsSupported(boolean matchConnectionsSupported) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MATCHCONNECTIONSSUPPORTED$30, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MATCHCONNECTIONSSUPPORTED$30);
         }

         target.setBooleanValue(matchConnectionsSupported);
      }
   }

   public void xsetMatchConnectionsSupported(XmlBoolean matchConnectionsSupported) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MATCHCONNECTIONSSUPPORTED$30, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MATCHCONNECTIONSSUPPORTED$30);
         }

         target.set(matchConnectionsSupported);
      }
   }

   public boolean getUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEFIRSTAVAILABLE$32, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEFIRSTAVAILABLE$32, 0);
         return target;
      }
   }

   public boolean isSetUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEFIRSTAVAILABLE$32) != 0;
      }
   }

   public void setUseFirstAvailable(boolean useFirstAvailable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEFIRSTAVAILABLE$32, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USEFIRSTAVAILABLE$32);
         }

         target.setBooleanValue(useFirstAvailable);
      }
   }

   public void xsetUseFirstAvailable(XmlBoolean useFirstAvailable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEFIRSTAVAILABLE$32, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USEFIRSTAVAILABLE$32);
         }

         target.set(useFirstAvailable);
      }
   }

   public void unsetUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEFIRSTAVAILABLE$32, 0);
      }
   }
}
