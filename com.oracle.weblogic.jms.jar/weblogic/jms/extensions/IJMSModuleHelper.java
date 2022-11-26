package weblogic.jms.extensions;

import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;
import weblogic.jms.common.JMSException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.SubDeploymentMBean;

public interface IJMSModuleHelper {
   String DEFAULT_TARGETING_ENABLED = "*Default Targeting Enabled*";

   void createJMSSystemResource(String var1, String var2) throws JMSException;

   void deleteJMSSystemResource(String var1) throws JMSException;

   void createPersistentStore(StoreType var1, String var2, String var3, PersistentStoreModifier var4) throws JMSException;

   void deletePersistentStore(String var1) throws JMSException;

   void createJMSServer(String var1, String var2, String var3) throws JMSException;

   void deleteJMSServer(String var1) throws JMSException;

   void createConnectionFactory(String var1, String var2, String var3, String var4, JMSNamedEntityModifier var5) throws JMSException;

   void deleteConnectionFactory(String var1, String var2) throws JMSException;

   void createUniformDistributedQueue(String var1, String var2, String var3, String var4, JMSNamedEntityModifier var5) throws JMSException;

   void deleteUniformDistributedQueue(String var1, String var2) throws JMSException;

   void createUniformDistributedTopic(String var1, String var2, String var3, String var4, JMSNamedEntityModifier var5) throws JMSException;

   void deleteUniformDistributedTopic(String var1, String var2) throws JMSException;

   JMSSystemResourceMBean findJMSSystemResource(String var1) throws JMSException;

   void deployJMSServer(String var1, String var2) throws JMSException;

   void undeployJMSServer(String var1) throws JMSException;

   void createQueue(String var1, String var2, String var3, String var4, JMSNamedEntityModifier var5) throws JMSException;

   void deleteQueue(String var1, String var2) throws JMSException;

   void createSAFAgent(String var1, String var2, String var3) throws JMSException;

   void deleteSAFAgent(String var1) throws JMSException;

   DestinationBean findDestinationBean(String var1, JMSBean var2);

   void createQuota(String var1, String var2, JMSNamedEntityModifier var3) throws JMSException;

   void deleteQuota(String var1, String var2) throws JMSException;

   void createSAFErrorHandling(String var1, String var2, JMSNamedEntityModifier var3) throws JMSException;

   void deleteSAFErrorHandling(String var1, String var2) throws JMSException;

   void createSAFImportedDestinations(String var1, String var2, String var3, String var4, String var5, JMSNamedEntityModifier var6) throws JMSException;

   void deleteSAFImportedDestinations(String var1, String var2) throws JMSException;

   void createSAFQueue(String var1, String var2, String var3, String var4, JMSNamedEntityModifier var5) throws JMSException;

   void deleteSAFQueue(String var1, String var2, String var3) throws JMSException;

   void createSAFRemoteContext(String var1, String var2, JMSNamedEntityModifier var3) throws JMSException;

   void deleteSAFRemoteContext(String var1, String var2) throws JMSException;

   void createSAFTopic(String var1, String var2, String var3, String var4, JMSNamedEntityModifier var5) throws JMSException;

   void deleteSAFTopic(String var1, String var2, String var3) throws JMSException;

   void createTemplate(String var1, String var2, JMSNamedEntityModifier var3) throws JMSException;

   void deleteTemplate(String var1, String var2) throws JMSException;

   void createTopic(String var1, String var2, String var3, String var4, JMSNamedEntityModifier var5) throws JMSException;

   void deleteTopic(String var1, String var2) throws JMSException;

   void deploySAFAgent(String var1, String var2) throws JMSException;

   void undeploySAFAgent(String var1) throws JMSException;

   boolean isTargetInDeploymentScope(ConfigurationMBean var1, WebLogicMBean var2);

   String uddMemberName(String var1, String var2);

   String uddMemberJNDIName(String var1, String var2);

   String[] uddReturnJMSServers(SubDeploymentMBean var1);

   String[] uddReturnJMSServers(JMSSystemResourceMBean var1, UniformDistributedDestinationBean var2);

   void createDestinationKey(String var1, String var2, String var3, String var4, String var5, JMSNamedEntityModifier var6) throws JMSException;

   void deleteDestinationKey(String var1, String var2) throws JMSException;

   void createForeignServer(String var1, String var2, String var3, JMSNamedEntityModifier var4) throws JMSException;

   void deleteForeignServer(String var1, String var2) throws JMSException;

   void createForeignConnectionFactory(String var1, String var2, String var3, String var4, String var5, String var6, String var7, JMSNamedEntityModifier var8) throws JMSException;

   void deleteForeignConnectionFactory(String var1, String var2, String var3) throws JMSException;

   void createForeignDestination(String var1, String var2, String var3, String var4, String var5, JMSNamedEntityModifier var6) throws JMSException;

   void deleteForeignDestination(String var1, String var2, String var3) throws JMSException;

   DestinationBean[] findAllInheritedDestinations(String var1, JMSBean var2);

   QueueBean[] findAllInheritedQueueBeans(String var1, JMSBean var2);

   TopicBean[] findAllInheritedTopicBeans(String var1, JMSBean var2);

   void findAndModifyEntity(String var1, String var2, String var3, JMSNamedEntityModifier var4) throws JMSException;

   public static enum StoreType {
      FILE,
      JDBC,
      REPLICATED;
   }

   public static enum ScopeType {
      DOMAIN,
      RG,
      RGT;
   }
}
