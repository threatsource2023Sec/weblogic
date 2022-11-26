package kodo.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class DetachOptionsLoadedBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DetachOptionsLoadedBean beanTreeNode;

   public DetachOptionsLoadedBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DetachOptionsLoadedBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
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

   public boolean getDetachedStateManager() {
      return this.beanTreeNode.getDetachedStateManager();
   }

   public void setDetachedStateManager(boolean value) {
      this.beanTreeNode.setDetachedStateManager(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DetachedStateManager", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getDetachedStateTransient() {
      return this.beanTreeNode.getDetachedStateTransient();
   }

   public void setDetachedStateTransient(boolean value) {
      this.beanTreeNode.setDetachedStateTransient(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DetachedStateTransient", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getAccessUnloaded() {
      return this.beanTreeNode.getAccessUnloaded();
   }

   public void setAccessUnloaded(boolean value) {
      this.beanTreeNode.setAccessUnloaded(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AccessUnloaded", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getDetachedStateField() {
      return this.beanTreeNode.getDetachedStateField();
   }

   public void setDetachedStateField(boolean value) {
      this.beanTreeNode.setDetachedStateField(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DetachedStateField", (Object)null, (Object)null));
      this.setModified(true);
   }
}
