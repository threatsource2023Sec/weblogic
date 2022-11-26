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

public class WeblogicConnectorBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicConnectorBean beanTreeNode;
   private OutboundResourceAdapterBeanDConfig outboundResourceAdapterDConfig;

   public WeblogicConnectorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicConnectorBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
      GeneralConfigTemplate.configureAdminObj(this);
   }

   public WeblogicConnectorBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (WeblogicConnectorBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public WeblogicConnectorBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(WeblogicConnectorBean.class);
         this.beanTreeNode = (WeblogicConnectorBean)this.beanTree;
         this.customInit();
         GeneralConfigTemplate.configureAdminObj(this);
      } catch (ConfigurationException var6) {
         throw new InvalidModuleException(var6.toString());
      }
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.applyNamespace("connector/resourceadapter/outbound-resourceadapter"));
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
         OutboundResourceAdapterBean btn = this.beanTreeNode.getOutboundResourceAdapter();
         this.outboundResourceAdapterDConfig = new OutboundResourceAdapterBeanDConfig(ddb, (DescriptorBean)btn, parent);
         retBean = this.outboundResourceAdapterDConfig;
         if (!retBean.hasCustomInit()) {
            retBean.setParentPropertyName("OutboundResourceAdapter");
         }
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
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("JNDIName: ");
      sb.append(this.beanTreeNode.getJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getNativeLibdir() {
      return this.beanTreeNode.getNativeLibdir();
   }

   public void setNativeLibdir(String value) {
      this.beanTreeNode.setNativeLibdir(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NativeLibdir", (Object)null, (Object)null));
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

   public boolean isEnableAccessOutsideApp() {
      return this.beanTreeNode.isEnableAccessOutsideApp();
   }

   public void setEnableAccessOutsideApp(boolean value) {
      this.beanTreeNode.setEnableAccessOutsideApp(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableAccessOutsideApp", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEnableGlobalAccessToClasses() {
      return this.beanTreeNode.isEnableGlobalAccessToClasses();
   }

   public void setEnableGlobalAccessToClasses(boolean value) {
      this.beanTreeNode.setEnableGlobalAccessToClasses(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableGlobalAccessToClasses", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDeployAsAWhole() {
      return this.beanTreeNode.isDeployAsAWhole();
   }

   public void setDeployAsAWhole(boolean value) {
      this.beanTreeNode.setDeployAsAWhole(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DeployAsAWhole", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WorkManagerBean getWorkManager() {
      return this.beanTreeNode.getWorkManager();
   }

   public ConnectorWorkManagerBean getConnectorWorkManager() {
      return this.beanTreeNode.getConnectorWorkManager();
   }

   public boolean isConnectorWorkManagerSet() {
      return this.beanTreeNode.isConnectorWorkManagerSet();
   }

   public ResourceAdapterSecurityBean getSecurity() {
      return this.beanTreeNode.getSecurity();
   }

   public boolean isSecuritySet() {
      return this.beanTreeNode.isSecuritySet();
   }

   public ConfigPropertiesBean getProperties() {
      return this.beanTreeNode.getProperties();
   }

   public boolean isPropertiesSet() {
      return this.beanTreeNode.isPropertiesSet();
   }

   public AdminObjectsBean getAdminObjects() {
      return this.beanTreeNode.getAdminObjects();
   }

   public boolean isAdminObjectsSet() {
      return this.beanTreeNode.isAdminObjectsSet();
   }

   public OutboundResourceAdapterBeanDConfig getOutboundResourceAdapter() {
      return this.outboundResourceAdapterDConfig;
   }

   public void setOutboundResourceAdapter(OutboundResourceAdapterBeanDConfig value) {
      this.outboundResourceAdapterDConfig = value;
      this.firePropertyChange(new PropertyChangeEvent(this, "OutboundResourceAdapter", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isOutboundResourceAdapterSet() {
      return this.beanTreeNode.isOutboundResourceAdapterSet();
   }

   public String getVersion() {
      return this.beanTreeNode.getVersion();
   }

   public void setVersion(String value) {
      this.beanTreeNode.setVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Version", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
