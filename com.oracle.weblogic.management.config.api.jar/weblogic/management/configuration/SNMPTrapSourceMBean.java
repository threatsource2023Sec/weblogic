package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SNMPTrapSourceMBean extends ConfigurationMBean {
   ServerMBean[] getEnabledServers();

   void setEnabledServers(ServerMBean[] var1) throws InvalidAttributeValueException, ConfigurationException;

   boolean addEnabledServer(ServerMBean var1) throws InvalidAttributeValueException, ConfigurationException;

   boolean removeEnabledServer(ServerMBean var1) throws InvalidAttributeValueException, ConfigurationException;
}
