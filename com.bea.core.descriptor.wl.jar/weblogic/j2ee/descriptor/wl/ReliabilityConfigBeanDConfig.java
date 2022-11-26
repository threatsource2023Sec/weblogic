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

public class ReliabilityConfigBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ReliabilityConfigBean beanTreeNode;

   public ReliabilityConfigBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ReliabilityConfigBean)btn;
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

   public boolean isCustomized() {
      return this.beanTreeNode.isCustomized();
   }

   public void setCustomized(boolean value) {
      this.beanTreeNode.setCustomized(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Customized", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getInactivityTimeout() {
      return this.beanTreeNode.getInactivityTimeout();
   }

   public void setInactivityTimeout(String value) {
      this.beanTreeNode.setInactivityTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InactivityTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBaseRetransmissionInterval() {
      return this.beanTreeNode.getBaseRetransmissionInterval();
   }

   public void setBaseRetransmissionInterval(String value) {
      this.beanTreeNode.setBaseRetransmissionInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BaseRetransmissionInterval", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRetransmissionExponentialBackoff() {
      return this.beanTreeNode.getRetransmissionExponentialBackoff();
   }

   public void setRetransmissionExponentialBackoff(boolean value) {
      this.beanTreeNode.setRetransmissionExponentialBackoff(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetransmissionExponentialBackoff", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getNonBufferedSource() {
      return this.beanTreeNode.getNonBufferedSource();
   }

   public void setNonBufferedSource(boolean value) {
      this.beanTreeNode.setNonBufferedSource(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NonBufferedSource", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAcknowledgementInterval() {
      return this.beanTreeNode.getAcknowledgementInterval();
   }

   public void setAcknowledgementInterval(String value) {
      this.beanTreeNode.setAcknowledgementInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AcknowledgementInterval", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSequenceExpiration() {
      return this.beanTreeNode.getSequenceExpiration();
   }

   public void setSequenceExpiration(String value) {
      this.beanTreeNode.setSequenceExpiration(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SequenceExpiration", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getBufferRetryCount() {
      return this.beanTreeNode.getBufferRetryCount();
   }

   public void setBufferRetryCount(int value) {
      this.beanTreeNode.setBufferRetryCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BufferRetryCount", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBufferRetryDelay() {
      return this.beanTreeNode.getBufferRetryDelay();
   }

   public void setBufferRetryDelay(String value) {
      this.beanTreeNode.setBufferRetryDelay(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BufferRetryDelay", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getNonBufferedDestination() {
      return this.beanTreeNode.getNonBufferedDestination();
   }

   public void setNonBufferedDestination(boolean value) {
      this.beanTreeNode.setNonBufferedDestination(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NonBufferedDestination", (Object)null, (Object)null));
      this.setModified(true);
   }
}
