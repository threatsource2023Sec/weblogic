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

public class ModuleDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ModuleDescriptorBean beanTreeNode;

   public ModuleDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ModuleDescriptorBean)btn;
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
      return this.getUri();
   }

   public void initKeyPropertyValue(String value) {
      this.setUri(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Uri: ");
      sb.append(this.beanTreeNode.getUri());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getRootElement() {
      return this.beanTreeNode.getRootElement();
   }

   public void setRootElement(String value) {
      this.beanTreeNode.setRootElement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RootElement", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getUri() {
      return this.beanTreeNode.getUri();
   }

   public void setUri(String value) {
      this.beanTreeNode.setUri(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Uri", (Object)null, (Object)null));
      this.setModified(true);
   }

   public VariableAssignmentBean[] getVariableAssignments() {
      return this.beanTreeNode.getVariableAssignments();
   }

   public String getHashCode() {
      return this.beanTreeNode.getHashCode();
   }

   public void setHashCode(String value) {
      this.beanTreeNode.setHashCode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HashCode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isExternal() {
      return this.beanTreeNode.isExternal();
   }

   public void setExternal(boolean value) {
      this.beanTreeNode.setExternal(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "External", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isChanged() {
      return this.beanTreeNode.isChanged();
   }

   public void setChanged(boolean value) {
      this.beanTreeNode.setChanged(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Changed", (Object)null, (Object)null));
      this.setModified(true);
   }
}
