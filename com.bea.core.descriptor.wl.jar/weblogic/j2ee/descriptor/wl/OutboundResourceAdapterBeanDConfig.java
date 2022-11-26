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

public class OutboundResourceAdapterBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private OutboundResourceAdapterBean beanTreeNode;
   private List connectionDefinitionGroupsDConfig = new ArrayList();

   public OutboundResourceAdapterBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (OutboundResourceAdapterBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("connection-definition/connectionfactory-interface")));
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
         ConnectionDefinitionBean btn = null;
         ConnectionDefinitionBean[] list = this.beanTreeNode.getConnectionDefinitionGroups();
         if (list == null) {
            this.beanTreeNode.createConnectionDefinitionGroup();
            list = this.beanTreeNode.getConnectionDefinitionGroups();
         }

         String keyName = this.lastElementOf(this.applyNamespace("connection-definition/connectionfactory-interface"));
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

            btn = this.beanTreeNode.createConnectionDefinitionGroup();
            newDCB = true;
         }

         retBean = new ConnectionDefinitionBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((ConnectionDefinitionBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!retBean.hasCustomInit()) {
            retBean.setParentPropertyName("ConnectionDefinitionGroups");
         }

         if (debug) {
            Debug.say("dcb dump: " + retBean.toString());
         }

         this.connectionDefinitionGroupsDConfig.add(retBean);
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
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public ConnectionDefinitionPropertiesBean getDefaultConnectionProperties() {
      return this.beanTreeNode.getDefaultConnectionProperties();
   }

   public boolean isDefaultConnectionPropertiesSet() {
      return this.beanTreeNode.isDefaultConnectionPropertiesSet();
   }

   public ConnectionDefinitionBeanDConfig[] getConnectionDefinitionGroups() {
      return (ConnectionDefinitionBeanDConfig[])((ConnectionDefinitionBeanDConfig[])this.connectionDefinitionGroupsDConfig.toArray(new ConnectionDefinitionBeanDConfig[0]));
   }

   void addConnectionDefinitionBean(ConnectionDefinitionBeanDConfig value) {
      this.addToList(this.connectionDefinitionGroupsDConfig, "ConnectionDefinitionBean", value);
   }

   void removeConnectionDefinitionBean(ConnectionDefinitionBeanDConfig value) {
      this.removeFromList(this.connectionDefinitionGroupsDConfig, "ConnectionDefinitionBean", value);
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
