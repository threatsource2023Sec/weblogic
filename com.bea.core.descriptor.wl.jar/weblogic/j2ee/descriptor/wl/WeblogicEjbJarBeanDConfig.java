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

public class WeblogicEjbJarBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicEjbJarBean beanTreeNode;

   public WeblogicEjbJarBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicEjbJarBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public WeblogicEjbJarBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (WeblogicEjbJarBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public WeblogicEjbJarBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(WeblogicEjbJarBean.class);
         this.beanTreeNode = (WeblogicEjbJarBean)this.beanTree;
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

   public String getDescription() {
      return this.beanTreeNode.getDescription();
   }

   public void setDescription(String value) {
      this.beanTreeNode.setDescription(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Description", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WeblogicEnterpriseBeanBean[] getWeblogicEnterpriseBeans() {
      return this.beanTreeNode.getWeblogicEnterpriseBeans();
   }

   public SecurityRoleAssignmentBean[] getSecurityRoleAssignments() {
      return this.beanTreeNode.getSecurityRoleAssignments();
   }

   public RunAsRoleAssignmentBean[] getRunAsRoleAssignments() {
      return this.beanTreeNode.getRunAsRoleAssignments();
   }

   public SecurityPermissionBean getSecurityPermission() {
      return this.beanTreeNode.getSecurityPermission();
   }

   public TransactionIsolationBean[] getTransactionIsolations() {
      return this.beanTreeNode.getTransactionIsolations();
   }

   public MessageDestinationDescriptorBean[] getMessageDestinationDescriptors() {
      return this.beanTreeNode.getMessageDestinationDescriptors();
   }

   public IdempotentMethodsBean getIdempotentMethods() {
      return this.beanTreeNode.getIdempotentMethods();
   }

   public SkipStateReplicationMethodsBean getSkipStateReplicationMethods() {
      return this.beanTreeNode.getSkipStateReplicationMethods();
   }

   public RetryMethodsOnRollbackBean[] getRetryMethodsOnRollbacks() {
      return this.beanTreeNode.getRetryMethodsOnRollbacks();
   }

   public boolean isEnableBeanClassRedeploy() {
      return this.beanTreeNode.isEnableBeanClassRedeploy();
   }

   public void setEnableBeanClassRedeploy(boolean value) {
      this.beanTreeNode.setEnableBeanClassRedeploy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableBeanClassRedeploy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTimerImplementation() {
      return this.beanTreeNode.getTimerImplementation();
   }

   public void setTimerImplementation(String value) {
      this.beanTreeNode.setTimerImplementation(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimerImplementation", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getDisableWarnings() {
      return this.beanTreeNode.getDisableWarnings();
   }

   public void setDisableWarnings(String[] value) {
      this.beanTreeNode.setDisableWarnings(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DisableWarnings", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WorkManagerBean[] getWorkManagers() {
      return this.beanTreeNode.getWorkManagers();
   }

   public ManagedExecutorServiceBean[] getManagedExecutorServices() {
      return this.beanTreeNode.getManagedExecutorServices();
   }

   public ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorServices() {
      return this.beanTreeNode.getManagedScheduledExecutorServices();
   }

   public ManagedThreadFactoryBean[] getManagedThreadFactories() {
      return this.beanTreeNode.getManagedThreadFactories();
   }

   public String getComponentFactoryClassName() {
      return this.beanTreeNode.getComponentFactoryClassName();
   }

   public void setComponentFactoryClassName(String value) {
      this.beanTreeNode.setComponentFactoryClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ComponentFactoryClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WeblogicCompatibilityBean getWeblogicCompatibility() {
      return this.beanTreeNode.getWeblogicCompatibility();
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }

   public CoherenceClusterRefBean getCoherenceClusterRef() {
      return this.beanTreeNode.getCoherenceClusterRef();
   }

   public String getVersion() {
      return this.beanTreeNode.getVersion();
   }

   public void setVersion(String value) {
      this.beanTreeNode.setVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Version", (Object)null, (Object)null));
      this.setModified(true);
   }

   public CdiDescriptorBean getCdiDescriptor() {
      return this.beanTreeNode.getCdiDescriptor();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
