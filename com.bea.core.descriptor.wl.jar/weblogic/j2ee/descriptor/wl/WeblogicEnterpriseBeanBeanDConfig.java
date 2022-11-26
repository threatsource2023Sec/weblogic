package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.config.templates.GeneralConfigTemplate;

public class WeblogicEnterpriseBeanBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicEnterpriseBeanBean beanTreeNode;
   private List resourceDescriptionsDConfig = new ArrayList();

   public WeblogicEnterpriseBeanBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicEnterpriseBeanBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
      GeneralConfigTemplate.configureEntityDescriptor(this);
      GeneralConfigTemplate.configureMessageDrivenDescriptor(this);
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("resource-ref/res-ref-name")));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      if (debug) {
         Debug.say("Creating child DCB for <" + ddb.getXpath() + ">");
      }

      boolean newDCB = false;
      BasicDConfigBean retBean = null;
      String xpath = ddb.getXpath();
      int xpathIndex = 0;
      if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         ResourceDescriptionBean btn = null;
         ResourceDescriptionBean[] list = this.beanTreeNode.getResourceDescriptions();
         if (list == null) {
            this.beanTreeNode.createResourceDescription();
            list = this.beanTreeNode.getResourceDescriptions();
         }

         String keyName = this.lastElementOf(this.applyNamespace("resource-ref/res-ref-name"));
         this.setKeyName(keyName);
         String key = this.getDDKey(ddb, keyName);
         if (debug) {
            Debug.say("Using keyName: " + keyName + ", key: " + key);
         }

         for(int i = 0; i < list.length; ++i) {
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
         if (!retBean.hasCustomInit()) {
            retBean.setParentPropertyName("ResourceDescriptions");
         }

         if (debug) {
            Debug.say("dcb dump: " + retBean.toString());
         }

         this.resourceDescriptionsDConfig.add(retBean);
      } else if (debug) {
         Debug.say("Ignoring " + ddb.getXpath());

         for(int i = 0; i < this.xpaths.length; ++i) {
            Debug.say("xpaths[" + i + "]=" + this.xpaths[i]);
         }
      }

      if (retBean != null) {
         this.addDConfigBean(retBean);
         if (newDCB) {
            retBean.setModified(true);

            Object p;
            for(p = retBean; ((BasicDConfigBean)p).getParent() != null; p = ((BasicDConfigBean)p).getParent()) {
            }

            ((BasicDConfigBeanRoot)p).registerAsListener(retBean.getDescriptorBean());
         }

         this.processDCB(retBean, newDCB);
      }

      return retBean;
   }

   public String keyPropertyValue() {
      return this.getEjbName();
   }

   public void initKeyPropertyValue(String value) {
      this.setEjbName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EjbName: ");
      sb.append(this.beanTreeNode.getEjbName());
      sb.append("\n");
      sb.append("JNDIName: ");
      sb.append(this.beanTreeNode.getJNDIName());
      sb.append("\n");
      sb.append("LocalJNDIName: ");
      sb.append(this.beanTreeNode.getLocalJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getEjbName() {
      return this.beanTreeNode.getEjbName();
   }

   public void setEjbName(String value) {
      this.beanTreeNode.setEjbName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EjbName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public EntityDescriptorBean getEntityDescriptor() {
      return this.beanTreeNode.getEntityDescriptor();
   }

   public boolean isEntityDescriptorSet() {
      return this.beanTreeNode.isEntityDescriptorSet();
   }

   public StatelessSessionDescriptorBean getStatelessSessionDescriptor() {
      return this.beanTreeNode.getStatelessSessionDescriptor();
   }

   public boolean isStatelessSessionDescriptorSet() {
      return this.beanTreeNode.isStatelessSessionDescriptorSet();
   }

   public StatefulSessionDescriptorBean getStatefulSessionDescriptor() {
      return this.beanTreeNode.getStatefulSessionDescriptor();
   }

   public boolean isStatefulSessionDescriptorSet() {
      return this.beanTreeNode.isStatefulSessionDescriptorSet();
   }

   public SingletonSessionDescriptorBean getSingletonSessionDescriptor() {
      return this.beanTreeNode.getSingletonSessionDescriptor();
   }

   public boolean isSingletonSessionDescriptorSet() {
      return this.beanTreeNode.isSingletonSessionDescriptorSet();
   }

   public MessageDrivenDescriptorBean getMessageDrivenDescriptor() {
      return this.beanTreeNode.getMessageDrivenDescriptor();
   }

   public boolean isMessageDrivenDescriptorSet() {
      return this.beanTreeNode.isMessageDrivenDescriptorSet();
   }

   public TransactionDescriptorBean getTransactionDescriptor() {
      return this.beanTreeNode.getTransactionDescriptor();
   }

   public boolean isTransactionDescriptorSet() {
      return this.beanTreeNode.isTransactionDescriptorSet();
   }

   public IiopSecurityDescriptorBean getIiopSecurityDescriptor() {
      return this.beanTreeNode.getIiopSecurityDescriptor();
   }

   public boolean isIiopSecurityDescriptorSet() {
      return this.beanTreeNode.isIiopSecurityDescriptorSet();
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

   public EjbReferenceDescriptionBean[] getEjbReferenceDescriptions() {
      return this.beanTreeNode.getEjbReferenceDescriptions();
   }

   public ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions() {
      return this.beanTreeNode.getServiceReferenceDescriptions();
   }

   public boolean isEnableCallByReference() {
      return this.beanTreeNode.isEnableCallByReference();
   }

   public void setEnableCallByReference(boolean value) {
      this.beanTreeNode.setEnableCallByReference(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableCallByReference", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNetworkAccessPoint() {
      return this.beanTreeNode.getNetworkAccessPoint();
   }

   public void setNetworkAccessPoint(String value) {
      this.beanTreeNode.setNetworkAccessPoint(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NetworkAccessPoint", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isClientsOnSameServer() {
      return this.beanTreeNode.isClientsOnSameServer();
   }

   public void setClientsOnSameServer(boolean value) {
      this.beanTreeNode.setClientsOnSameServer(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClientsOnSameServer", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRunAsPrincipalName() {
      return this.beanTreeNode.getRunAsPrincipalName();
   }

   public void setRunAsPrincipalName(String value) {
      this.beanTreeNode.setRunAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RunAsPrincipalName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCreateAsPrincipalName() {
      return this.beanTreeNode.getCreateAsPrincipalName();
   }

   public void setCreateAsPrincipalName(String value) {
      this.beanTreeNode.setCreateAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CreateAsPrincipalName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRemoveAsPrincipalName() {
      return this.beanTreeNode.getRemoveAsPrincipalName();
   }

   public void setRemoveAsPrincipalName(String value) {
      this.beanTreeNode.setRemoveAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RemoveAsPrincipalName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPassivateAsPrincipalName() {
      return this.beanTreeNode.getPassivateAsPrincipalName();
   }

   public void setPassivateAsPrincipalName(String value) {
      this.beanTreeNode.setPassivateAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PassivateAsPrincipalName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJNDIName() {
      return this.beanTreeNode.getJNDIName();
   }

   public void setJNDIName(String value) {
      this.beanTreeNode.setJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLocalJNDIName() {
      return this.beanTreeNode.getLocalJNDIName();
   }

   public void setLocalJNDIName(String value) {
      this.beanTreeNode.setLocalJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LocalJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDispatchPolicy() {
      return this.beanTreeNode.getDispatchPolicy();
   }

   public void setDispatchPolicy(String value) {
      this.beanTreeNode.setDispatchPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DispatchPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRemoteClientTimeout() {
      return this.beanTreeNode.getRemoteClientTimeout();
   }

   public void setRemoteClientTimeout(int value) {
      this.beanTreeNode.setRemoteClientTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RemoteClientTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isStickToFirstServer() {
      return this.beanTreeNode.isStickToFirstServer();
   }

   public void setStickToFirstServer(boolean value) {
      this.beanTreeNode.setStickToFirstServer(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StickToFirstServer", (Object)null, (Object)null));
      this.setModified(true);
   }

   public JndiBindingBean[] getJndiBinding() {
      return this.beanTreeNode.getJndiBinding();
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
