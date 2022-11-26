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

public class DefaultPersistentStoreBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DefaultPersistentStoreBean beanTreeNode;

   public DefaultPersistentStoreBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DefaultPersistentStoreBean)btn;
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

   public String getNotes() {
      return this.beanTreeNode.getNotes();
   }

   public void setNotes(String value) {
      this.beanTreeNode.setNotes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Notes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDirectoryPath() {
      return this.beanTreeNode.getDirectoryPath();
   }

   public void setDirectoryPath(String value) {
      this.beanTreeNode.setDirectoryPath(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DirectoryPath", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSynchronousWritePolicy() {
      return this.beanTreeNode.getSynchronousWritePolicy();
   }

   public void setSynchronousWritePolicy(String value) {
      this.beanTreeNode.setSynchronousWritePolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SynchronousWritePolicy", (Object)null, (Object)null));
      this.setModified(true);
   }
}
