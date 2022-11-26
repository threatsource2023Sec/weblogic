package weblogic.diagnostics.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class WLDFJMSNotificationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFJMSNotificationBean beanTreeNode;

   public WLDFJMSNotificationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFJMSNotificationBean)btn;
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

   public String getDestinationJNDIName() {
      return this.beanTreeNode.getDestinationJNDIName();
   }

   public void setDestinationJNDIName(String value) {
      this.beanTreeNode.setDestinationJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DestinationJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionFactoryJNDIName() {
      return this.beanTreeNode.getConnectionFactoryJNDIName();
   }

   public void setConnectionFactoryJNDIName(String value) {
      this.beanTreeNode.setConnectionFactoryJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactoryJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
