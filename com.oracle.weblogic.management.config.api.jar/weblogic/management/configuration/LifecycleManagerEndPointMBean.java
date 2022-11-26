package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface LifecycleManagerEndPointMBean extends ConfigurationMBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   String getURL();

   void setURL(String var1);

   String getRuntimeName();

   void setRuntimeName(String var1);

   String getUsername();

   void setUsername(String var1);

   String getPassword();

   void setPassword(String var1) throws InvalidAttributeValueException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;
}
