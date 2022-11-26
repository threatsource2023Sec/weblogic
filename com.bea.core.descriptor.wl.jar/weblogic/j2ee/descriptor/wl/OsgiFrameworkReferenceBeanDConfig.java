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

public class OsgiFrameworkReferenceBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private OsgiFrameworkReferenceBean beanTreeNode;

   public OsgiFrameworkReferenceBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (OsgiFrameworkReferenceBean)btn;
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

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBundlesDirectory() {
      return this.beanTreeNode.getBundlesDirectory();
   }

   public void setBundlesDirectory(String value) {
      this.beanTreeNode.setBundlesDirectory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BundlesDirectory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getApplicationBundleSymbolicName() {
      return this.beanTreeNode.getApplicationBundleSymbolicName();
   }

   public void setApplicationBundleSymbolicName(String value) {
      this.beanTreeNode.setApplicationBundleSymbolicName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ApplicationBundleSymbolicName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getApplicationBundleVersion() {
      return this.beanTreeNode.getApplicationBundleVersion();
   }

   public void setApplicationBundleVersion(String value) {
      this.beanTreeNode.setApplicationBundleVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ApplicationBundleVersion", (Object)null, (Object)null));
      this.setModified(true);
   }
}
