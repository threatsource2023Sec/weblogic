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

public class PersistenceUseBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PersistenceUseBean beanTreeNode;

   public PersistenceUseBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PersistenceUseBean)btn;
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

   public String getTypeIdentifier() {
      return this.beanTreeNode.getTypeIdentifier();
   }

   public void setTypeIdentifier(String value) {
      this.beanTreeNode.setTypeIdentifier(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TypeIdentifier", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTypeVersion() {
      return this.beanTreeNode.getTypeVersion();
   }

   public void setTypeVersion(String value) {
      this.beanTreeNode.setTypeVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TypeVersion", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTypeStorage() {
      return this.beanTreeNode.getTypeStorage();
   }

   public void setTypeStorage(String value) {
      this.beanTreeNode.setTypeStorage(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TypeStorage", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
