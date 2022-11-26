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

public class StatefulSessionDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private StatefulSessionDescriptorBean beanTreeNode;

   public StatefulSessionDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (StatefulSessionDescriptorBean)btn;
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

   public StatefulSessionCacheBean getStatefulSessionCache() {
      return this.beanTreeNode.getStatefulSessionCache();
   }

   public boolean isStatefulSessionCacheSet() {
      return this.beanTreeNode.isStatefulSessionCacheSet();
   }

   public String getPersistentStoreDir() {
      return this.beanTreeNode.getPersistentStoreDir();
   }

   public void setPersistentStoreDir(String value) {
      this.beanTreeNode.setPersistentStoreDir(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentStoreDir", (Object)null, (Object)null));
      this.setModified(true);
   }

   public StatefulSessionClusteringBean getStatefulSessionClustering() {
      return this.beanTreeNode.getStatefulSessionClustering();
   }

   public boolean isStatefulSessionClusteringSet() {
      return this.beanTreeNode.isStatefulSessionClusteringSet();
   }

   public boolean isAllowRemoveDuringTransaction() {
      return this.beanTreeNode.isAllowRemoveDuringTransaction();
   }

   public void setAllowRemoveDuringTransaction(boolean value) {
      this.beanTreeNode.setAllowRemoveDuringTransaction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllowRemoveDuringTransaction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public BusinessInterfaceJndiNameMapBean[] getBusinessInterfaceJndiNameMaps() {
      return this.beanTreeNode.getBusinessInterfaceJndiNameMaps();
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
