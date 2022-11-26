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

public class MappingDefaultsImplBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MappingDefaultsImplBean beanTreeNode;

   public MappingDefaultsImplBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MappingDefaultsImplBean)btn;
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

   public boolean getUseClassCriteria() {
      return this.beanTreeNode.getUseClassCriteria();
   }

   public void setUseClassCriteria(boolean value) {
      this.beanTreeNode.setUseClassCriteria(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseClassCriteria", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBaseClassStrategy() {
      return this.beanTreeNode.getBaseClassStrategy();
   }

   public void setBaseClassStrategy(String value) {
      this.beanTreeNode.setBaseClassStrategy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BaseClassStrategy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getVersionStrategy() {
      return this.beanTreeNode.getVersionStrategy();
   }

   public void setVersionStrategy(String value) {
      this.beanTreeNode.setVersionStrategy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VersionStrategy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDiscriminatorColumnName() {
      return this.beanTreeNode.getDiscriminatorColumnName();
   }

   public void setDiscriminatorColumnName(String value) {
      this.beanTreeNode.setDiscriminatorColumnName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DiscriminatorColumnName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSubclassStrategy() {
      return this.beanTreeNode.getSubclassStrategy();
   }

   public void setSubclassStrategy(String value) {
      this.beanTreeNode.setSubclassStrategy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SubclassStrategy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getIndexVersion() {
      return this.beanTreeNode.getIndexVersion();
   }

   public void setIndexVersion(boolean value) {
      this.beanTreeNode.setIndexVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IndexVersion", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getDefaultMissingInfo() {
      return this.beanTreeNode.getDefaultMissingInfo();
   }

   public void setDefaultMissingInfo(boolean value) {
      this.beanTreeNode.setDefaultMissingInfo(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultMissingInfo", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getIndexLogicalForeignKeys() {
      return this.beanTreeNode.getIndexLogicalForeignKeys();
   }

   public void setIndexLogicalForeignKeys(boolean value) {
      this.beanTreeNode.setIndexLogicalForeignKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IndexLogicalForeignKeys", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNullIndicatorColumnName() {
      return this.beanTreeNode.getNullIndicatorColumnName();
   }

   public void setNullIndicatorColumnName(String value) {
      this.beanTreeNode.setNullIndicatorColumnName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NullIndicatorColumnName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getForeignKeyDeleteAction() {
      return this.beanTreeNode.getForeignKeyDeleteAction();
   }

   public void setForeignKeyDeleteAction(int value) {
      this.beanTreeNode.setForeignKeyDeleteAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ForeignKeyDeleteAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJoinForeignKeyDeleteAction() {
      return this.beanTreeNode.getJoinForeignKeyDeleteAction();
   }

   public void setJoinForeignKeyDeleteAction(String value) {
      this.beanTreeNode.setJoinForeignKeyDeleteAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JoinForeignKeyDeleteAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDiscriminatorStrategy() {
      return this.beanTreeNode.getDiscriminatorStrategy();
   }

   public void setDiscriminatorStrategy(String value) {
      this.beanTreeNode.setDiscriminatorStrategy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DiscriminatorStrategy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getDeferConstraints() {
      return this.beanTreeNode.getDeferConstraints();
   }

   public void setDeferConstraints(boolean value) {
      this.beanTreeNode.setDeferConstraints(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DeferConstraints", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFieldStrategies() {
      return this.beanTreeNode.getFieldStrategies();
   }

   public void setFieldStrategies(String value) {
      this.beanTreeNode.setFieldStrategies(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FieldStrategies", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getVersionColumnName() {
      return this.beanTreeNode.getVersionColumnName();
   }

   public void setVersionColumnName(String value) {
      this.beanTreeNode.setVersionColumnName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VersionColumnName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDataStoreIdColumnName() {
      return this.beanTreeNode.getDataStoreIdColumnName();
   }

   public void setDataStoreIdColumnName(String value) {
      this.beanTreeNode.setDataStoreIdColumnName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DataStoreIdColumnName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getIndexDiscriminator() {
      return this.beanTreeNode.getIndexDiscriminator();
   }

   public void setIndexDiscriminator(boolean value) {
      this.beanTreeNode.setIndexDiscriminator(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IndexDiscriminator", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStoreEnumOrdinal() {
      return this.beanTreeNode.getStoreEnumOrdinal();
   }

   public void setStoreEnumOrdinal(boolean value) {
      this.beanTreeNode.setStoreEnumOrdinal(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StoreEnumOrdinal", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getOrderLists() {
      return this.beanTreeNode.getOrderLists();
   }

   public void setOrderLists(boolean value) {
      this.beanTreeNode.setOrderLists(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OrderLists", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOrderColumnName() {
      return this.beanTreeNode.getOrderColumnName();
   }

   public void setOrderColumnName(String value) {
      this.beanTreeNode.setOrderColumnName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OrderColumnName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getAddNullIndicator() {
      return this.beanTreeNode.getAddNullIndicator();
   }

   public void setAddNullIndicator(boolean value) {
      this.beanTreeNode.setAddNullIndicator(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AddNullIndicator", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStoreUnmappedObjectIdString() {
      return this.beanTreeNode.getStoreUnmappedObjectIdString();
   }

   public void setStoreUnmappedObjectIdString(boolean value) {
      this.beanTreeNode.setStoreUnmappedObjectIdString(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StoreUnmappedObjectIdString", (Object)null, (Object)null));
      this.setModified(true);
   }
}
