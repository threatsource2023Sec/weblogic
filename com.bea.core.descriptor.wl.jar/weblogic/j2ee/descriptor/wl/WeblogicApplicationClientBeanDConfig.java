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

public class WeblogicApplicationClientBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicApplicationClientBean beanTreeNode;
   private List resourceDescriptionsDConfig = new ArrayList();
   private List resourceEnvDescriptionsDConfig = new ArrayList();
   private List ejbReferenceDescriptionsDConfig = new ArrayList();

   public WeblogicApplicationClientBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicApplicationClientBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public WeblogicApplicationClientBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (WeblogicApplicationClientBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public WeblogicApplicationClientBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(WeblogicApplicationClientBean.class);
         this.beanTreeNode = (WeblogicApplicationClientBean)this.beanTree;
         this.customInit();
      } catch (ConfigurationException var6) {
         throw new InvalidModuleException(var6.toString());
      }
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("application-client/resource-ref/res-ref-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("application-client/resource-env-ref/resource-env-ref-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("application-client/ejb-ref/ejb-ref-name")));
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

            keyName = this.lastElementOf(this.applyNamespace("application-client/resource-ref/res-ref-name"));
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
            ResourceEnvDescriptionBean btn = null;
            ResourceEnvDescriptionBean[] list = this.beanTreeNode.getResourceEnvDescriptions();
            if (list == null) {
               this.beanTreeNode.createResourceEnvDescription();
               list = this.beanTreeNode.getResourceEnvDescriptions();
            }

            keyName = this.lastElementOf(this.applyNamespace("application-client/resource-env-ref/resource-env-ref-name"));
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

               btn = this.beanTreeNode.createResourceEnvDescription();
               newDCB = true;
            }

            retBean = new ResourceEnvDescriptionBeanDConfig(ddb, (DescriptorBean)btn, parent);
            ((ResourceEnvDescriptionBeanDConfig)retBean).initKeyPropertyValue(key);
            if (!((BasicDConfigBean)retBean).hasCustomInit()) {
               ((BasicDConfigBean)retBean).setParentPropertyName("ResourceEnvDescriptions");
            }

            if (debug) {
               Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
            }

            this.resourceEnvDescriptionsDConfig.add(retBean);
         } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
            EjbReferenceDescriptionBean btn = null;
            EjbReferenceDescriptionBean[] list = this.beanTreeNode.getEjbReferenceDescriptions();
            if (list == null) {
               this.beanTreeNode.createEjbReferenceDescription();
               list = this.beanTreeNode.getEjbReferenceDescriptions();
            }

            keyName = this.lastElementOf(this.applyNamespace("application-client/ejb-ref/ejb-ref-name"));
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

   public String getServerApplicationName() {
      return this.beanTreeNode.getServerApplicationName();
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

   public ResourceEnvDescriptionBeanDConfig[] getResourceEnvDescriptions() {
      return (ResourceEnvDescriptionBeanDConfig[])((ResourceEnvDescriptionBeanDConfig[])this.resourceEnvDescriptionsDConfig.toArray(new ResourceEnvDescriptionBeanDConfig[0]));
   }

   void addResourceEnvDescriptionBean(ResourceEnvDescriptionBeanDConfig value) {
      this.addToList(this.resourceEnvDescriptionsDConfig, "ResourceEnvDescriptionBean", value);
   }

   void removeResourceEnvDescriptionBean(ResourceEnvDescriptionBeanDConfig value) {
      this.removeFromList(this.resourceEnvDescriptionsDConfig, "ResourceEnvDescriptionBean", value);
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

   public MessageDestinationDescriptorBean[] getMessageDestinationDescriptors() {
      return this.beanTreeNode.getMessageDestinationDescriptors();
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

   public CdiDescriptorBean getCdiDescriptor() {
      return this.beanTreeNode.getCdiDescriptor();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
