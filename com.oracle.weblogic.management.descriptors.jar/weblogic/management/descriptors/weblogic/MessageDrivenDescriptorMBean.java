package weblogic.management.descriptors.weblogic;

public interface MessageDrivenDescriptorMBean extends WeblogicBeanDescriptorMBean {
   PoolMBean getPool();

   void setPool(PoolMBean var1);

   String getDestinationJNDIName();

   void setDestinationJNDIName(String var1);

   String getInitialContextFactory();

   void setInitialContextFactory(String var1);

   String getProviderURL();

   void setProviderURL(String var1);

   String getConnectionFactoryJNDIName();

   void setConnectionFactoryJNDIName(String var1);

   int getJMSPollingIntervalSeconds();

   void setJMSPollingIntervalSeconds(int var1);

   String getJMSClientID();

   void setJMSClientID(String var1);
}
