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

public class SAFDestinationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SAFDestinationBean beanTreeNode;

   public SAFDestinationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SAFDestinationBean)btn;
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
      sb.append("UnitOfOrderRouting: ");
      sb.append(this.beanTreeNode.getUnitOfOrderRouting());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getRemoteJNDIName() {
      return this.beanTreeNode.getRemoteJNDIName();
   }

   public void setRemoteJNDIName(String value) {
      this.beanTreeNode.setRemoteJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RemoteJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLocalJNDIName() {
      return this.beanTreeNode.getLocalJNDIName();
   }

   public void setLocalJNDIName(String value) {
      this.beanTreeNode.setLocalJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LocalJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPersistentQos() {
      return this.beanTreeNode.getPersistentQos();
   }

   public void setPersistentQos(String value) {
      this.beanTreeNode.setPersistentQos(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentQos", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNonPersistentQos() {
      return this.beanTreeNode.getNonPersistentQos();
   }

   public void setNonPersistentQos(String value) {
      this.beanTreeNode.setNonPersistentQos(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NonPersistentQos", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SAFErrorHandlingBean getSAFErrorHandling() {
      return this.beanTreeNode.getSAFErrorHandling();
   }

   public void setSAFErrorHandling(SAFErrorHandlingBean value) {
      this.beanTreeNode.setSAFErrorHandling(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SAFErrorHandling", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getTimeToLiveDefault() {
      return this.beanTreeNode.getTimeToLiveDefault();
   }

   public void setTimeToLiveDefault(long value) {
      this.beanTreeNode.setTimeToLiveDefault(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeToLiveDefault", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseSAFTimeToLiveDefault() {
      return this.beanTreeNode.isUseSAFTimeToLiveDefault();
   }

   public void setUseSAFTimeToLiveDefault(boolean value) {
      this.beanTreeNode.setUseSAFTimeToLiveDefault(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSAFTimeToLiveDefault", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getUnitOfOrderRouting() {
      return this.beanTreeNode.getUnitOfOrderRouting();
   }

   public void setUnitOfOrderRouting(String value) {
      this.beanTreeNode.setUnitOfOrderRouting(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UnitOfOrderRouting", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this.beanTreeNode.getMessageLoggingParams();
   }
}
