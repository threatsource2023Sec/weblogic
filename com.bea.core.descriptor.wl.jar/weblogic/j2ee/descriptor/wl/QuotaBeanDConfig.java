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

public class QuotaBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private QuotaBean beanTreeNode;

   public QuotaBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (QuotaBean)btn;
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

   public String getPolicy() {
      return this.beanTreeNode.getPolicy();
   }

   public void setPolicy(String value) {
      this.beanTreeNode.setPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Policy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isShared() {
      return this.beanTreeNode.isShared();
   }

   public void setShared(boolean value) {
      this.beanTreeNode.setShared(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Shared", (Object)null, (Object)null));
      this.setModified(true);
   }
}
