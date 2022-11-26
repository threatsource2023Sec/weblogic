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

public class VariableAssignmentBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private VariableAssignmentBean beanTreeNode;

   public VariableAssignmentBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (VariableAssignmentBean)btn;
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

   public String getDescription() {
      return this.beanTreeNode.getDescription();
   }

   public void setDescription(String value) {
      this.beanTreeNode.setDescription(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Description", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getXpath() {
      return this.beanTreeNode.getXpath();
   }

   public void setXpath(String value) {
      this.beanTreeNode.setXpath(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Xpath", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOrigin() {
      return this.beanTreeNode.getOrigin();
   }

   public void setOrigin(String value) {
      this.beanTreeNode.setOrigin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Origin", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOperation() {
      return this.beanTreeNode.getOperation();
   }

   public void setOperation(String value) {
      this.beanTreeNode.setOperation(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Operation", (Object)null, (Object)null));
      this.setModified(true);
   }
}
