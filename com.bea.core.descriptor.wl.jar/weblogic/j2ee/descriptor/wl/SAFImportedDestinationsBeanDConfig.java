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

public class SAFImportedDestinationsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SAFImportedDestinationsBean beanTreeNode;

   public SAFImportedDestinationsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SAFImportedDestinationsBean)btn;
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
      sb.append("UnitOfOrderRouting: ");
      sb.append(this.beanTreeNode.getUnitOfOrderRouting());
      sb.append("\n");
      sb.append("ExactlyOnceLoadBalancingPolicy: ");
      sb.append(this.beanTreeNode.getExactlyOnceLoadBalancingPolicy());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public SAFQueueBean[] getSAFQueues() {
      return this.beanTreeNode.getSAFQueues();
   }

   public SAFTopicBean[] getSAFTopics() {
      return this.beanTreeNode.getSAFTopics();
   }

   public String getJNDIPrefix() {
      return this.beanTreeNode.getJNDIPrefix();
   }

   public void setJNDIPrefix(String value) {
      this.beanTreeNode.setJNDIPrefix(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JNDIPrefix", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SAFRemoteContextBean getSAFRemoteContext() {
      return this.beanTreeNode.getSAFRemoteContext();
   }

   public void setSAFRemoteContext(SAFRemoteContextBean value) {
      this.beanTreeNode.setSAFRemoteContext(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SAFRemoteContext", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SAFErrorHandlingBean getSAFErrorHandling() {
      return this.beanTreeNode.getSAFErrorHandling();
   }

   public void setSAFErrorHandling(SAFErrorHandlingBean value) {
      this.beanTreeNode.setSAFErrorHandling(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SAFErrorHandling", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getTimeToLiveDefault() {
      return this.beanTreeNode.getTimeToLiveDefault();
   }

   public void setTimeToLiveDefault(long value) {
      this.beanTreeNode.setTimeToLiveDefault(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeToLiveDefault", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseSAFTimeToLiveDefault() {
      return this.beanTreeNode.isUseSAFTimeToLiveDefault();
   }

   public void setUseSAFTimeToLiveDefault(boolean value) {
      this.beanTreeNode.setUseSAFTimeToLiveDefault(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSAFTimeToLiveDefault", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getUnitOfOrderRouting() {
      return this.beanTreeNode.getUnitOfOrderRouting();
   }

   public void setUnitOfOrderRouting(String value) {
      this.beanTreeNode.setUnitOfOrderRouting(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UnitOfOrderRouting", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this.beanTreeNode.getMessageLoggingParams();
   }

   public String getExactlyOnceLoadBalancingPolicy() {
      return this.beanTreeNode.getExactlyOnceLoadBalancingPolicy();
   }

   public void setExactlyOnceLoadBalancingPolicy(String value) {
      this.beanTreeNode.setExactlyOnceLoadBalancingPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ExactlyOnceLoadBalancingPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }
}
