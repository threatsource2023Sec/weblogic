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

public class BufferingQueueBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private BufferingQueueBean beanTreeNode;

   public BufferingQueueBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (BufferingQueueBean)btn;
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

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public Boolean getEnabled() {
      return this.beanTreeNode.getEnabled();
   }

   public void setEnabled(Boolean value) {
      this.beanTreeNode.setEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Enabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionFactoryJndiName() {
      return this.beanTreeNode.getConnectionFactoryJndiName();
   }

   public void setConnectionFactoryJndiName(String value) {
      this.beanTreeNode.setConnectionFactoryJndiName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactoryJndiName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public Boolean getTransactionEnabled() {
      return this.beanTreeNode.getTransactionEnabled();
   }

   public void setTransactionEnabled(Boolean value) {
      this.beanTreeNode.setTransactionEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TransactionEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }
}
