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

public class SizeParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SizeParamsBean beanTreeNode;

   public SizeParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SizeParamsBean)btn;
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

   public int getShrinkPeriodMinutes() {
      return this.beanTreeNode.getShrinkPeriodMinutes();
   }

   public void setShrinkPeriodMinutes(int value) {
      this.beanTreeNode.setShrinkPeriodMinutes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ShrinkPeriodMinutes", (Object)null, (Object)null));
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
}
