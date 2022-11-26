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

public class FastSwapBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private FastSwapBean beanTreeNode;

   public FastSwapBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (FastSwapBean)btn;
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

   public boolean isEnabled() {
      return this.beanTreeNode.isEnabled();
   }

   public void setEnabled(boolean value) {
      this.beanTreeNode.setEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Enabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRefreshInterval() {
      return this.beanTreeNode.getRefreshInterval();
   }

   public void setRefreshInterval(int value) {
      this.beanTreeNode.setRefreshInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RefreshInterval", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRedefinitionTaskLimit() {
      return this.beanTreeNode.getRedefinitionTaskLimit();
   }

   public void setRedefinitionTaskLimit(int value) {
      this.beanTreeNode.setRedefinitionTaskLimit(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RedefinitionTaskLimit", (Object)null, (Object)null));
      this.setModified(true);
   }
}
