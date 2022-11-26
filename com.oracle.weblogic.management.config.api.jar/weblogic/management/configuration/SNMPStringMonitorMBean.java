package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SNMPStringMonitorMBean extends SNMPJMXMonitorMBean {
   String getStringToCompare();

   void setStringToCompare(String var1) throws InvalidAttributeValueException, ConfigurationException;

   boolean isNotifyDiffer();

   void setNotifyDiffer(boolean var1);

   boolean isNotifyMatch();

   void setNotifyMatch(boolean var1);
}
