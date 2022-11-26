package weblogic.j2ee.descriptor.wl;

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
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MailSessionBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;

public class PojoEnvironmentBeanDConfigBeanInfo extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private PojoEnvironmentBean beanTreeNode;

   public PojoEnvironmentBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PojoEnvironmentBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public PojoEnvironmentBeanDConfigBeanInfo(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (PojoEnvironmentBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public PojoEnvironmentBeanDConfigBeanInfo(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(PojoEnvironmentBean.class);
         this.beanTreeNode = (PojoEnvironmentBean)this.beanTree;
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

   public EjbLocalRefBean[] getEjbLocalRefs() {
      return this.beanTreeNode.getEjbLocalRefs();
   }

   public PersistenceContextRefBean[] getPersistenceContextRefs() {
      return this.beanTreeNode.getPersistenceContextRefs();
   }

   public EnvEntryBean[] getEnvEntries() {
      return this.beanTreeNode.getEnvEntries();
   }

   public EjbRefBean[] getEjbRefs() {
      return this.beanTreeNode.getEjbRefs();
   }

   public ServiceRefBean[] getServiceRefs() {
      return this.beanTreeNode.getServiceRefs();
   }

   public ResourceRefBean[] getResourceRefs() {
      return this.beanTreeNode.getResourceRefs();
   }

   public ResourceEnvRefBean[] getResourceEnvRefs() {
      return this.beanTreeNode.getResourceEnvRefs();
   }

   public MessageDestinationRefBean[] getMessageDestinationRefs() {
      return this.beanTreeNode.getMessageDestinationRefs();
   }

   public PersistenceUnitRefBean[] getPersistenceUnitRefs() {
      return this.beanTreeNode.getPersistenceUnitRefs();
   }

   public LifecycleCallbackBean[] getPostConstructs() {
      return this.beanTreeNode.getPostConstructs();
   }

   public LifecycleCallbackBean[] getPreDestroys() {
      return this.beanTreeNode.getPreDestroys();
   }

   public DataSourceBean[] getDataSources() {
      return this.beanTreeNode.getDataSources();
   }

   public JmsConnectionFactoryBean[] getJmsConnectionFactories() {
      return this.beanTreeNode.getJmsConnectionFactories();
   }

   public JmsDestinationBean[] getJmsDestinations() {
      return this.beanTreeNode.getJmsDestinations();
   }

   public MailSessionBean[] getMailSessions() {
      return this.beanTreeNode.getMailSessions();
   }

   public ConnectionFactoryResourceBean[] getConnectionFactories() {
      return this.beanTreeNode.getConnectionFactories();
   }

   public AdministeredObjectBean[] getAdministeredObjects() {
      return this.beanTreeNode.getAdministeredObjects();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
