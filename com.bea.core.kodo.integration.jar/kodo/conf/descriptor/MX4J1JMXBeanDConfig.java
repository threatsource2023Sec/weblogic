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

public class MX4J1JMXBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MX4J1JMXBean beanTreeNode;

   public MX4J1JMXBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MX4J1JMXBean)btn;
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

   public String getHost() {
      return this.beanTreeNode.getHost();
   }

   public void setHost(String value) {
      this.beanTreeNode.setHost(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Host", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPort() {
      return this.beanTreeNode.getPort();
   }

   public void setPort(String value) {
      this.beanTreeNode.setPort(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Port", (Object)null, (Object)null));
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
}
