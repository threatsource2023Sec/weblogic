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

public class ClusterRemoteCommitProviderBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ClusterRemoteCommitProviderBean beanTreeNode;

   public ClusterRemoteCommitProviderBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ClusterRemoteCommitProviderBean)btn;
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

   public int getBufferSize() {
      return this.beanTreeNode.getBufferSize();
   }

   public void setBufferSize(int value) {
      this.beanTreeNode.setBufferSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BufferSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCacheTopics() {
      return this.beanTreeNode.getCacheTopics();
   }

   public void setCacheTopics(String value) {
      this.beanTreeNode.setCacheTopics(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheTopics", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRecoverAction() {
      return this.beanTreeNode.getRecoverAction();
   }

   public void setRecoverAction(String value) {
      this.beanTreeNode.setRecoverAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RecoverAction", (Object)null, (Object)null));
      this.setModified(true);
   }
}
