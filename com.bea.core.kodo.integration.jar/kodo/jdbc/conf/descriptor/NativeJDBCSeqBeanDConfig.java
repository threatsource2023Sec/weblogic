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

public class NativeJDBCSeqBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private NativeJDBCSeqBean beanTreeNode;

   public NativeJDBCSeqBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (NativeJDBCSeqBean)btn;
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

   public int getType() {
      return this.beanTreeNode.getType();
   }

   public void setType(int value) {
      this.beanTreeNode.setType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Type", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getAllocate() {
      return this.beanTreeNode.getAllocate();
   }

   public void setAllocate(int value) {
      this.beanTreeNode.setAllocate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Allocate", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTableName() {
      return this.beanTreeNode.getTableName();
   }

   public void setTableName(String value) {
      this.beanTreeNode.setTableName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TableName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getInitialValue() {
      return this.beanTreeNode.getInitialValue();
   }

   public void setInitialValue(int value) {
      this.beanTreeNode.setInitialValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitialValue", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSequence() {
      return this.beanTreeNode.getSequence();
   }

   public void setSequence(String value) {
      this.beanTreeNode.setSequence(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Sequence", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSequenceName() {
      return this.beanTreeNode.getSequenceName();
   }

   public void setSequenceName(String value) {
      this.beanTreeNode.setSequenceName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SequenceName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFormat() {
      return this.beanTreeNode.getFormat();
   }

   public void setFormat(String value) {
      this.beanTreeNode.setFormat(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Format", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getIncrement() {
      return this.beanTreeNode.getIncrement();
   }

   public void setIncrement(int value) {
      this.beanTreeNode.setIncrement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Increment", (Object)null, (Object)null));
      this.setModified(true);
   }
}
