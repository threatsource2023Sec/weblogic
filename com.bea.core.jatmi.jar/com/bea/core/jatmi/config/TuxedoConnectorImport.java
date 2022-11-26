package com.bea.core.jatmi.config;

public interface TuxedoConnectorImport {
   void setResourceName(String var1);

   String getResourceName();

   void setSessionNameList(String[] var1);

   String[] getSessionNameList();

   void setRemoteName(String var1);

   String getRemoteName();

   void setLBAlgorithm(String var1);

   String getLBAlgorithm();
}
