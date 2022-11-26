package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JMSBean extends SettableBean {
   int getVersion();

   void setVersion(int var1);

   String getNotes();

   void setNotes(String var1);

   QuotaBean[] getQuotas();

   QuotaBean createQuota(String var1);

   void destroyQuota(QuotaBean var1);

   QuotaBean lookupQuota(String var1);

   TemplateBean[] getTemplates();

   TemplateBean createTemplate(String var1);

   void destroyTemplate(TemplateBean var1);

   TemplateBean lookupTemplate(String var1);

   DestinationKeyBean[] getDestinationKeys();

   DestinationKeyBean createDestinationKey(String var1);

   void destroyDestinationKey(DestinationKeyBean var1);

   DestinationKeyBean lookupDestinationKey(String var1);

   JMSConnectionFactoryBean[] getConnectionFactories();

   JMSConnectionFactoryBean createConnectionFactory(String var1);

   void destroyConnectionFactory(JMSConnectionFactoryBean var1);

   JMSConnectionFactoryBean lookupConnectionFactory(String var1);

   ForeignServerBean[] getForeignServers();

   ForeignServerBean createForeignServer(String var1);

   void destroyForeignServer(ForeignServerBean var1);

   ForeignServerBean lookupForeignServer(String var1);

   QueueBean[] getQueues();

   QueueBean createQueue(String var1);

   void destroyQueue(QueueBean var1);

   QueueBean lookupQueue(String var1);

   TopicBean[] getTopics();

   TopicBean createTopic(String var1);

   void destroyTopic(TopicBean var1);

   TopicBean lookupTopic(String var1);

   /** @deprecated */
   @Deprecated
   DistributedQueueBean[] getDistributedQueues();

   /** @deprecated */
   @Deprecated
   DistributedQueueBean createDistributedQueue(String var1);

   /** @deprecated */
   @Deprecated
   void destroyDistributedQueue(DistributedQueueBean var1);

   /** @deprecated */
   @Deprecated
   DistributedQueueBean lookupDistributedQueue(String var1);

   /** @deprecated */
   @Deprecated
   DistributedTopicBean[] getDistributedTopics();

   /** @deprecated */
   @Deprecated
   DistributedTopicBean createDistributedTopic(String var1);

   /** @deprecated */
   @Deprecated
   void destroyDistributedTopic(DistributedTopicBean var1);

   /** @deprecated */
   @Deprecated
   DistributedTopicBean lookupDistributedTopic(String var1);

   UniformDistributedQueueBean[] getUniformDistributedQueues();

   UniformDistributedQueueBean createUniformDistributedQueue(String var1);

   void destroyUniformDistributedQueue(UniformDistributedQueueBean var1);

   UniformDistributedQueueBean lookupUniformDistributedQueue(String var1);

   UniformDistributedTopicBean[] getUniformDistributedTopics();

   UniformDistributedTopicBean createUniformDistributedTopic(String var1);

   void destroyUniformDistributedTopic(UniformDistributedTopicBean var1);

   UniformDistributedTopicBean lookupUniformDistributedTopic(String var1);

   SAFImportedDestinationsBean[] getSAFImportedDestinations();

   SAFImportedDestinationsBean createSAFImportedDestinations(String var1);

   void destroySAFImportedDestinations(SAFImportedDestinationsBean var1);

   SAFImportedDestinationsBean lookupSAFImportedDestinations(String var1);

   SAFRemoteContextBean[] getSAFRemoteContexts();

   SAFRemoteContextBean createSAFRemoteContext(String var1);

   void destroySAFRemoteContext(SAFRemoteContextBean var1);

   SAFRemoteContextBean lookupSAFRemoteContext(String var1);

   SAFErrorHandlingBean[] getSAFErrorHandlings();

   SAFErrorHandlingBean createSAFErrorHandling(String var1);

   void destroySAFErrorHandling(SAFErrorHandlingBean var1);

   SAFErrorHandlingBean lookupSAFErrorHandling(String var1);
}
