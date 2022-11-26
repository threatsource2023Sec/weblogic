package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface CoherenceMemberConfigMBean extends ConfigurationMBean {
   String getUnicastListenAddress();

   void setUnicastListenAddress(String var1) throws InvalidAttributeValueException;

   int getUnicastListenPort();

   void setUnicastListenPort(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isUnicastPortAutoAdjust();

   /** @deprecated */
   @Deprecated
   void setUnicastPortAutoAdjust(boolean var1);

   int getUnicastPortAutoAdjustAttempts();

   void setUnicastPortAutoAdjustAttempts(int var1);

   boolean isLocalStorageEnabled();

   void setLocalStorageEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isCoherenceWebLocalStorageEnabled();

   void setCoherenceWebLocalStorageEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isCoherenceWebFederatedStorageEnabled();

   void setCoherenceWebFederatedStorageEnabled(boolean var1) throws InvalidAttributeValueException;

   String getSiteName();

   void setSiteName(String var1) throws InvalidAttributeValueException;

   String getRackName();

   void setRackName(String var1) throws InvalidAttributeValueException;

   String getRoleName();

   void setRoleName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isManagementProxy();

   /** @deprecated */
   @Deprecated
   void setManagementProxy(boolean var1) throws InvalidAttributeValueException;
}
