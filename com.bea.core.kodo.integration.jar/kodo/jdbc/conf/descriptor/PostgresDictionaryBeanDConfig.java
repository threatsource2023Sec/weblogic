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

public class PostgresDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PostgresDictionaryBean beanTreeNode;

   public PostgresDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PostgresDictionaryBean)btn;
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

   public String getBinaryTypeName() {
      return this.beanTreeNode.getBinaryTypeName();
   }

   public void setBinaryTypeName(String value) {
      this.beanTreeNode.setBinaryTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BinaryTypeName", (Object)null, (Object)null));
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

   public boolean getUseSetBytesForBlobs() {
      return this.beanTreeNode.getUseSetBytesForBlobs();
   }

   public void setUseSetBytesForBlobs(boolean value) {
      this.beanTreeNode.setUseSetBytesForBlobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSetBytesForBlobs", (Object)null, (Object)null));
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

   public String getAllSequencesFromOneSchemaSQL() {
      return this.beanTreeNode.getAllSequencesFromOneSchemaSQL();
   }

   public void setAllSequencesFromOneSchemaSQL(String value) {
      this.beanTreeNode.setAllSequencesFromOneSchemaSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllSequencesFromOneSchemaSQL", (Object)null, (Object)null));
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

   public String getNextSequenceQuery() {
      return this.beanTreeNode.getNextSequenceQuery();
   }

   public void setNextSequenceQuery(String value) {
      this.beanTreeNode.setNextSequenceQuery(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NextSequenceQuery", (Object)null, (Object)null));
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

   public boolean getSupportsSetFetchSize() {
      return this.beanTreeNode.getSupportsSetFetchSize();
   }

   public void setSupportsSetFetchSize(boolean value) {
      this.beanTreeNode.setSupportsSetFetchSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSetFetchSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSchemaCase() {
      return this.beanTreeNode.getSchemaCase();
   }

   public void setSchemaCase(String value) {
      this.beanTreeNode.setSchemaCase(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SchemaCase", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAllSequencesSQL() {
      return this.beanTreeNode.getAllSequencesSQL();
   }

   public void setAllSequencesSQL(String value) {
      this.beanTreeNode.setAllSequencesSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllSequencesSQL", (Object)null, (Object)null));
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

   public int getDatePrecision() {
      return this.beanTreeNode.getDatePrecision();
   }

   public void setDatePrecision(int value) {
      this.beanTreeNode.setDatePrecision(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DatePrecision", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsAlterTableWithDropColumn() {
      return this.beanTreeNode.getSupportsAlterTableWithDropColumn();
   }

   public void setSupportsAlterTableWithDropColumn(boolean value) {
      this.beanTreeNode.setSupportsAlterTableWithDropColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsAlterTableWithDropColumn", (Object)null, (Object)null));
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

   public String getNamedSequencesFromAllSchemasSQL() {
      return this.beanTreeNode.getNamedSequencesFromAllSchemasSQL();
   }

   public void setNamedSequencesFromAllSchemasSQL(String value) {
      this.beanTreeNode.setNamedSequencesFromAllSchemasSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NamedSequencesFromAllSchemasSQL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsSelectEndIndex() {
      return this.beanTreeNode.getSupportsSelectEndIndex();
   }

   public void setSupportsSelectEndIndex(boolean value) {
      this.beanTreeNode.setSupportsSelectEndIndex(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSelectEndIndex", (Object)null, (Object)null));
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

   public boolean getAllowsAliasInBulkClause() {
      return this.beanTreeNode.getAllowsAliasInBulkClause();
   }

   public void setAllowsAliasInBulkClause(boolean value) {
      this.beanTreeNode.setAllowsAliasInBulkClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllowsAliasInBulkClause", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNamedSequenceFromOneSchemaSQL() {
      return this.beanTreeNode.getNamedSequenceFromOneSchemaSQL();
   }

   public void setNamedSequenceFromOneSchemaSQL(String value) {
      this.beanTreeNode.setNamedSequenceFromOneSchemaSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NamedSequenceFromOneSchemaSQL", (Object)null, (Object)null));
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

   public int getMaxAutoAssignNameLength() {
      return this.beanTreeNode.getMaxAutoAssignNameLength();
   }

   public void setMaxAutoAssignNameLength(int value) {
      this.beanTreeNode.setMaxAutoAssignNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxAutoAssignNameLength", (Object)null, (Object)null));
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

   public String getVarcharTypeName() {
      return this.beanTreeNode.getVarcharTypeName();
   }

   public void setVarcharTypeName(String value) {
      this.beanTreeNode.setVarcharTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VarcharTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRangePosition() {
      return this.beanTreeNode.getRangePosition();
   }

   public void setRangePosition(int value) {
      this.beanTreeNode.setRangePosition(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RangePosition", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getUseGetBytesForBlobs() {
      return this.beanTreeNode.getUseGetBytesForBlobs();
   }

   public void setUseGetBytesForBlobs(boolean value) {
      this.beanTreeNode.setUseGetBytesForBlobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseGetBytesForBlobs", (Object)null, (Object)null));
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

   public boolean getUseSetStringForClobs() {
      return this.beanTreeNode.getUseSetStringForClobs();
   }

   public void setUseSetStringForClobs(boolean value) {
      this.beanTreeNode.setUseSetStringForClobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSetStringForClobs", (Object)null, (Object)null));
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

   public boolean getSupportsLockingWithOuterJoin() {
      return this.beanTreeNode.getSupportsLockingWithOuterJoin();
   }

   public void setSupportsLockingWithOuterJoin(boolean value) {
      this.beanTreeNode.setSupportsLockingWithOuterJoin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithOuterJoin", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsNullTableForGetImportedKeys() {
      return this.beanTreeNode.getSupportsNullTableForGetImportedKeys();
   }

   public void setSupportsNullTableForGetImportedKeys(boolean value) {
      this.beanTreeNode.setSupportsNullTableForGetImportedKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullTableForGetImportedKeys", (Object)null, (Object)null));
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

   public int getMaxIndexNameLength() {
      return this.beanTreeNode.getMaxIndexNameLength();
   }

   public void setMaxIndexNameLength(int value) {
      this.beanTreeNode.setMaxIndexNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxIndexNameLength", (Object)null, (Object)null));
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

   public String getVarbinaryTypeName() {
      return this.beanTreeNode.getVarbinaryTypeName();
   }

   public void setVarbinaryTypeName(String value) {
      this.beanTreeNode.setVarbinaryTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VarbinaryTypeName", (Object)null, (Object)null));
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

   public boolean getSupportsSelectStartIndex() {
      return this.beanTreeNode.getSupportsSelectStartIndex();
   }

   public void setSupportsSelectStartIndex(boolean value) {
      this.beanTreeNode.setSupportsSelectStartIndex(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSelectStartIndex", (Object)null, (Object)null));
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
