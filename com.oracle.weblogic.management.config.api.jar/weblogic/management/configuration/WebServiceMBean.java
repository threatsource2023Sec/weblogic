package weblogic.management.configuration;

public interface WebServiceMBean extends ConfigurationMBean {
   void setJmsConnectionFactory(String var1);

   String getJmsConnectionFactory();

   void setMessagingQueue(String var1);

   String getMessagingQueue();

   void setMessagingQueueMDBRunAsPrincipalName(String var1);

   String getMessagingQueueMDBRunAsPrincipalName();

   void setCallbackQueue(String var1);

   String getCallbackQueue();

   void setCallbackQueueMDBRunAsPrincipalName(String var1);

   String getCallbackQueueMDBRunAsPrincipalName();

   WebServicePersistenceMBean getWebServicePersistence();

   WebServiceBufferingMBean getWebServiceBuffering();

   WebServiceReliabilityMBean getWebServiceReliability();

   WebServiceResiliencyMBean getWebServiceResiliency();
}
