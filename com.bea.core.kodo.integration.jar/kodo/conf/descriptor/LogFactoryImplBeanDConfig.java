package kodo.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class LogFactoryImplBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LogFactoryImplBean beanTreeNode;

   public LogFactoryImplBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LogFactoryImplBean)btn;
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

   public String getDiagnosticContext() {
      return this.beanTreeNode.getDiagnosticContext();
   }

   public void setDiagnosticContext(String value) {
      this.beanTreeNode.setDiagnosticContext(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DiagnosticContext", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDefaultLevel() {
      return this.beanTreeNode.getDefaultLevel();
   }

   public void setDefaultLevel(String value) {
      this.beanTreeNode.setDefaultLevel(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultLevel", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFile() {
      return this.beanTreeNode.getFile();
   }

   public void setFile(String value) {
      this.beanTreeNode.setFile(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "File", (Object)null, (Object)null));
      this.setModified(true);
   }
}
