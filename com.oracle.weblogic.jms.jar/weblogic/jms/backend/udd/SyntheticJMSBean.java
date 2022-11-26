package weblogic.jms.backend.udd;

import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.SAFErrorHandlingBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;

public class SyntheticJMSBean implements JMSBean {
   UDDEntity udd;

   public SyntheticJMSBean(UDDEntity udd) {
      this.udd = udd;
   }

   public JMSConnectionFactoryBean createConnectionFactory(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public DestinationKeyBean createDestinationKey(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public DistributedQueueBean createDistributedQueue(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public DistributedTopicBean createDistributedTopic(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public ForeignServerBean createForeignServer(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public QueueBean createQueue(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public QuotaBean createQuota(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public SAFErrorHandlingBean createSAFErrorHandling(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public SAFImportedDestinationsBean createSAFImportedDestinations(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public SAFRemoteContextBean createSAFRemoteContext(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public TemplateBean createTemplate(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public TopicBean createTopic(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public UniformDistributedQueueBean createUniformDistributedQueue(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public UniformDistributedTopicBean createUniformDistributedTopic(String name) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyConnectionFactory(JMSConnectionFactoryBean connectionFactory) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyDestinationKey(DestinationKeyBean destinationKey) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyDistributedQueue(DistributedQueueBean distributedQueue) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyDistributedTopic(DistributedTopicBean distributedTopic) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyForeignServer(ForeignServerBean foreignServer) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyQueue(QueueBean queue) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyQuota(QuotaBean quota) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroySAFErrorHandling(SAFErrorHandlingBean safErrorHandling) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroySAFImportedDestinations(SAFImportedDestinationsBean safImportedDestinations) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroySAFRemoteContext(SAFRemoteContextBean safRemoteContext) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyTemplate(TemplateBean template) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyTopic(TopicBean topic) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyUniformDistributedQueue(UniformDistributedQueueBean uniformDistributedQueue) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void destroyUniformDistributedTopic(UniformDistributedTopicBean uniformDistributedTopic) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public JMSConnectionFactoryBean[] getConnectionFactories() {
      return this.udd.getJMSModuleBean().getConnectionFactories();
   }

   public DestinationKeyBean[] getDestinationKeys() {
      return this.udd.getJMSModuleBean().getDestinationKeys();
   }

   public DistributedQueueBean[] getDistributedQueues() {
      return this.udd.getJMSModuleBean().getDistributedQueues();
   }

   public DistributedTopicBean[] getDistributedTopics() {
      return this.udd.getJMSModuleBean().getDistributedTopics();
   }

   public ForeignServerBean[] getForeignServers() {
      return this.udd.getJMSModuleBean().getForeignServers();
   }

   public QueueBean[] getQueues() {
      return this.udd.getQueues();
   }

   public QuotaBean[] getQuotas() {
      return this.udd.getJMSModuleBean().getQuotas();
   }

   public SAFErrorHandlingBean[] getSAFErrorHandlings() {
      return this.udd.getJMSModuleBean().getSAFErrorHandlings();
   }

   public SAFImportedDestinationsBean[] getSAFImportedDestinations() {
      return this.udd.getJMSModuleBean().getSAFImportedDestinations();
   }

   public SAFRemoteContextBean[] getSAFRemoteContexts() {
      return this.udd.getJMSModuleBean().getSAFRemoteContexts();
   }

   public TemplateBean[] getTemplates() {
      return this.udd.getJMSModuleBean().getTemplates();
   }

   public TopicBean[] getTopics() {
      return this.udd.getTopics();
   }

   public UniformDistributedQueueBean[] getUniformDistributedQueues() {
      return this.udd.getJMSModuleBean().getUniformDistributedQueues();
   }

   public UniformDistributedTopicBean[] getUniformDistributedTopics() {
      return this.udd.getJMSModuleBean().getUniformDistributedTopics();
   }

   public int getVersion() {
      return this.udd.getJMSModuleBean().getVersion();
   }

   public String getNotes() {
      return this.udd.getJMSModuleBean().getNotes();
   }

   public int hashCode() {
      return this.udd.getJMSModuleBean().hashCode();
   }

   public JMSConnectionFactoryBean lookupConnectionFactory(String name) {
      return this.udd.getJMSModuleBean().lookupConnectionFactory(name);
   }

   public DestinationKeyBean lookupDestinationKey(String name) {
      return this.udd.getJMSModuleBean().lookupDestinationKey(name);
   }

   public DistributedQueueBean lookupDistributedQueue(String name) {
      return this.udd.getJMSModuleBean().lookupDistributedQueue(name);
   }

   public DistributedTopicBean lookupDistributedTopic(String name) {
      return this.udd.getJMSModuleBean().lookupDistributedTopic(name);
   }

   public ForeignServerBean lookupForeignServer(String name) {
      return this.udd.getJMSModuleBean().lookupForeignServer(name);
   }

   public QueueBean lookupQueue(String name) {
      return this.udd.lookupQueue(name);
   }

   public QuotaBean lookupQuota(String name) {
      return this.udd.getJMSModuleBean().lookupQuota(name);
   }

   public SAFErrorHandlingBean lookupSAFErrorHandling(String name) {
      return this.udd.getJMSModuleBean().lookupSAFErrorHandling(name);
   }

   public SAFImportedDestinationsBean lookupSAFImportedDestinations(String name) {
      return this.udd.getJMSModuleBean().lookupSAFImportedDestinations(name);
   }

   public SAFRemoteContextBean lookupSAFRemoteContext(String name) {
      return this.udd.getJMSModuleBean().lookupSAFRemoteContext(name);
   }

   public TemplateBean lookupTemplate(String name) {
      return this.udd.getJMSModuleBean().lookupTemplate(name);
   }

   public TopicBean lookupTopic(String name) {
      return this.udd.lookupTopic(name);
   }

   public UniformDistributedQueueBean lookupUniformDistributedQueue(String name) {
      return this.udd.getJMSModuleBean().lookupUniformDistributedQueue(name);
   }

   public UniformDistributedTopicBean lookupUniformDistributedTopic(String name) {
      return this.udd.getJMSModuleBean().lookupUniformDistributedTopic(name);
   }

   public void setVersion(int version) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setNotes(String notes) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public boolean isSet(String property) {
      return true;
   }

   public void unSet(String property) {
   }
}
