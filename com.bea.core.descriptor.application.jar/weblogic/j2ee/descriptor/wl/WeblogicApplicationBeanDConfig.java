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
import weblogic.management.config.templates.GeneralConfigTemplate;

public class WeblogicApplicationBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicApplicationBean beanTreeNode;

   public WeblogicApplicationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicApplicationBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
      GeneralConfigTemplate.configureSecurity(this);
   }

   public WeblogicApplicationBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (WeblogicApplicationBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public WeblogicApplicationBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(WeblogicApplicationBean.class);
         this.beanTreeNode = (WeblogicApplicationBean)this.beanTree;
         this.customInit();
         GeneralConfigTemplate.configureSecurity(this);
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

   public EjbBean getEjb() {
      return this.beanTreeNode.getEjb();
   }

   public XmlBean getXml() {
      return this.beanTreeNode.getXml();
   }

   public JDBCConnectionPoolBean[] getJDBCConnectionPools() {
      return this.beanTreeNode.getJDBCConnectionPools();
   }

   public SecurityBean getSecurity() {
      return this.beanTreeNode.getSecurity();
   }

   public ApplicationParamBean[] getApplicationParams() {
      return this.beanTreeNode.getApplicationParams();
   }

   public ClassloaderStructureBean getClassloaderStructure() {
      return this.beanTreeNode.getClassloaderStructure();
   }

   public ListenerBean[] getListeners() {
      return this.beanTreeNode.getListeners();
   }

   public SingletonServiceBean[] getSingletonServices() {
      return this.beanTreeNode.getSingletonServices();
   }

   public StartupBean[] getStartups() {
      return this.beanTreeNode.getStartups();
   }

   public ShutdownBean[] getShutdowns() {
      return this.beanTreeNode.getShutdowns();
   }

   public WeblogicModuleBean[] getModules() {
      return this.beanTreeNode.getModules();
   }

   public LibraryRefBean[] getLibraryRefs() {
      return this.beanTreeNode.getLibraryRefs();
   }

   public FairShareRequestClassBean[] getFairShareRequests() {
      return this.beanTreeNode.getFairShareRequests();
   }

   public ResponseTimeRequestClassBean[] getResponseTimeRequests() {
      return this.beanTreeNode.getResponseTimeRequests();
   }

   public ContextRequestClassBean[] getContextRequests() {
      return this.beanTreeNode.getContextRequests();
   }

   public MaxThreadsConstraintBean[] getMaxThreadsConstraints() {
      return this.beanTreeNode.getMaxThreadsConstraints();
   }

   public MinThreadsConstraintBean[] getMinThreadsConstraints() {
      return this.beanTreeNode.getMinThreadsConstraints();
   }

   public CapacityBean[] getCapacities() {
      return this.beanTreeNode.getCapacities();
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

   public ApplicationAdminModeTriggerBean getApplicationAdminModeTrigger() {
      return this.beanTreeNode.getApplicationAdminModeTrigger();
   }

   public SessionDescriptorBean getSessionDescriptor() {
      return this.beanTreeNode.getSessionDescriptor();
   }

   public LibraryContextRootOverrideBean[] getLibraryContextRootOverrides() {
      return this.beanTreeNode.getLibraryContextRootOverrides();
   }

   public PreferApplicationPackagesBean getPreferApplicationPackages() {
      return this.beanTreeNode.getPreferApplicationPackages();
   }

   public PreferApplicationResourcesBean getPreferApplicationResources() {
      return this.beanTreeNode.getPreferApplicationResources();
   }

   public FastSwapBean getFastSwap() {
      return this.beanTreeNode.getFastSwap();
   }

   public CoherenceClusterRefBean getCoherenceClusterRef() {
      return this.beanTreeNode.getCoherenceClusterRef();
   }

   public ResourceDescriptionBean[] getResourceDescriptions() {
      return this.beanTreeNode.getResourceDescriptions();
   }

   public ResourceEnvDescriptionBean[] getResourceEnvDescriptions() {
      return this.beanTreeNode.getResourceEnvDescriptions();
   }

   public EjbReferenceDescriptionBean[] getEjbReferenceDescriptions() {
      return this.beanTreeNode.getEjbReferenceDescriptions();
   }

   public ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions() {
      return this.beanTreeNode.getServiceReferenceDescriptions();
   }

   public MessageDestinationDescriptorBean[] getMessageDestinationDescriptors() {
      return this.beanTreeNode.getMessageDestinationDescriptors();
   }

   public String getVersion() {
      return this.beanTreeNode.getVersion();
   }

   public void setVersion(String value) {
      this.beanTreeNode.setVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Version", (Object)null, (Object)null));
      this.setModified(true);
   }

   public OsgiFrameworkReferenceBean getOsgiFrameworkReference() {
      return this.beanTreeNode.getOsgiFrameworkReference();
   }

   public ClassLoadingBean getClassLoading() {
      return this.beanTreeNode.getClassLoading();
   }

   public String getReadyRegistration() {
      return this.beanTreeNode.getReadyRegistration();
   }

   public CdiDescriptorBean getCdiDescriptor() {
      return this.beanTreeNode.getCdiDescriptor();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
