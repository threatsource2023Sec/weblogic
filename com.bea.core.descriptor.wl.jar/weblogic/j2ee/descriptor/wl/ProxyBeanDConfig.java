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

public class ProxyBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ProxyBean beanTreeNode;

   public ProxyBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ProxyBean)btn;
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

   public int getInactiveConnectionTimeoutSeconds() {
      return this.beanTreeNode.getInactiveConnectionTimeoutSeconds();
   }

   public void setInactiveConnectionTimeoutSeconds(int value) {
      this.beanTreeNode.setInactiveConnectionTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InactiveConnectionTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isConnectionProfilingEnabled() {
      return this.beanTreeNode.isConnectionProfilingEnabled();
   }

   public void setConnectionProfilingEnabled(boolean value) {
      this.beanTreeNode.setConnectionProfilingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionProfilingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getUseConnectionProxies() {
      return this.beanTreeNode.getUseConnectionProxies();
   }

   public void setUseConnectionProxies(String value) {
      this.beanTreeNode.setUseConnectionProxies(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseConnectionProxies", (Object)null, (Object)null));
      this.setModified(true);
   }
}
