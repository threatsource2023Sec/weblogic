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

public class PointbaseDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PointbaseDictionaryBean beanTreeNode;

   public PointbaseDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PointbaseDictionaryBean)btn;
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

   public String getCharTypeName() {
      return this.beanTreeNode.getCharTypeName();
   }

   public void setCharTypeName(String value) {
      this.beanTreeNode.setCharTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CharTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getClobTypeName() {
      return this.beanTreeNode.getClobTypeName();
   }

   public void setClobTypeName(String value) {
      this.beanTreeNode.setClobTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClobTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsLockingWithDistinctClause() {
      return this.beanTreeNode.getSupportsLockingWithDistinctClause();
   }

   public void setSupportsLockingWithDistinctClause(boolean value) {
      this.beanTreeNode.setSupportsLockingWithDistinctClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithDistinctClause", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLongVarbinaryTypeName() {
      return this.beanTreeNode.getLongVarbinaryTypeName();
   }

   public void setLongVarbinaryTypeName(String value) {
      this.beanTreeNode.setLongVarbinaryTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LongVarbinaryTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsLockingWithMultipleTables() {
      return this.beanTreeNode.getSupportsLockingWithMultipleTables();
   }

   public void setSupportsLockingWithMultipleTables(boolean value) {
      this.beanTreeNode.setSupportsLockingWithMultipleTables(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithMultipleTables", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDoubleTypeName() {
      return this.beanTreeNode.getDoubleTypeName();
   }

   public void setDoubleTypeName(String value) {
      this.beanTreeNode.setDoubleTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DoubleTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBitTypeName() {
      return this.beanTreeNode.getBitTypeName();
   }

   public void setBitTypeName(String value) {
      this.beanTreeNode.setBitTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BitTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsAutoAssign() {
      return this.beanTreeNode.getSupportsAutoAssign();
   }

   public void setSupportsAutoAssign(boolean value) {
      this.beanTreeNode.setSupportsAutoAssign(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsAutoAssign", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAutoAssignTypeName() {
      return this.beanTreeNode.getAutoAssignTypeName();
   }

   public void setAutoAssignTypeName(String value) {
      this.beanTreeNode.setAutoAssignTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AutoAssignTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsMultipleNontransactionalResultSets() {
      return this.beanTreeNode.getSupportsMultipleNontransactionalResultSets();
   }

   public void setSupportsMultipleNontransactionalResultSets(boolean value) {
      this.beanTreeNode.setSupportsMultipleNontransactionalResultSets(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsMultipleNontransactionalResultSets", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getIntegerTypeName() {
      return this.beanTreeNode.getIntegerTypeName();
   }

   public void setIntegerTypeName(String value) {
      this.beanTreeNode.setIntegerTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IntegerTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBlobTypeName() {
      return this.beanTreeNode.getBlobTypeName();
   }

   public void setBlobTypeName(String value) {
      this.beanTreeNode.setBlobTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BlobTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPlatform() {
      return this.beanTreeNode.getPlatform();
   }

   public void setPlatform(String value) {
      this.beanTreeNode.setPlatform(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Platform", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBigintTypeName() {
      return this.beanTreeNode.getBigintTypeName();
   }

   public void setBigintTypeName(String value) {
      this.beanTreeNode.setBigintTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BigintTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLastGeneratedKeyQuery() {
      return this.beanTreeNode.getLastGeneratedKeyQuery();
   }

   public void setLastGeneratedKeyQuery(String value) {
      this.beanTreeNode.setLastGeneratedKeyQuery(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LastGeneratedKeyQuery", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsDeferredConstraints() {
      return this.beanTreeNode.getSupportsDeferredConstraints();
   }

   public void setSupportsDeferredConstraints(boolean value) {
      this.beanTreeNode.setSupportsDeferredConstraints(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsDeferredConstraints", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRealTypeName() {
      return this.beanTreeNode.getRealTypeName();
   }

   public void setRealTypeName(String value) {
      this.beanTreeNode.setRealTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RealTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRequiresAliasForSubselect() {
      return this.beanTreeNode.getRequiresAliasForSubselect();
   }

   public void setRequiresAliasForSubselect(boolean value) {
      this.beanTreeNode.setRequiresAliasForSubselect(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresAliasForSubselect", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTinyintTypeName() {
      return this.beanTreeNode.getTinyintTypeName();
   }

   public void setTinyintTypeName(String value) {
      this.beanTreeNode.setTinyintTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TinyintTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSmallintTypeName() {
      return this.beanTreeNode.getSmallintTypeName();
   }

   public void setSmallintTypeName(String value) {
      this.beanTreeNode.setSmallintTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SmallintTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFloatTypeName() {
      return this.beanTreeNode.getFloatTypeName();
   }

   public void setFloatTypeName(String value) {
      this.beanTreeNode.setFloatTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FloatTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
