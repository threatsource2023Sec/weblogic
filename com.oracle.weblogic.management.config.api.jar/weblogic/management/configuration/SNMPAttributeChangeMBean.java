package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SNMPAttributeChangeMBean extends SNMPTrapSourceMBean {
   String getAttributeMBeanType();

   void setAttributeMBeanType(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String getAttributeMBeanName();

   void setAttributeMBeanName(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String getAttributeName();

   void setAttributeName(String var1) throws InvalidAttributeValueException, ConfigurationException;
}
