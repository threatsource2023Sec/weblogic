package weblogic.j2ee.descriptor.wl;

public interface ApplicationPoolParamsBean {
   SizeParamsBean getSizeParams();

   SizeParamsBean createSizeParams();

   void destroySizeParams(SizeParamsBean var1);

   XAParamsBean getXAParams();

   XAParamsBean createXAParams();

   void destroyXAParams(XAParamsBean var1);

   int getLoginDelaySeconds();

   void setLoginDelaySeconds(int var1);

   boolean isLeakProfilingEnabled();

   void setLeakProfilingEnabled(boolean var1);

   ConnectionCheckParamsBean getConnectionCheckParams();

   ConnectionCheckParamsBean createConnectionCheckParams();

   void destroyConnectionCheckParams(ConnectionCheckParamsBean var1);

   int getJDBCXADebugLevel();

   void setJDBCXADebugLevel(int var1);

   boolean isRemoveInfectedConnectionsEnabled();

   void setRemoveInfectedConnectionsEnabled(boolean var1);
}
