package weblogic.diagnostics.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class WLDFScriptActionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFScriptActionBean beanTreeNode;

   public WLDFScriptActionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFScriptActionBean)btn;
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

   public String getWorkingDirectory() {
      return this.beanTreeNode.getWorkingDirectory();
   }

   public void setWorkingDirectory(String value) {
      this.beanTreeNode.setWorkingDirectory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WorkingDirectory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPathToScript() {
      return this.beanTreeNode.getPathToScript();
   }

   public void setPathToScript(String value) {
      this.beanTreeNode.setPathToScript(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PathToScript", (Object)null, (Object)null));
      this.setModified(true);
   }

   public Properties getEnvironment() {
      return this.beanTreeNode.getEnvironment();
   }

   public void setEnvironment(Properties value) {
      this.beanTreeNode.setEnvironment(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Environment", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getParameters() {
      return this.beanTreeNode.getParameters();
   }

   public void setParameters(String[] value) {
      this.beanTreeNode.setParameters(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Parameters", (Object)null, (Object)null));
      this.setModified(true);
   }
}
