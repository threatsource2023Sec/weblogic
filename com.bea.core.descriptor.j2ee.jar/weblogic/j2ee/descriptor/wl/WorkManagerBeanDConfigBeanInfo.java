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

public class WorkManagerBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WorkManagerBean beanTreeNode;

   public WorkManagerBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WorkManagerBean)btn;
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
      return this.getName();
   }

   public void initKeyPropertyValue(String value) {
      this.setName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Name: ");
      sb.append(this.beanTreeNode.getName());
      sb.append("\n");
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

   public ResponseTimeRequestClassBean getResponseTimeRequestClass() {
      return this.beanTreeNode.getResponseTimeRequestClass();
   }

   public FairShareRequestClassBean getFairShareRequestClass() {
      return this.beanTreeNode.getFairShareRequestClass();
   }

   public ContextRequestClassBean getContextRequestClass() {
      return this.beanTreeNode.getContextRequestClass();
   }

   public String getRequestClassName() {
      return this.beanTreeNode.getRequestClassName();
   }

   public void setRequestClassName(String value) {
      this.beanTreeNode.setRequestClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequestClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MinThreadsConstraintBean getMinThreadsConstraint() {
      return this.beanTreeNode.getMinThreadsConstraint();
   }

   public String getMinThreadsConstraintName() {
      return this.beanTreeNode.getMinThreadsConstraintName();
   }

   public void setMinThreadsConstraintName(String value) {
      this.beanTreeNode.setMinThreadsConstraintName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MinThreadsConstraintName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MaxThreadsConstraintBean getMaxThreadsConstraint() {
      return this.beanTreeNode.getMaxThreadsConstraint();
   }

   public String getMaxThreadsConstraintName() {
      return this.beanTreeNode.getMaxThreadsConstraintName();
   }

   public void setMaxThreadsConstraintName(String value) {
      this.beanTreeNode.setMaxThreadsConstraintName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxThreadsConstraintName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public CapacityBean getCapacity() {
      return this.beanTreeNode.getCapacity();
   }

   public String getCapacityName() {
      return this.beanTreeNode.getCapacityName();
   }

   public void setCapacityName(String value) {
      this.beanTreeNode.setCapacityName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CapacityName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WorkManagerShutdownTriggerBean getWorkManagerShutdownTrigger() {
      return this.beanTreeNode.getWorkManagerShutdownTrigger();
   }

   public boolean getIgnoreStuckThreads() {
      return this.beanTreeNode.getIgnoreStuckThreads();
   }

   public void setIgnoreStuckThreads(boolean value) {
      this.beanTreeNode.setIgnoreStuckThreads(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IgnoreStuckThreads", (Object)null, (Object)null));
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
