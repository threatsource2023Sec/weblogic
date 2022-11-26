package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface CoherenceTierMBean extends ConfigurationMBean {
   boolean isLocalStorageEnabled();

   void setLocalStorageEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isCoherenceWebLocalStorageEnabled();

   void setCoherenceWebLocalStorageEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isCoherenceWebFederatedStorageEnabled();

   void setCoherenceWebFederatedStorageEnabled(boolean var1) throws InvalidAttributeValueException;
}
