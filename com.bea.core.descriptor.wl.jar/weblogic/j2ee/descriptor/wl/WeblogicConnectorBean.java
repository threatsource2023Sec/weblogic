package weblogic.j2ee.descriptor.wl;

public interface WeblogicConnectorBean {
   String getNativeLibdir();

   void setNativeLibdir(String var1);

   String getJNDIName();

   void setJNDIName(String var1);

   boolean isEnableAccessOutsideApp();

   void setEnableAccessOutsideApp(boolean var1);

   boolean isEnableGlobalAccessToClasses();

   void setEnableGlobalAccessToClasses(boolean var1);

   boolean isDeployAsAWhole();

   void setDeployAsAWhole(boolean var1);

   WorkManagerBean getWorkManager();

   WorkManagerBean createWorkManager();

   void destroyWorkManager(WorkManagerBean var1);

   ConnectorWorkManagerBean getConnectorWorkManager();

   boolean isConnectorWorkManagerSet();

   ResourceAdapterSecurityBean getSecurity();

   boolean isSecuritySet();

   ConfigPropertiesBean getProperties();

   boolean isPropertiesSet();

   AdminObjectsBean getAdminObjects();

   boolean isAdminObjectsSet();

   OutboundResourceAdapterBean getOutboundResourceAdapter();

   boolean isOutboundResourceAdapterSet();

   String getVersion();

   void setVersion(String var1);
}
