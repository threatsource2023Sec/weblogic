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

public class KodoPersistenceMappingFactoryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private KodoPersistenceMappingFactoryBean beanTreeNode;

   public KodoPersistenceMappingFactoryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (KodoPersistenceMappingFactoryBean)btn;
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

   public String getDefaultAccessType() {
      return this.beanTreeNode.getDefaultAccessType();
   }

   public void setDefaultAccessType(String value) {
      this.beanTreeNode.setDefaultAccessType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultAccessType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getFieldOverride() {
      return this.beanTreeNode.getFieldOverride();
   }

   public void setFieldOverride(boolean value) {
      this.beanTreeNode.setFieldOverride(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FieldOverride", (Object)null, (Object)null));
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
}
