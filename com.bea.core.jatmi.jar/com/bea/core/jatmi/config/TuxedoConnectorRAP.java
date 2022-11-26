package com.bea.core.jatmi.config;

import weblogic.wtc.jatmi.TPException;

public interface TuxedoConnectorRAP {
   void setAccessPoint(String var1) throws TPException;

   String getAccessPoint();

   void setAccessPointId(String var1) throws TPException;

   String getAccessPointId();

   void setNetworkAddr(String[] var1);

   String[] getNetworkAddr();

   void setTpUsrFile(String var1) throws TPException;

   String getTpUsrFile();

   void setAppKey(String var1) throws TPException;

   String getAppKey();

   void setAllowAnonymous(boolean var1);

   boolean isAllowAnonymous();

   void setDefaultAppKey(int var1) throws TPException;

   int getDefaultAppKey();

   void setCustomAppKeyClass(String var1);

   String getCustomAppKeyClass();

   void setCustomAppKeyClassParam(String var1);

   String getCustomAppKeyClassParam();
}
