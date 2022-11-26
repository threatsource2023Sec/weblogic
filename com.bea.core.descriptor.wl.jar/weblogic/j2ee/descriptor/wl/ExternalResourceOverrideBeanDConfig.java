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

public class ExternalResourceOverrideBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ExternalResourceOverrideBean beanTreeNode;

   public ExternalResourceOverrideBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ExternalResourceOverrideBean)btn;
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

   public String getResourceName() {
      return this.beanTreeNode.getResourceName();
   }

   public void setResourceName(String value) {
      this.beanTreeNode.setResourceName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getResourceType() {
      return this.beanTreeNode.getResourceType();
   }

   public void setResourceType(String value) {
      this.beanTreeNode.setResourceType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRootElement() {
      return this.beanTreeNode.getRootElement();
   }

   public void setRootElement(String value) {
      this.beanTreeNode.setRootElement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RootElement", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDescriptorFilePath() {
      return this.beanTreeNode.getDescriptorFilePath();
   }

   public void setDescriptorFilePath(String value) {
      this.beanTreeNode.setDescriptorFilePath(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DescriptorFilePath", (Object)null, (Object)null));
      this.setModified(true);
   }

   public VariableAssignmentBean[] getVariableAssignments() {
      return this.beanTreeNode.getVariableAssignments();
   }
}
