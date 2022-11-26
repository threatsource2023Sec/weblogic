package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.descriptor.DescriptorBean;

public class JMSBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private JMSBean beanTreeNode;

   public JMSBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JMSBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public JMSBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (JMSBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public JMSBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(JMSBean.class);
         this.beanTreeNode = (JMSBean)this.beanTree;
         this.customInit();
      } catch (ConfigurationException var6) {
         throw new InvalidModuleException(var6.toString());
      }
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public int getVersion() {
      return this.beanTreeNode.getVersion();
   }

   public void setVersion(int value) {
      this.beanTreeNode.setVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Version", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNotes() {
      return this.beanTreeNode.getNotes();
   }

   public void setNotes(String value) {
      this.beanTreeNode.setNotes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Notes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public QuotaBean[] getQuotas() {
      return this.beanTreeNode.getQuotas();
   }

   public TemplateBean[] getTemplates() {
      return this.beanTreeNode.getTemplates();
   }

   public DestinationKeyBean[] getDestinationKeys() {
      return this.beanTreeNode.getDestinationKeys();
   }

   public JMSConnectionFactoryBean[] getConnectionFactories() {
      return this.beanTreeNode.getConnectionFactories();
   }

   public ForeignServerBean[] getForeignServers() {
      return this.beanTreeNode.getForeignServers();
   }

   public QueueBean[] getQueues() {
      return this.beanTreeNode.getQueues();
   }

   public TopicBean[] getTopics() {
      return this.beanTreeNode.getTopics();
   }

   public DistributedQueueBean[] getDistributedQueues() {
      return this.beanTreeNode.getDistributedQueues();
   }

   public DistributedTopicBean[] getDistributedTopics() {
      return this.beanTreeNode.getDistributedTopics();
   }

   public UniformDistributedQueueBean[] getUniformDistributedQueues() {
      return this.beanTreeNode.getUniformDistributedQueues();
   }

   public UniformDistributedTopicBean[] getUniformDistributedTopics() {
      return this.beanTreeNode.getUniformDistributedTopics();
   }

   public SAFImportedDestinationsBean[] getSAFImportedDestinations() {
      return this.beanTreeNode.getSAFImportedDestinations();
   }

   public SAFRemoteContextBean[] getSAFRemoteContexts() {
      return this.beanTreeNode.getSAFRemoteContexts();
   }

   public SAFErrorHandlingBean[] getSAFErrorHandlings() {
      return this.beanTreeNode.getSAFErrorHandlings();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
