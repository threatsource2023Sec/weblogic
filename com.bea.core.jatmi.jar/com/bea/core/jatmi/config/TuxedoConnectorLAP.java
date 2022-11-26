package com.bea.core.jatmi.config;

import weblogic.wtc.jatmi.TPException;

public interface TuxedoConnectorLAP {
   void setAccessPoint(String var1) throws TPException;

   String getAccessPoint();

   void setAccessPointId(String var1) throws TPException;

   String getAccessPointId();

   void setNetworkAddr(String[] var1) throws TPException;

   String[] getNetworkAddr();

   void setBlockTime(long var1) throws TPException;

   long getBlockTime();

   void setInteroperate(String var1) throws TPException;

   String getInteroperate();
}
