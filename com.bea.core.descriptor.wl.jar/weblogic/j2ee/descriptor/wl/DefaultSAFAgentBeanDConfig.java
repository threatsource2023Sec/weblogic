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

public class DefaultSAFAgentBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DefaultSAFAgentBean beanTreeNode;

   public DefaultSAFAgentBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DefaultSAFAgentBean)btn;
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

   public String getNotes() {
      return this.beanTreeNode.getNotes();
   }

   public void setNotes(String value) {
      this.beanTreeNode.setNotes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Notes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getBytesMaximum() {
      return this.beanTreeNode.getBytesMaximum();
   }

   public void setBytesMaximum(long value) {
      this.beanTreeNode.setBytesMaximum(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BytesMaximum", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getMessagesMaximum() {
      return this.beanTreeNode.getMessagesMaximum();
   }

   public void setMessagesMaximum(long value) {
      this.beanTreeNode.setMessagesMaximum(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessagesMaximum", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaximumMessageSize() {
      return this.beanTreeNode.getMaximumMessageSize();
   }

   public void setMaximumMessageSize(int value) {
      this.beanTreeNode.setMaximumMessageSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaximumMessageSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getDefaultRetryDelayBase() {
      return this.beanTreeNode.getDefaultRetryDelayBase();
   }

   public void setDefaultRetryDelayBase(long value) {
      this.beanTreeNode.setDefaultRetryDelayBase(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultRetryDelayBase", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getDefaultRetryDelayMaximum() {
      return this.beanTreeNode.getDefaultRetryDelayMaximum();
   }

   public void setDefaultRetryDelayMaximum(long value) {
      this.beanTreeNode.setDefaultRetryDelayMaximum(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultRetryDelayMaximum", (Object)null, (Object)null));
      this.setModified(true);
   }

   public double getDefaultRetryDelayMultiplier() {
      return this.beanTreeNode.getDefaultRetryDelayMultiplier();
   }

   public void setDefaultRetryDelayMultiplier(double value) {
      this.beanTreeNode.setDefaultRetryDelayMultiplier(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultRetryDelayMultiplier", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getWindowSize() {
      return this.beanTreeNode.getWindowSize();
   }

   public void setWindowSize(int value) {
      this.beanTreeNode.setWindowSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WindowSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isLoggingEnabled() {
      return this.beanTreeNode.isLoggingEnabled();
   }

   public void setLoggingEnabled(boolean value) {
      this.beanTreeNode.setLoggingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoggingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getDefaultTimeToLive() {
      return this.beanTreeNode.getDefaultTimeToLive();
   }

   public void setDefaultTimeToLive(long value) {
      this.beanTreeNode.setDefaultTimeToLive(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultTimeToLive", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getMessageBufferSize() {
      return this.beanTreeNode.getMessageBufferSize();
   }

   public void setMessageBufferSize(long value) {
      this.beanTreeNode.setMessageBufferSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessageBufferSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPagingDirectory() {
      return this.beanTreeNode.getPagingDirectory();
   }

   public void setPagingDirectory(String value) {
      this.beanTreeNode.setPagingDirectory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PagingDirectory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getWindowInterval() {
      return this.beanTreeNode.getWindowInterval();
   }

   public void setWindowInterval(long value) {
      this.beanTreeNode.setWindowInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WindowInterval", (Object)null, (Object)null));
      this.setModified(true);
   }
}
