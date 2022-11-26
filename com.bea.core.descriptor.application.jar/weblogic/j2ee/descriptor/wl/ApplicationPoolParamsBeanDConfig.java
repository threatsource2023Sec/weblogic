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

public class ApplicationPoolParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ApplicationPoolParamsBean beanTreeNode;

   public ApplicationPoolParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ApplicationPoolParamsBean)btn;
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

   public SizeParamsBean getSizeParams() {
      return this.beanTreeNode.getSizeParams();
   }

   public XAParamsBean getXAParams() {
      return this.beanTreeNode.getXAParams();
   }

   public int getLoginDelaySeconds() {
      return this.beanTreeNode.getLoginDelaySeconds();
   }

   public void setLoginDelaySeconds(int value) {
      this.beanTreeNode.setLoginDelaySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoginDelaySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isLeakProfilingEnabled() {
      return this.beanTreeNode.isLeakProfilingEnabled();
   }

   public void setLeakProfilingEnabled(boolean value) {
      this.beanTreeNode.setLeakProfilingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LeakProfilingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ConnectionCheckParamsBean getConnectionCheckParams() {
      return this.beanTreeNode.getConnectionCheckParams();
   }

   public int getJDBCXADebugLevel() {
      return this.beanTreeNode.getJDBCXADebugLevel();
   }

   public void setJDBCXADebugLevel(int value) {
      this.beanTreeNode.setJDBCXADebugLevel(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JDBCXADebugLevel", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRemoveInfectedConnectionsEnabled() {
      return this.beanTreeNode.isRemoveInfectedConnectionsEnabled();
   }

   public void setRemoveInfectedConnectionsEnabled(boolean value) {
      this.beanTreeNode.setRemoveInfectedConnectionsEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RemoveInfectedConnectionsEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }
}
