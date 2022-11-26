package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface PoolParamsMBean extends XMLElementMBean {
   SizeParamsMBean getSizeParams();

   void setSizeParams(SizeParamsMBean var1);

   XaParamsMBean getXaParams();

   void setXaParams(XaParamsMBean var1);

   int getLoginDelaySeconds();

   void setLoginDelaySeconds(int var1);

   int getSecondsToTrustAnIdlePoolConnection();

   void setSecondsToTrustAnIdlePoolConnection(int var1);

   boolean isLeakProfilingEnabled();

   void setLeakProfilingEnabled(boolean var1);

   ConnectionCheckParamsMBean getConnectionCheckParams();

   void setConnectionCheckParams(ConnectionCheckParamsMBean var1);

   int getJDBCXADebugLevel();

   void setJDBCXADebugLevel(int var1);

   boolean isRemoveInfectedConnectionsEnabled();

   void setRemoveInfectedConnectionsEnabled(boolean var1);
}
