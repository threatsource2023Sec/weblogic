package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SNMPJMXMonitorMBean extends SNMPTrapSourceMBean {
   String getMonitoredMBeanType();

   void setMonitoredMBeanType(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String getMonitoredMBeanName();

   void setMonitoredMBeanName(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String getMonitoredAttributeName();

   void setMonitoredAttributeName(String var1) throws InvalidAttributeValueException, ConfigurationException;

   int getPollingInterval();

   void setPollingInterval(int var1) throws InvalidAttributeValueException, ConfigurationException;
}
