package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface NodeManagerMBean extends ConfigurationMBean {
   /** @deprecated */
   @Deprecated
   String ADAPTER_SEPARATOR = "_";

   void setNMType(String var1) throws InvalidAttributeValueException;

   String getNMType();

   String getListenAddress();

   void setListenAddress(String var1) throws InvalidAttributeValueException;

   int getListenPort();

   void setListenPort(int var1);

   boolean isDebugEnabled();

   void setDebugEnabled(boolean var1);

   void setShellCommand(String var1);

   String getShellCommand();

   void setNodeManagerHome(String var1);

   String getNodeManagerHome();

   /** @deprecated */
   @Deprecated
   void setAdapter(String var1);

   String getAdapter();

   /** @deprecated */
   @Deprecated
   void setAdapterName(String var1);

   String getAdapterName();

   /** @deprecated */
   @Deprecated
   void setAdapterVersion(String var1);

   String getAdapterVersion();

   /** @deprecated */
   @Deprecated
   void setUserName(String var1);

   String getUserName();

   /** @deprecated */
   @Deprecated
   void setPassword(String var1);

   String getPassword();

   byte[] getPasswordEncrypted();

   /** @deprecated */
   @Deprecated
   void setPasswordEncrypted(byte[] var1);

   /** @deprecated */
   @Deprecated
   String[] getInstalledVMMAdapters();

   int getNMSocketCreateTimeoutInMillis();

   void setNMSocketCreateTimeoutInMillis(int var1) throws InvalidAttributeValueException;
}
