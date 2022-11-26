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

public class InformixDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private InformixDictionaryBean beanTreeNode;

   public InformixDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (InformixDictionaryBean)btn;
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

   public boolean getSupportsQueryTimeout() {
      return this.beanTreeNode.getSupportsQueryTimeout();
   }

   public void setSupportsQueryTimeout(boolean value) {
      this.beanTreeNode.setSupportsQueryTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsQueryTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxConstraintNameLength() {
      return this.beanTreeNode.getMaxConstraintNameLength();
   }

   public void setMaxConstraintNameLength(int value) {
      this.beanTreeNode.setMaxConstraintNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxConstraintNameLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLongVarcharTypeName() {
      return this.beanTreeNode.getLongVarcharTypeName();
   }

   public void setLongVarcharTypeName(String value) {
      this.beanTreeNode.setLongVarcharTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LongVarcharTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getLockWaitSeconds() {
      return this.beanTreeNode.getLockWaitSeconds();
   }

   public void setLockWaitSeconds(int value) {
      this.beanTreeNode.setLockWaitSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LockWaitSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDateTypeName() {
      return this.beanTreeNode.getDateTypeName();
   }

   public void setDateTypeName(String value) {
      this.beanTreeNode.setDateTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DateTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsSchemaForGetTables() {
      return this.beanTreeNode.getSupportsSchemaForGetTables();
   }

   public void setSupportsSchemaForGetTables(boolean value) {
      this.beanTreeNode.setSupportsSchemaForGetTables(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSchemaForGetTables", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCatalogSeparator() {
      return this.beanTreeNode.getCatalogSeparator();
   }

   public void setCatalogSeparator(String value) {
      this.beanTreeNode.setCatalogSeparator(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CatalogSeparator", (Object)null, (Object)null));
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

   public int getMaxColumnNameLength() {
      return this.beanTreeNode.getMaxColumnNameLength();
   }

   public void setMaxColumnNameLength(int value) {
      this.beanTreeNode.setMaxColumnNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxColumnNameLength", (Object)null, (Object)null));
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

   public boolean getUseGetStringForClobs() {
      return this.beanTreeNode.getUseGetStringForClobs();
   }

   public void setUseGetStringForClobs(boolean value) {
      this.beanTreeNode.setUseGetStringForClobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseGetStringForClobs", (Object)null, (Object)null));
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

   public String getBitTypeName() {
      return this.beanTreeNode.getBitTypeName();
   }

   public void setBitTypeName(String value) {
      this.beanTreeNode.setBitTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BitTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getLockModeEnabled() {
      return this.beanTreeNode.getLockModeEnabled();
   }

   public void setLockModeEnabled(boolean value) {
      this.beanTreeNode.setLockModeEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LockModeEnabled", (Object)null, (Object)null));
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

   public String getConstraintNameMode() {
      return this.beanTreeNode.getConstraintNameMode();
   }

   public void setConstraintNameMode(String value) {
      this.beanTreeNode.setConstraintNameMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConstraintNameMode", (Object)null, (Object)null));
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

   public String getValidationSQL() {
      return this.beanTreeNode.getValidationSQL();
   }

   public void setValidationSQL(String value) {
      this.beanTreeNode.setValidationSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ValidationSQL", (Object)null, (Object)null));
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

   public String getFloatTypeName() {
      return this.beanTreeNode.getFloatTypeName();
   }

   public void setFloatTypeName(String value) {
      this.beanTreeNode.setFloatTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FloatTypeName", (Object)null, (Object)null));
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

   public boolean getSupportsLockingWithOrderClause() {
      return this.beanTreeNode.getSupportsLockingWithOrderClause();
   }

   public void setSupportsLockingWithOrderClause(boolean value) {
      this.beanTreeNode.setSupportsLockingWithOrderClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithOrderClause", (Object)null, (Object)null));
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

   public boolean getSwapSchemaAndCatalog() {
      return this.beanTreeNode.getSwapSchemaAndCatalog();
   }

   public void setSwapSchemaAndCatalog(boolean value) {
      this.beanTreeNode.setSwapSchemaAndCatalog(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SwapSchemaAndCatalog", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxIndexNameLength() {
      return this.beanTreeNode.getMaxIndexNameLength();
   }

   public void setMaxIndexNameLength(int value) {
      this.beanTreeNode.setMaxIndexNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxIndexNameLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxTableNameLength() {
      return this.beanTreeNode.getMaxTableNameLength();
   }

   public void setMaxTableNameLength(int value) {
      this.beanTreeNode.setMaxTableNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxTableNameLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsSchemaForGetColumns() {
      return this.beanTreeNode.getSupportsSchemaForGetColumns();
   }

   public void setSupportsSchemaForGetColumns(boolean value) {
      this.beanTreeNode.setSupportsSchemaForGetColumns(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSchemaForGetColumns", (Object)null, (Object)null));
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

   public String getTimestampTypeName() {
      return this.beanTreeNode.getTimestampTypeName();
   }

   public void setTimestampTypeName(String value) {
      this.beanTreeNode.setTimestampTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimestampTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
