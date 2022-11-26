package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCDataSourceParamsBean extends SettableBean {
   String[] getJNDINames();

   void setJNDINames(String[] var1);

   void addJNDIName(String var1);

   void removeJNDIName(String var1);

   String getScope();

   void setScope(String var1);

   /** @deprecated */
   @Deprecated
   boolean isRowPrefetch();

   /** @deprecated */
   @Deprecated
   void setRowPrefetch(boolean var1);

   /** @deprecated */
   @Deprecated
   int getRowPrefetchSize();

   /** @deprecated */
   @Deprecated
   void setRowPrefetchSize(int var1);

   /** @deprecated */
   @Deprecated
   int getStreamChunkSize();

   /** @deprecated */
   @Deprecated
   void setStreamChunkSize(int var1);

   String getAlgorithmType();

   void setAlgorithmType(String var1);

   String getDataSourceList();

   void setDataSourceList(String var1);

   String getConnectionPoolFailoverCallbackHandler();

   void setConnectionPoolFailoverCallbackHandler(String var1);

   boolean isFailoverRequestIfBusy();

   void setFailoverRequestIfBusy(boolean var1);

   String getGlobalTransactionsProtocol();

   void setGlobalTransactionsProtocol(String var1);

   /** @deprecated */
   @Deprecated
   boolean isKeepConnAfterLocalTx();

   /** @deprecated */
   @Deprecated
   void setKeepConnAfterLocalTx(boolean var1);

   boolean isKeepConnAfterGlobalTx();

   void setKeepConnAfterGlobalTx(boolean var1);

   String getProxySwitchingCallback();

   void setProxySwitchingCallback(String var1);

   String getProxySwitchingProperties();

   void setProxySwitchingProperties(String var1);
}
