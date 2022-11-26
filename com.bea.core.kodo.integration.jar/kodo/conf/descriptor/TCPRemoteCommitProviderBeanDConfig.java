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

public class TCPRemoteCommitProviderBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private TCPRemoteCommitProviderBean beanTreeNode;

   public TCPRemoteCommitProviderBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (TCPRemoteCommitProviderBean)btn;
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

   public int getMaxIdle() {
      return this.beanTreeNode.getMaxIdle();
   }

   public void setMaxIdle(int value) {
      this.beanTreeNode.setMaxIdle(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxIdle", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getNumBroadcastThreads() {
      return this.beanTreeNode.getNumBroadcastThreads();
   }

   public void setNumBroadcastThreads(int value) {
      this.beanTreeNode.setNumBroadcastThreads(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NumBroadcastThreads", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRecoveryTimeMillis() {
      return this.beanTreeNode.getRecoveryTimeMillis();
   }

   public void setRecoveryTimeMillis(int value) {
      this.beanTreeNode.setRecoveryTimeMillis(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RecoveryTimeMillis", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxActive() {
      return this.beanTreeNode.getMaxActive();
   }

   public void setMaxActive(int value) {
      this.beanTreeNode.setMaxActive(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxActive", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getPort() {
      return this.beanTreeNode.getPort();
   }

   public void setPort(int value) {
      this.beanTreeNode.setPort(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Port", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAddresses() {
      return this.beanTreeNode.getAddresses();
   }

   public void setAddresses(String value) {
      this.beanTreeNode.setAddresses(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Addresses", (Object)null, (Object)null));
      this.setModified(true);
   }
}
