package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SizeParamsMBeanImpl extends XMLElementMBeanDelegate implements SizeParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_shrinkPeriodMinutes = false;
   private int shrinkPeriodMinutes;
   private boolean isSet_initialCapacity = false;
   private int initialCapacity;
   private boolean isSet_highestNumUnavailable = false;
   private int highestNumUnavailable;
   private boolean isSet_shrinkFrequencySeconds = false;
   private int shrinkFrequencySeconds;
   private boolean isSet_highestNumWaiters = false;
   private int highestNumWaiters;
   private boolean isSet_shrinkingEnabled = false;
   private boolean shrinkingEnabled;
   private boolean isSet_capacityIncrement = false;
   private int capacityIncrement;
   private boolean isSet_maxCapacity = false;
   private int maxCapacity;

   public int getShrinkPeriodMinutes() {
      return this.shrinkPeriodMinutes;
   }

   public void setShrinkPeriodMinutes(int value) {
      int old = this.shrinkPeriodMinutes;
      this.shrinkPeriodMinutes = value;
      this.isSet_shrinkPeriodMinutes = value != -1;
      this.checkChange("shrinkPeriodMinutes", old, this.shrinkPeriodMinutes);
   }

   public int getInitialCapacity() {
      return this.initialCapacity;
   }

   public void setInitialCapacity(int value) {
      int old = this.initialCapacity;
      this.initialCapacity = value;
      this.isSet_initialCapacity = value != -1;
      this.checkChange("initialCapacity", old, this.initialCapacity);
   }

   public int getHighestNumUnavailable() {
      return this.highestNumUnavailable;
   }

   public void setHighestNumUnavailable(int value) {
      int old = this.highestNumUnavailable;
      this.highestNumUnavailable = value;
      this.isSet_highestNumUnavailable = value != -1;
      this.checkChange("highestNumUnavailable", old, this.highestNumUnavailable);
   }

   public int getShrinkFrequencySeconds() {
      return this.shrinkFrequencySeconds;
   }

   public void setShrinkFrequencySeconds(int value) {
      int old = this.shrinkFrequencySeconds;
      this.shrinkFrequencySeconds = value;
      this.isSet_shrinkFrequencySeconds = value != -1;
      this.checkChange("shrinkFrequencySeconds", old, this.shrinkFrequencySeconds);
   }

   public int getHighestNumWaiters() {
      return this.highestNumWaiters;
   }

   public void setHighestNumWaiters(int value) {
      int old = this.highestNumWaiters;
      this.highestNumWaiters = value;
      this.isSet_highestNumWaiters = value != -1;
      this.checkChange("highestNumWaiters", old, this.highestNumWaiters);
   }

   public boolean isShrinkingEnabled() {
      return this.shrinkingEnabled;
   }

   public void setShrinkingEnabled(boolean value) {
      boolean old = this.shrinkingEnabled;
      this.shrinkingEnabled = value;
      this.isSet_shrinkingEnabled = true;
      this.checkChange("shrinkingEnabled", old, this.shrinkingEnabled);
   }

   public int getCapacityIncrement() {
      return this.capacityIncrement;
   }

   public void setCapacityIncrement(int value) {
      int old = this.capacityIncrement;
      this.capacityIncrement = value;
      this.isSet_capacityIncrement = value != -1;
      this.checkChange("capacityIncrement", old, this.capacityIncrement);
   }

   public int getMaxCapacity() {
      return this.maxCapacity;
   }

   public void setMaxCapacity(int value) {
      int old = this.maxCapacity;
      this.maxCapacity = value;
      this.isSet_maxCapacity = value != -1;
      this.checkChange("maxCapacity", old, this.maxCapacity);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<size-params");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</size-params>\n");
      return result.toString();
   }
}
