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

public class LocalJMXBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LocalJMXBean beanTreeNode;

   public LocalJMXBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LocalJMXBean)btn;
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

   public String getMBeanServerStrategy() {
      return this.beanTreeNode.getMBeanServerStrategy();
   }

   public void setMBeanServerStrategy(String value) {
      this.beanTreeNode.setMBeanServerStrategy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MBeanServerStrategy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getEnableLogMBean() {
      return this.beanTreeNode.getEnableLogMBean();
   }

   public void setEnableLogMBean(boolean value) {
      this.beanTreeNode.setEnableLogMBean(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableLogMBean", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getEnableRuntimeMBean() {
      return this.beanTreeNode.getEnableRuntimeMBean();
   }

   public void setEnableRuntimeMBean(boolean value) {
      this.beanTreeNode.setEnableRuntimeMBean(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableRuntimeMBean", (Object)null, (Object)null));
      this.setModified(true);
   }
}
