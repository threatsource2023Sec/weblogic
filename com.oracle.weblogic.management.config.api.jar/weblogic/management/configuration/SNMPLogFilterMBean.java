package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SNMPLogFilterMBean extends SNMPTrapSourceMBean {
   String getSeverityLevel();

   void setSeverityLevel(String var1);

   String[] getSubsystemNames();

   void setSubsystemNames(String[] var1);

   boolean addSubsystemName(String var1) throws InvalidAttributeValueException, ConfigurationException;

   boolean removeSubsystemName(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String[] getUserIds();

   void setUserIds(String[] var1);

   boolean addUserId(String var1) throws InvalidAttributeValueException, ConfigurationException;

   boolean removeUserId(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String[] getMessageIds();

   void setMessageIds(String[] var1);

   boolean addMessageId(String var1) throws InvalidAttributeValueException, ConfigurationException;

   boolean removeMessageId(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String getMessageSubstring();

   void setMessageSubstring(String var1) throws InvalidAttributeValueException, ConfigurationException;
}
