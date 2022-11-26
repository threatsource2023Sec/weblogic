package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCOracleParamsBean extends SettableBean {
   boolean isFanEnabled();

   void setFanEnabled(boolean var1);

   String getOnsNodeList();

   void setOnsNodeList(String var1);

   String getOnsWalletFile();

   void setOnsWalletFile(String var1);

   byte[] getOnsWalletPasswordEncrypted();

   void setOnsWalletPasswordEncrypted(byte[] var1);

   String getOnsWalletPassword();

   void setOnsWalletPassword(String var1);

   /** @deprecated */
   @Deprecated
   boolean isOracleEnableJavaNetFastPath();

   /** @deprecated */
   @Deprecated
   void setOracleEnableJavaNetFastPath(boolean var1);

   boolean isOracleOptimizeUtf8Conversion();

   void setOracleOptimizeUtf8Conversion(boolean var1);

   String getConnectionInitializationCallback();

   void setConnectionInitializationCallback(String var1);

   String getAffinityPolicy();

   void setAffinityPolicy(String var1);

   boolean isOracleProxySession();

   void setOracleProxySession(boolean var1);

   boolean isUseDatabaseCredentials();

   void setUseDatabaseCredentials(boolean var1);

   int getReplayInitiationTimeout();

   void setReplayInitiationTimeout(int var1);

   boolean isActiveGridlink();

   void setActiveGridlink(boolean var1);
}
