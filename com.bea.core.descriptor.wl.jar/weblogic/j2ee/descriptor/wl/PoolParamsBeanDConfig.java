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

public class PoolParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PoolParamsBean beanTreeNode;

   public PoolParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PoolParamsBean)btn;
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

   public int getMaxCapacity() {
      return this.beanTreeNode.getMaxCapacity();
   }

   public void setMaxCapacity(int value) {
      this.beanTreeNode.setMaxCapacity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxCapacity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this.beanTreeNode.getConnectionReserveTimeoutSeconds();
   }

   public void setConnectionReserveTimeoutSeconds(int value) {
      this.beanTreeNode.setConnectionReserveTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionReserveTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getHighestNumWaiters() {
      return this.beanTreeNode.getHighestNumWaiters();
   }

   public void setHighestNumWaiters(int value) {
      this.beanTreeNode.setHighestNumWaiters(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HighestNumWaiters", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isMatchConnectionsSupported() {
      return this.beanTreeNode.isMatchConnectionsSupported();
   }

   public void setMatchConnectionsSupported(boolean value) {
      this.beanTreeNode.setMatchConnectionsSupported(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MatchConnectionsSupported", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseFirstAvailable() {
      return this.beanTreeNode.isUseFirstAvailable();
   }

   public void setUseFirstAvailable(boolean value) {
      this.beanTreeNode.setUseFirstAvailable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseFirstAvailable", (Object)null, (Object)null));
      this.setModified(true);
   }
}
