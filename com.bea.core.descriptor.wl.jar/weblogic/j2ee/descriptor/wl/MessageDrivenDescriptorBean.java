package weblogic.j2ee.descriptor.wl;

public interface MessageDrivenDescriptorBean {
   PoolBean getPool();

   boolean isPoolSet();

   TimerDescriptorBean getTimerDescriptor();

   boolean isTimerDescriptorSet();

   String getResourceAdapterJNDIName();

   void setResourceAdapterJNDIName(String var1);

   String getDestinationJNDIName();

   void setDestinationJNDIName(String var1);

   String getInitialContextFactory();

   void setInitialContextFactory(String var1);

   String getProviderUrl();

   void setProviderUrl(String var1);

   String getConnectionFactoryJNDIName();

   void setConnectionFactoryJNDIName(String var1);

   String getDestinationResourceLink();

   void setDestinationResourceLink(String var1);

   String getConnectionFactoryResourceLink();

   void setConnectionFactoryResourceLink(String var1);

   int getJmsPollingIntervalSeconds();

   void setJmsPollingIntervalSeconds(int var1);

   String getJmsClientId();

   void setJmsClientId(String var1);

   boolean isGenerateUniqueJmsClientId();

   void setGenerateUniqueJmsClientId(boolean var1);

   boolean isDurableSubscriptionDeletion();

   void setDurableSubscriptionDeletion(boolean var1);

   int getMaxMessagesInTransaction();

   void setMaxMessagesInTransaction(int var1);

   String getDistributedDestinationConnection();

   void setDistributedDestinationConnection(String var1);

   boolean isUse81StylePolling();

   void setUse81StylePolling(boolean var1);

   int getInitSuspendSeconds();

   void setInitSuspendSeconds(int var1);

   int getMaxSuspendSeconds();

   void setMaxSuspendSeconds(int var1);

   SecurityPluginBean getSecurityPlugin();

   boolean isSecurityPluginSet();

   String getId();

   void setId(String var1);
}
