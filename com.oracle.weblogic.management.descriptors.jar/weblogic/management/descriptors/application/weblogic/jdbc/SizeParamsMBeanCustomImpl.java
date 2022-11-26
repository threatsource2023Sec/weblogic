package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SizeParamsMBeanCustomImpl extends XMLElementMBeanDelegate implements SizeParamsMBean {
   private String descrEncoding = null;
   private String descriptorVersion = null;
   private Integer initialCapacity;
   private Integer maxCapacity;
   private Integer capacityIncrement;
   private Boolean shrinkingEnabled;
   private Integer shrinkPeriodMinutes;
   private Integer shrinkFrequencySeconds;
   private Integer highestNumWaiters;
   private Integer highestNumUnavailable;

   public void setEncoding(String encoding) {
      this.descrEncoding = encoding;
   }

   public void setVersion(String version) {
      String old = this.descriptorVersion;
      this.descriptorVersion = version;
      this.checkChange("version", old, this.descriptorVersion);
   }

   public void setInitialCapacity(int value) {
      Integer old = this.initialCapacity;
      this.initialCapacity = new Integer(value);
      this.checkChange("initialCapacity", old, this.initialCapacity);
   }

   public void setMaxCapacity(int value) {
      Integer old = this.maxCapacity;
      this.maxCapacity = new Integer(value);
      this.checkChange("maxCapacity", old, this.maxCapacity);
   }

   public void setCapacityIncrement(int value) {
      Integer old = this.capacityIncrement;
      this.capacityIncrement = new Integer(value);
      this.checkChange("capacityIncrement", old, this.capacityIncrement);
   }

   public void setShrinkingEnabled(boolean value) {
      Boolean old = this.shrinkingEnabled;
      this.shrinkingEnabled = new Boolean(value);
      this.checkChange("shrinkingEnabled", old, this.shrinkingEnabled);
   }

   public void setShrinkPeriodMinutes(int value) {
      Integer old = this.shrinkPeriodMinutes;
      this.shrinkPeriodMinutes = new Integer(value);
      this.checkChange("shrinkPeriodMinutes", old, this.shrinkPeriodMinutes);
   }

   public void setShrinkFrequencySeconds(int value) {
      Integer old = this.shrinkFrequencySeconds;
      this.shrinkFrequencySeconds = new Integer(value);
      this.checkChange("shrinkFrequencySeconds", old, this.shrinkFrequencySeconds);
   }

   public void setHighestNumWaiters(int value) {
      Integer old = this.highestNumWaiters;
      this.highestNumWaiters = new Integer(value);
      this.checkChange("highestNumWaiters", old, this.highestNumWaiters);
   }

   public void setHighestNumUnavailable(int value) {
      Integer old = this.highestNumUnavailable;
      this.highestNumUnavailable = new Integer(value);
      this.checkChange("highestNumUnavailable", old, this.highestNumUnavailable);
   }

   public String getEncoding() {
      return this.descrEncoding;
   }

   public String getVersion() {
      return this.descriptorVersion;
   }

   public int getInitialCapacity() {
      return this.initialCapacity != null ? this.initialCapacity : 1;
   }

   public int getMaxCapacity() {
      return this.maxCapacity != null ? this.maxCapacity : 15;
   }

   public int getCapacityIncrement() {
      return this.capacityIncrement != null ? this.capacityIncrement : 1;
   }

   public boolean isShrinkingEnabled() {
      return this.shrinkingEnabled != null ? this.shrinkingEnabled : true;
   }

   public int getShrinkPeriodMinutes() {
      return this.shrinkPeriodMinutes != null ? this.shrinkPeriodMinutes : 15;
   }

   public int getShrinkFrequencySeconds() {
      return this.shrinkFrequencySeconds != null ? this.shrinkFrequencySeconds : 900;
   }

   public int getHighestNumWaiters() {
      return this.highestNumWaiters != null ? this.highestNumWaiters : Integer.MAX_VALUE;
   }

   public int getHighestNumUnavailable() {
      return this.highestNumUnavailable != null ? this.highestNumUnavailable : 0;
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<size-params");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<initial-capacity>").append(this.getInitialCapacity()).append("</initial-capacity>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<max-capacity>").append(this.getMaxCapacity()).append("</max-capacity>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<capacity-increment>").append(this.getCapacityIncrement()).append("</capacity-increment>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<shrinking-enabled>").append(ToXML.capitalize((new Boolean(this.isShrinkingEnabled())).toString())).append("</shrinking-enabled>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<shrink-period-minutes>").append(this.getShrinkPeriodMinutes()).append("</shrink-period-minutes>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<shrink-frequency-seconds>").append(this.getShrinkFrequencySeconds()).append("</shrink-frequency-seconds>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<highest-num-waiters>").append(this.getHighestNumWaiters()).append("</highest-num-waiters>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<highest-num-unavailable>").append(this.getHighestNumUnavailable()).append("</highest-num-unavailable>\n");
      result.append(ToXML.indent(indentLevel)).append("</size-params>\n");
      return result.toString();
   }
}
