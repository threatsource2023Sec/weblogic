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

public class LibraryRefBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LibraryRefBean beanTreeNode;

   public LibraryRefBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LibraryRefBean)btn;
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

   public String getLibraryName() {
      return this.beanTreeNode.getLibraryName();
   }

   public void setLibraryName(String value) {
      this.beanTreeNode.setLibraryName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LibraryName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSpecificationVersion() {
      return this.beanTreeNode.getSpecificationVersion();
   }

   public void setSpecificationVersion(String value) {
      this.beanTreeNode.setSpecificationVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SpecificationVersion", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getImplementationVersion() {
      return this.beanTreeNode.getImplementationVersion();
   }

   public void setImplementationVersion(String value) {
      this.beanTreeNode.setImplementationVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ImplementationVersion", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getExactMatch() {
      return this.beanTreeNode.getExactMatch();
   }

   public void setExactMatch(boolean value) {
      this.beanTreeNode.setExactMatch(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ExactMatch", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getContextRoot() {
      return this.beanTreeNode.getContextRoot();
   }

   public void setContextRoot(String value) {
      this.beanTreeNode.setContextRoot(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ContextRoot", (Object)null, (Object)null));
      this.setModified(true);
   }
}
