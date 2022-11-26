package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCPasswordMBean extends ConfigurationMBean {
   void setLocalAccessPoint(String var1) throws InvalidAttributeValueException;

   String getLocalAccessPoint();

   void setRemoteAccessPoint(String var1) throws InvalidAttributeValueException;

   String getRemoteAccessPoint();

   void setLocalPasswordIV(String var1) throws InvalidAttributeValueException;

   String getLocalPasswordIV();

   void setLocalPassword(String var1) throws InvalidAttributeValueException;

   String getLocalPassword();

   void setRemotePasswordIV(String var1) throws InvalidAttributeValueException;

   String getRemotePasswordIV();

   void setRemotePassword(String var1) throws InvalidAttributeValueException;

   String getRemotePassword();
}
