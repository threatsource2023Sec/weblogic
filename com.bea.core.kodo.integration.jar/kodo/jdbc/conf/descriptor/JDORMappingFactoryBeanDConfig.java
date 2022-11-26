package kodo.jdbc.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class JDORMappingFactoryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JDORMappingFactoryBean beanTreeNode;

   public JDORMappingFactoryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDORMappingFactoryBean)btn;
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

   public boolean getUseSchemaValidation() {
      return this.beanTreeNode.getUseSchemaValidation();
   }

   public void setUseSchemaValidation(boolean value) {
      this.beanTreeNode.setUseSchemaValidation(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSchemaValidation", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getURLs() {
      return this.beanTreeNode.getURLs();
   }

   public void setURLs(String value) {
      this.beanTreeNode.setURLs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "URLs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFiles() {
      return this.beanTreeNode.getFiles();
   }

   public void setFiles(String value) {
      this.beanTreeNode.setFiles(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Files", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getClasspathScan() {
      return this.beanTreeNode.getClasspathScan();
   }

   public void setClasspathScan(String value) {
      this.beanTreeNode.setClasspathScan(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClasspathScan", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getConstraintNames() {
      return this.beanTreeNode.getConstraintNames();
   }

   public void setConstraintNames(boolean value) {
      this.beanTreeNode.setConstraintNames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConstraintNames", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTypes() {
      return this.beanTreeNode.getTypes();
   }

   public void setTypes(String value) {
      this.beanTreeNode.setTypes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Types", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getStoreMode() {
      return this.beanTreeNode.getStoreMode();
   }

   public void setStoreMode(int value) {
      this.beanTreeNode.setStoreMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StoreMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStrict() {
      return this.beanTreeNode.getStrict();
   }

   public void setStrict(boolean value) {
      this.beanTreeNode.setStrict(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Strict", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getResources() {
      return this.beanTreeNode.getResources();
   }

   public void setResources(String value) {
      this.beanTreeNode.setResources(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Resources", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getScanTopDown() {
      return this.beanTreeNode.getScanTopDown();
   }

   public void setScanTopDown(boolean value) {
      this.beanTreeNode.setScanTopDown(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ScanTopDown", (Object)null, (Object)null));
      this.setModified(true);
   }
}
