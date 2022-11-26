package weblogic.management.runtime;

import weblogic.wtc.gwt.DServiceInfo;
import weblogic.wtc.gwt.DSessConnInfo;
import weblogic.wtc.jatmi.TPException;

public interface WTCRuntimeMBean extends RuntimeMBean {
   void startConnection(String var1, String var2) throws TPException;

   void startConnection(String var1) throws TPException;

   void stopConnection(String var1, String var2) throws TPException;

   void stopConnection(String var1) throws TPException;

   DSessConnInfo[] listConnectionsConfigured();

   void suspendService(String var1) throws TPException;

   void suspendService(String var1, boolean var2) throws TPException;

   void suspendService(String var1, String var2) throws TPException;

   void suspendService(String var1, String var2, boolean var3) throws TPException;

   void suspendService(String var1, String var2, String var3) throws TPException;

   void resumeService(String var1) throws TPException;

   void resumeService(String var1, boolean var2) throws TPException;

   void resumeService(String var1, String var2) throws TPException;

   void resumeService(String var1, String var2, boolean var3) throws TPException;

   void resumeService(String var1, String var2, String var3) throws TPException;

   DServiceInfo[] getServiceStatus() throws TPException;

   int getServiceStatus(String var1) throws TPException;

   int getServiceStatus(String var1, boolean var2) throws TPException;

   int getServiceStatus(String var1, String var2) throws TPException;

   int getServiceStatus(String var1, String var2, boolean var3) throws TPException;

   int getServiceStatus(String var1, String var2, String var3) throws TPException;

   WTCStatisticsRuntimeMBean getWTCStatisticsRuntimeMBean();

   long getWTCServerStartTime();

   String getWTCServerStatus();
}
