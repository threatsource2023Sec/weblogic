package kodo.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class KodoBrokerBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private KodoBrokerBean beanTreeNode;

   public KodoBrokerBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (KodoBrokerBean)btn;
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

   public boolean getLargeTransaction() {
      return this.beanTreeNode.getLargeTransaction();
   }

   public void setLargeTransaction(boolean value) {
      this.beanTreeNode.setLargeTransaction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LargeTransaction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getAutoClear() {
      return this.beanTreeNode.getAutoClear();
   }

   public void setAutoClear(int value) {
      this.beanTreeNode.setAutoClear(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AutoClear", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getDetachState() {
      return this.beanTreeNode.getDetachState();
   }

   public void setDetachState(int value) {
      this.beanTreeNode.setDetachState(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DetachState", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getNontransactionalRead() {
      return this.beanTreeNode.getNontransactionalRead();
   }

   public void setNontransactionalRead(boolean value) {
      this.beanTreeNode.setNontransactionalRead(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NontransactionalRead", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRetainState() {
      return this.beanTreeNode.getRetainState();
   }

   public void setRetainState(boolean value) {
      this.beanTreeNode.setRetainState(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetainState", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getEvictFromDataCache() {
      return this.beanTreeNode.getEvictFromDataCache();
   }

   public void setEvictFromDataCache(boolean value) {
      this.beanTreeNode.setEvictFromDataCache(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EvictFromDataCache", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getDetachedNew() {
      return this.beanTreeNode.getDetachedNew();
   }

   public void setDetachedNew(boolean value) {
      this.beanTreeNode.setDetachedNew(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DetachedNew", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getOptimistic() {
      return this.beanTreeNode.getOptimistic();
   }

   public void setOptimistic(boolean value) {
      this.beanTreeNode.setOptimistic(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Optimistic", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getNontransactionalWrite() {
      return this.beanTreeNode.getNontransactionalWrite();
   }

   public void setNontransactionalWrite(boolean value) {
      this.beanTreeNode.setNontransactionalWrite(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NontransactionalWrite", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSyncWithManagedTransactions() {
      return this.beanTreeNode.getSyncWithManagedTransactions();
   }

   public void setSyncWithManagedTransactions(boolean value) {
      this.beanTreeNode.setSyncWithManagedTransactions(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SyncWithManagedTransactions", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getMultithreaded() {
      return this.beanTreeNode.getMultithreaded();
   }

   public void setMultithreaded(boolean value) {
      this.beanTreeNode.setMultithreaded(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Multithreaded", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getPopulateDataCache() {
      return this.beanTreeNode.getPopulateDataCache();
   }

   public void setPopulateDataCache(boolean value) {
      this.beanTreeNode.setPopulateDataCache(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PopulateDataCache", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getIgnoreChanges() {
      return this.beanTreeNode.getIgnoreChanges();
   }

   public void setIgnoreChanges(boolean value) {
      this.beanTreeNode.setIgnoreChanges(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IgnoreChanges", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getAutoDetach() {
      return this.beanTreeNode.getAutoDetach();
   }

   public void setAutoDetach(int value) {
      this.beanTreeNode.setAutoDetach(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AutoDetach", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRestoreState() {
      return this.beanTreeNode.getRestoreState();
   }

   public void setRestoreState(int value) {
      this.beanTreeNode.setRestoreState(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RestoreState", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getOrderDirtyObjects() {
      return this.beanTreeNode.getOrderDirtyObjects();
   }

   public void setOrderDirtyObjects(boolean value) {
      this.beanTreeNode.setOrderDirtyObjects(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OrderDirtyObjects", (Object)null, (Object)null));
      this.setModified(true);
   }
}
