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

public class WeblogicWebAppBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicWebAppBean beanTreeNode;
   private List resourceDescriptionsDConfig = new ArrayList();
   private List ejbReferenceDescriptionsDConfig = new ArrayList();
   private List messageDestinationDescriptorsDConfig = new ArrayList();
   private List servletDescriptorsDConfig = new ArrayList();

   public WeblogicWebAppBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicWebAppBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public WeblogicWebAppBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (WeblogicWebAppBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public WeblogicWebAppBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(WeblogicWebAppBean.class);
         this.beanTreeNode = (WeblogicWebAppBean)this.beanTree;
         this.customInit();
      } catch (ConfigurationException var6) {
         throw new InvalidModuleException(var6.toString());
      }
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("web-app/resource-ref/res-ref-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("web-app/ejb-ref/ejb-ref-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("web-app/message-destination/message-destination-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("web-app/servlet/servlet-name")));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      if (!GeneralConfigTemplate.requireEjbRefDConfig(ddb, parent)) {
         return null;
      } else {
         if (debug) {
            Debug.say("Creating child DCB for <" + ddb.getXpath() + ">");
         }

         boolean newDCB = false;
         BasicDConfigBean retBean = null;
         String xpath = ddb.getXpath();
         int xpathIndex = 0;
         String key;
         String keyName;
         int i;
         if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
            ResourceDescriptionBean btn = null;
            ResourceDescriptionBean[] list = this.beanTreeNode.getResourceDescriptions();
            if (list == null) {
               this.beanTreeNode.createResourceDescription();
               list = this.beanTreeNode.getResourceDescriptions();
            }

            keyName = this.lastElementOf(this.applyNamespace("web-app/resource-ref/res-ref-name"));
            this.setKeyName(keyName);
            key = this.getDDKey(ddb, keyName);
            if (debug) {
               Debug.say("Using keyName: " + keyName + ", key: " + key);
            }

            for(i = 0; i < list.length; ++i) {
               btn = list[i];
               if (this.isMatch((DescriptorBean)btn, ddb, key)) {
                  break;
               }

               btn = null;
            }

            if (btn == null) {
               if (debug) {
                  Debug.say("creating new dcb element");
               }

               btn = this.beanTreeNode.createResourceDescription();
               newDCB = true;
            }

            retBean = new ResourceDescriptionBeanDConfig(ddb, (DescriptorBean)btn, parent);
            ((ResourceDescriptionBeanDConfig)retBean).initKeyPropertyValue(key);
            if (!((BasicDConfigBean)retBean).hasCustomInit()) {
               ((BasicDConfigBean)retBean).setParentPropertyName("ResourceDescriptions");
            }

            if (debug) {
               Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
            }

            this.resourceDescriptionsDConfig.add(retBean);
         } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
            EjbReferenceDescriptionBean btn = null;
            EjbReferenceDescriptionBean[] list = this.beanTreeNode.getEjbReferenceDescriptions();
            if (list == null) {
               this.beanTreeNode.createEjbReferenceDescription();
               list = this.beanTreeNode.getEjbReferenceDescriptions();
            }

            keyName = this.lastElementOf(this.applyNamespace("web-app/ejb-ref/ejb-ref-name"));
            this.setKeyName(keyName);
            key = this.getDDKey(ddb, keyName);
            if (debug) {
               Debug.say("Using keyName: " + keyName + ", key: " + key);
            }

            for(i = 0; i < list.length; ++i) {
               btn = list[i];
               if (this.isMatch((DescriptorBean)btn, ddb, key)) {
                  break;
               }

               btn = null;
            }

            if (btn == null) {
               if (debug) {
                  Debug.say("creating new dcb element");
               }

               btn = this.beanTreeNode.createEjbReferenceDescription();
               newDCB = true;
            }

            retBean = new EjbReferenceDescriptionBeanDConfig(ddb, (DescriptorBean)btn, parent);
            ((EjbReferenceDescriptionBeanDConfig)retBean).initKeyPropertyValue(key);
            if (!((BasicDConfigBean)retBean).hasCustomInit()) {
               ((BasicDConfigBean)retBean).setParentPropertyName("EjbReferenceDescriptions");
            }

            if (debug) {
               Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
            }

            this.ejbReferenceDescriptionsDConfig.add(retBean);
         } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
            MessageDestinationDescriptorBean btn = null;
            MessageDestinationDescriptorBean[] list = this.beanTreeNode.getMessageDestinationDescriptors();
            if (list == null) {
               this.beanTreeNode.createMessageDestinationDescriptor();
               list = this.beanTreeNode.getMessageDestinationDescriptors();
            }

            keyName = this.lastElementOf(this.applyNamespace("web-app/message-destination/message-destination-name"));
            this.setKeyName(keyName);
            key = this.getDDKey(ddb, keyName);
            if (debug) {
               Debug.say("Using keyName: " + keyName + ", key: " + key);
            }

            for(i = 0; i < list.length; ++i) {
               btn = list[i];
               if (this.isMatch((DescriptorBean)btn, ddb, key)) {
                  break;
               }

               btn = null;
            }

            if (btn == null) {
               if (debug) {
                  Debug.say("creating new dcb element");
               }

               btn = this.beanTreeNode.createMessageDestinationDescriptor();
               newDCB = true;
            }

            retBean = new MessageDestinationDescriptorBeanDConfig(ddb, (DescriptorBean)btn, parent);
            ((MessageDestinationDescriptorBeanDConfig)retBean).initKeyPropertyValue(key);
            if (!((BasicDConfigBean)retBean).hasCustomInit()) {
               ((BasicDConfigBean)retBean).setParentPropertyName("MessageDestinationDescriptors");
            }

            if (debug) {
               Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
            }

            this.messageDestinationDescriptorsDConfig.add(retBean);
         } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
            ServletDescriptorBean btn = null;
            ServletDescriptorBean[] list = this.beanTreeNode.getServletDescriptors();
            if (list == null) {
               this.beanTreeNode.createServletDescriptor();
               list = this.beanTreeNode.getServletDescriptors();
            }

            keyName = this.lastElementOf(this.applyNamespace("web-app/servlet/servlet-name"));
            this.setKeyName(keyName);
            key = this.getDDKey(ddb, keyName);
            if (debug) {
               Debug.say("Using keyName: " + keyName + ", key: " + key);
            }

            for(i = 0; i < list.length; ++i) {
               btn = list[i];
               if (this.isMatch((DescriptorBean)btn, ddb, key)) {
                  break;
               }

               btn = null;
            }

            if (btn == null) {
               if (debug) {
                  Debug.say("creating new dcb element");
               }

               btn = this.beanTreeNode.createServletDescriptor();
               newDCB = true;
            }

            retBean = new ServletDescriptorBeanDConfig(ddb, (DescriptorBean)btn, parent);
            ((ServletDescriptorBeanDConfig)retBean).initKeyPropertyValue(key);
            if (!((BasicDConfigBean)retBean).hasCustomInit()) {
               ((BasicDConfigBean)retBean).setParentPropertyName("ServletDescriptors");
            }

            if (debug) {
               Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
            }

            this.servletDescriptorsDConfig.add(retBean);
         } else if (debug) {
            Debug.say("Ignoring " + ddb.getXpath());

            for(int i = 0; i < this.xpaths.length; ++i) {
               Debug.say("xpaths[" + i + "]=" + this.xpaths[i]);
            }
         }

         if (retBean != null) {
            this.addDConfigBean((DConfigBean)retBean);
            if (newDCB) {
               ((BasicDConfigBean)retBean).setModified(true);

               Object p;
               for(p = retBean; ((BasicDConfigBean)p).getParent() != null; p = ((BasicDConfigBean)p).getParent()) {
               }

               ((BasicDConfigBeanRoot)p).registerAsListener(((BasicDConfigBean)retBean).getDescriptorBean());
            }

            this.processDCB((BasicDConfigBean)retBean, newDCB);
         }

         return (DConfigBean)retBean;
      }
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

   public String[] getDescriptions() {
      return this.beanTreeNode.getDescriptions();
   }

   public void setDescriptions(String[] value) {
      this.beanTreeNode.setDescriptions(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Descriptions", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getWeblogicVersions() {
      return this.beanTreeNode.getWeblogicVersions();
   }

   public void setWeblogicVersions(String[] value) {
      this.beanTreeNode.setWeblogicVersions(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WeblogicVersions", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SecurityRoleAssignmentBean[] getSecurityRoleAssignments() {
      return this.beanTreeNode.getSecurityRoleAssignments();
   }

   public RunAsRoleAssignmentBean[] getRunAsRoleAssignments() {
      return this.beanTreeNode.getRunAsRoleAssignments();
   }

   public ResourceDescriptionBeanDConfig[] getResourceDescriptions() {
      return (ResourceDescriptionBeanDConfig[])((ResourceDescriptionBeanDConfig[])this.resourceDescriptionsDConfig.toArray(new ResourceDescriptionBeanDConfig[0]));
   }

   void addResourceDescriptionBean(ResourceDescriptionBeanDConfig value) {
      this.addToList(this.resourceDescriptionsDConfig, "ResourceDescriptionBean", value);
   }

   void removeResourceDescriptionBean(ResourceDescriptionBeanDConfig value) {
      this.removeFromList(this.resourceDescriptionsDConfig, "ResourceDescriptionBean", value);
   }

   public ResourceEnvDescriptionBean[] getResourceEnvDescriptions() {
      return this.beanTreeNode.getResourceEnvDescriptions();
   }

   public EjbReferenceDescriptionBeanDConfig[] getEjbReferenceDescriptions() {
      return (EjbReferenceDescriptionBeanDConfig[])((EjbReferenceDescriptionBeanDConfig[])this.ejbReferenceDescriptionsDConfig.toArray(new EjbReferenceDescriptionBeanDConfig[0]));
   }

   void addEjbReferenceDescriptionBean(EjbReferenceDescriptionBeanDConfig value) {
      this.addToList(this.ejbReferenceDescriptionsDConfig, "EjbReferenceDescriptionBean", value);
   }

   void removeEjbReferenceDescriptionBean(EjbReferenceDescriptionBeanDConfig value) {
      this.removeFromList(this.ejbReferenceDescriptionsDConfig, "EjbReferenceDescriptionBean", value);
   }

   public ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions() {
      return this.beanTreeNode.getServiceReferenceDescriptions();
   }

   public MessageDestinationDescriptorBeanDConfig[] getMessageDestinationDescriptors() {
      return (MessageDestinationDescriptorBeanDConfig[])((MessageDestinationDescriptorBeanDConfig[])this.messageDestinationDescriptorsDConfig.toArray(new MessageDestinationDescriptorBeanDConfig[0]));
   }

   void addMessageDestinationDescriptorBean(MessageDestinationDescriptorBeanDConfig value) {
      this.addToList(this.messageDestinationDescriptorsDConfig, "MessageDestinationDescriptorBean", value);
   }

   void removeMessageDestinationDescriptorBean(MessageDestinationDescriptorBeanDConfig value) {
      this.removeFromList(this.messageDestinationDescriptorsDConfig, "MessageDestinationDescriptorBean", value);
   }

   public SessionDescriptorBean[] getSessionDescriptors() {
      return this.beanTreeNode.getSessionDescriptors();
   }

   public AsyncDescriptorBean[] getAsyncDescriptors() {
      return this.beanTreeNode.getAsyncDescriptors();
   }

   public JspDescriptorBean[] getJspDescriptors() {
      return this.beanTreeNode.getJspDescriptors();
   }

   public ContainerDescriptorBean[] getContainerDescriptors() {
      return this.beanTreeNode.getContainerDescriptors();
   }

   public String[] getAuthFilters() {
      return this.beanTreeNode.getAuthFilters();
   }

   public void setAuthFilters(String[] value) {
      this.beanTreeNode.setAuthFilters(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AuthFilters", (Object)null, (Object)null));
      this.setModified(true);
   }

   public CharsetParamsBean[] getCharsetParams() {
      return this.beanTreeNode.getCharsetParams();
   }

   public VirtualDirectoryMappingBean[] getVirtualDirectoryMappings() {
      return this.beanTreeNode.getVirtualDirectoryMappings();
   }

   public String[] getUrlMatchMaps() {
      return this.beanTreeNode.getUrlMatchMaps();
   }

   public void setUrlMatchMaps(String[] value) {
      this.beanTreeNode.setUrlMatchMaps(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UrlMatchMaps", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SecurityPermissionBean[] getSecurityPermissions() {
      return this.beanTreeNode.getSecurityPermissions();
   }

   public String[] getContextRoots() {
      return this.beanTreeNode.getContextRoots();
   }

   public void setContextRoots(String[] value) {
      this.beanTreeNode.setContextRoots(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ContextRoots", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getWlDispatchPolicies() {
      return this.beanTreeNode.getWlDispatchPolicies();
   }

   public void setWlDispatchPolicies(String[] value) {
      this.beanTreeNode.setWlDispatchPolicies(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WlDispatchPolicies", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ServletDescriptorBeanDConfig[] getServletDescriptors() {
      return (ServletDescriptorBeanDConfig[])((ServletDescriptorBeanDConfig[])this.servletDescriptorsDConfig.toArray(new ServletDescriptorBeanDConfig[0]));
   }

   void addServletDescriptorBean(ServletDescriptorBeanDConfig value) {
      this.addToList(this.servletDescriptorsDConfig, "ServletDescriptorBean", value);
   }

   void removeServletDescriptorBean(ServletDescriptorBeanDConfig value) {
      this.removeFromList(this.servletDescriptorsDConfig, "ServletDescriptorBean", value);
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

   public String[] getComponentFactoryClassName() {
      return this.beanTreeNode.getComponentFactoryClassName();
   }

   public void setComponentFactoryClassName(String[] value) {
      this.beanTreeNode.setComponentFactoryClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ComponentFactoryClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public LoggingBean[] getLoggings() {
      return this.beanTreeNode.getLoggings();
   }

   public LibraryRefBean[] getLibraryRefs() {
      return this.beanTreeNode.getLibraryRefs();
   }

   public JASPICProviderBean getJASPICProvider() {
      return this.beanTreeNode.getJASPICProvider();
   }

   public FastSwapBean getFastSwap() {
      return this.beanTreeNode.getFastSwap();
   }

   public CoherenceClusterRefBean getCoherenceClusterRef() {
      return this.beanTreeNode.getCoherenceClusterRef();
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
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
