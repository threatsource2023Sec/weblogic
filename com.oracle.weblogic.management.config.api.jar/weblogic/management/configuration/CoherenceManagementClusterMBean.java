package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface CoherenceManagementClusterMBean extends ConfigurationMBean {
   String getUsername();

   void setUsername(String var1);

   String getPassword();

   void setPassword(String var1) throws InvalidAttributeValueException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   byte[] getEncryptedPassword();

   /** @deprecated */
   @Deprecated
   void setEncryptedPassword(byte[] var1) throws InvalidAttributeValueException;

   String getReportGroupFile();

   void setReportGroupFile(String var1);

   CoherenceManagementAddressProviderMBean[] getCoherenceManagementAddressProviders();

   CoherenceManagementAddressProviderMBean createCoherenceManagementAddressProvider(String var1);

   CoherenceManagementAddressProviderMBean lookupCoherenceManagementAddressProvider(String var1);

   void destroyCoherenceManagementAddressProvider(CoherenceManagementAddressProviderMBean var1);
}
