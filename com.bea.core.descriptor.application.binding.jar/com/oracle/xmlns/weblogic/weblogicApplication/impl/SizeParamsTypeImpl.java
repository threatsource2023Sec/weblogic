package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicApplication.SizeParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import javax.xml.namespace.QName;

public class SizeParamsTypeImpl extends XmlComplexContentImpl implements SizeParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName INITIALCAPACITY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "initial-capacity");
   private static final QName MAXCAPACITY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "max-capacity");
   private static final QName CAPACITYINCREMENT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "capacity-increment");
   private static final QName SHRINKINGENABLED$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "shrinking-enabled");
   private static final QName SHRINKPERIODMINUTES$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "shrink-period-minutes");
   private static final QName SHRINKFREQUENCYSECONDS$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "shrink-frequency-seconds");
   private static final QName HIGHESTNUMWAITERS$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "highest-num-waiters");
   private static final QName HIGHESTNUMUNAVAILABLE$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "highest-num-unavailable");

   public SizeParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         return target;
      }
   }

   public boolean isSetInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALCAPACITY$0) != 0;
      }
   }

   public void setInitialCapacity(int initialCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INITIALCAPACITY$0);
         }

         target.setIntValue(initialCapacity);
      }
   }

   public void xsetInitialCapacity(XmlInt initialCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INITIALCAPACITY$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INITIALCAPACITY$0);
         }

         target.set(initialCapacity);
      }
   }

   public void unsetInitialCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALCAPACITY$0, 0);
      }
   }

   public int getMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         return target;
      }
   }

   public boolean isSetMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCAPACITY$2) != 0;
      }
   }

   public void setMaxCapacity(int maxCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXCAPACITY$2);
         }

         target.setIntValue(maxCapacity);
      }
   }

   public void xsetMaxCapacity(XmlInt maxCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCAPACITY$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXCAPACITY$2);
         }

         target.set(maxCapacity);
      }
   }

   public void unsetMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCAPACITY$2, 0);
      }
   }

   public int getCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         return target;
      }
   }

   public boolean isSetCapacityIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CAPACITYINCREMENT$4) != 0;
      }
   }

   public void setCapacityIncrement(int capacityIncrement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CAPACITYINCREMENT$4);
         }

         target.setIntValue(capacityIncrement);
      }
   }

   public void xsetCapacityIncrement(XmlInt capacityIncrement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CAPACITYINCREMENT$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CAPACITYINCREMENT$4);
         }

         target.set(capacityIncrement);
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

   public int getShrinkPeriodMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKPERIODMINUTES$8, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetShrinkPeriodMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SHRINKPERIODMINUTES$8, 0);
         return target;
      }
   }

   public boolean isSetShrinkPeriodMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHRINKPERIODMINUTES$8) != 0;
      }
   }

   public void setShrinkPeriodMinutes(int shrinkPeriodMinutes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKPERIODMINUTES$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHRINKPERIODMINUTES$8);
         }

         target.setIntValue(shrinkPeriodMinutes);
      }
   }

   public void xsetShrinkPeriodMinutes(XmlInt shrinkPeriodMinutes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SHRINKPERIODMINUTES$8, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(SHRINKPERIODMINUTES$8);
         }

         target.set(shrinkPeriodMinutes);
      }
   }

   public void unsetShrinkPeriodMinutes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHRINKPERIODMINUTES$8, 0);
      }
   }

   public int getShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$10, 0);
         return target;
      }
   }

   public boolean isSetShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHRINKFREQUENCYSECONDS$10) != 0;
      }
   }

   public void setShrinkFrequencySeconds(int shrinkFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHRINKFREQUENCYSECONDS$10);
         }

         target.setIntValue(shrinkFrequencySeconds);
      }
   }

   public void xsetShrinkFrequencySeconds(XmlInt shrinkFrequencySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SHRINKFREQUENCYSECONDS$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(SHRINKFREQUENCYSECONDS$10);
         }

         target.set(shrinkFrequencySeconds);
      }
   }

   public void unsetShrinkFrequencySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHRINKFREQUENCYSECONDS$10, 0);
      }
   }

   public int getHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMWAITERS$12, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(HIGHESTNUMWAITERS$12, 0);
         return target;
      }
   }

   public boolean isSetHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HIGHESTNUMWAITERS$12) != 0;
      }
   }

   public void setHighestNumWaiters(int highestNumWaiters) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMWAITERS$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HIGHESTNUMWAITERS$12);
         }

         target.setIntValue(highestNumWaiters);
      }
   }

   public void xsetHighestNumWaiters(XmlInt highestNumWaiters) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(HIGHESTNUMWAITERS$12, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(HIGHESTNUMWAITERS$12);
         }

         target.set(highestNumWaiters);
      }
   }

   public void unsetHighestNumWaiters() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HIGHESTNUMWAITERS$12, 0);
      }
   }

   public int getHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$14, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$14, 0);
         return target;
      }
   }

   public boolean isSetHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HIGHESTNUMUNAVAILABLE$14) != 0;
      }
   }

   public void setHighestNumUnavailable(int highestNumUnavailable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HIGHESTNUMUNAVAILABLE$14);
         }

         target.setIntValue(highestNumUnavailable);
      }
   }

   public void xsetHighestNumUnavailable(XmlInt highestNumUnavailable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(HIGHESTNUMUNAVAILABLE$14, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(HIGHESTNUMUNAVAILABLE$14);
         }

         target.set(highestNumUnavailable);
      }
   }

   public void unsetHighestNumUnavailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HIGHESTNUMUNAVAILABLE$14, 0);
      }
   }
}
