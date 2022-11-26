package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class ConnectionPoolParamsBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ConnectionPoolParamsBean beanTreeNode;

   public ConnectionPoolParamsBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ConnectionPoolParamsBean)btn;
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

   public int getInitialCapacity() {
      return this.beanTreeNode.getInitialCapacity();
   }

   public void setInitialCapacity(int value) {
      this.beanTreeNode.setInitialCapacity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitialCapacity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxCapacity() {
      return this.beanTreeNode.getMaxCapacity();
   }

   public void setMaxCapacity(int value) {
      this.beanTreeNode.setMaxCapacity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxCapacity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCapacityIncrement() {
      return this.beanTreeNode.getCapacityIncrement();
   }

   public void setCapacityIncrement(int value) {
      this.beanTreeNode.setCapacityIncrement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CapacityIncrement", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isShrinkingEnabled() {
      return this.beanTreeNode.isShrinkingEnabled();
   }

   public void setShrinkingEnabled(boolean value) {
      this.beanTreeNode.setShrinkingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ShrinkingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getShrinkFrequencySeconds() {
      return this.beanTreeNode.getShrinkFrequencySeconds();
   }

   public void setShrinkFrequencySeconds(int value) {
      this.beanTreeNode.setShrinkFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ShrinkFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getHighestNumWaiters() {
      return this.beanTreeNode.getHighestNumWaiters();
   }

   public void setHighestNumWaiters(int value) {
      this.beanTreeNode.setHighestNumWaiters(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HighestNumWaiters", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getHighestNumUnavailable() {
      return this.beanTreeNode.getHighestNumUnavailable();
   }

   public void setHighestNumUnavailable(int value) {
      this.beanTreeNode.setHighestNumUnavailable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HighestNumUnavailable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this.beanTreeNode.getConnectionCreationRetryFrequencySeconds();
   }

   public void setConnectionCreationRetryFrequencySeconds(int value) {
      this.beanTreeNode.setConnectionCreationRetryFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionCreationRetryFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this.beanTreeNode.getConnectionReserveTimeoutSeconds();
   }

   public void setConnectionReserveTimeoutSeconds(int value) {
      this.beanTreeNode.setConnectionReserveTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionReserveTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getTestFrequencySeconds() {
      return this.beanTreeNode.getTestFrequencySeconds();
   }

   public void setTestFrequencySeconds(int value) {
      this.beanTreeNode.setTestFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTestConnectionsOnCreate() {
      return this.beanTreeNode.isTestConnectionsOnCreate();
   }

   public void setTestConnectionsOnCreate(boolean value) {
      this.beanTreeNode.setTestConnectionsOnCreate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestConnectionsOnCreate", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTestConnectionsOnRelease() {
      return this.beanTreeNode.isTestConnectionsOnRelease();
   }

   public void setTestConnectionsOnRelease(boolean value) {
      this.beanTreeNode.setTestConnectionsOnRelease(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestConnectionsOnRelease", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTestConnectionsOnReserve() {
      return this.beanTreeNode.isTestConnectionsOnReserve();
   }

   public void setTestConnectionsOnReserve(boolean value) {
      this.beanTreeNode.setTestConnectionsOnReserve(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestConnectionsOnReserve", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getProfileHarvestFrequencySeconds() {
      return this.beanTreeNode.getProfileHarvestFrequencySeconds();
   }

   public void setProfileHarvestFrequencySeconds(int value) {
      this.beanTreeNode.setProfileHarvestFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProfileHarvestFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIgnoreInUseConnectionsEnabled() {
      return this.beanTreeNode.isIgnoreInUseConnectionsEnabled();
   }

   public void setIgnoreInUseConnectionsEnabled(boolean value) {
      this.beanTreeNode.setIgnoreInUseConnectionsEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IgnoreInUseConnectionsEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }
}
