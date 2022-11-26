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

public class AppDeploymentBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AppDeploymentBean beanTreeNode;

   public AppDeploymentBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AppDeploymentBean)btn;
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
      return this.getName();
   }

   public void initKeyPropertyValue(String value) {
      this.setName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Name: ");
      sb.append(this.beanTreeNode.getName());
      sb.append("\n");
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

   public String getSourcePath() {
      return this.beanTreeNode.getSourcePath();
   }

   public void setSourcePath(String value) {
      this.beanTreeNode.setSourcePath(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SourcePath", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRetireTimeout() {
      return this.beanTreeNode.getRetireTimeout();
   }

   public void setRetireTimeout(String value) {
      this.beanTreeNode.setRetireTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetireTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRetireTimeoutSet() {
      return this.beanTreeNode.isRetireTimeoutSet();
   }

   public String getGeneratedVersion() {
      return this.beanTreeNode.getGeneratedVersion();
   }

   public void setGeneratedVersion(String value) {
      this.beanTreeNode.setGeneratedVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GeneratedVersion", (Object)null, (Object)null));
      this.setModified(true);
   }
}
