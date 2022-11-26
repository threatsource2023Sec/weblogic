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

public class BuiltInDBDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private BuiltInDBDictionaryBean beanTreeNode;

   public BuiltInDBDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (BuiltInDBDictionaryBean)btn;
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

   public String getOuterJoinClause() {
      return this.beanTreeNode.getOuterJoinClause();
   }

   public void setOuterJoinClause(String value) {
      this.beanTreeNode.setOuterJoinClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OuterJoinClause", (Object)null, (Object)null));
      this.setModified(true);
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

   public boolean getSimulateLocking() {
      return this.beanTreeNode.getSimulateLocking();
   }

   public void setSimulateLocking(boolean value) {
      this.beanTreeNode.setSimulateLocking(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SimulateLocking", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSystemTables() {
      return this.beanTreeNode.getSystemTables();
   }

   public void setSystemTables(String value) {
      this.beanTreeNode.setSystemTables(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SystemTables", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConcatenateFunction() {
      return this.beanTreeNode.getConcatenateFunction();
   }

   public void setConcatenateFunction(String value) {
      this.beanTreeNode.setConcatenateFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConcatenateFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSubstringFunctionName() {
      return this.beanTreeNode.getSubstringFunctionName();
   }

   public void setSubstringFunctionName(String value) {
      this.beanTreeNode.setSubstringFunctionName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SubstringFunctionName", (Object)null, (Object)null));
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

   public String getSearchStringEscape() {
      return this.beanTreeNode.getSearchStringEscape();
   }

   public void setSearchStringEscape(String value) {
      this.beanTreeNode.setSearchStringEscape(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SearchStringEscape", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsCascadeUpdateAction() {
      return this.beanTreeNode.getSupportsCascadeUpdateAction();
   }

   public void setSupportsCascadeUpdateAction(boolean value) {
      this.beanTreeNode.setSupportsCascadeUpdateAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsCascadeUpdateAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getStringLengthFunction() {
      return this.beanTreeNode.getStringLengthFunction();
   }

   public void setStringLengthFunction(String value) {
      this.beanTreeNode.setStringLengthFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StringLengthFunction", (Object)null, (Object)null));
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

   public boolean getSupportsUniqueConstraints() {
      return this.beanTreeNode.getSupportsUniqueConstraints();
   }

   public void setSupportsUniqueConstraints(boolean value) {
      this.beanTreeNode.setSupportsUniqueConstraints(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsUniqueConstraints", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsRestrictDeleteAction() {
      return this.beanTreeNode.getSupportsRestrictDeleteAction();
   }

   public void setSupportsRestrictDeleteAction(boolean value) {
      this.beanTreeNode.setSupportsRestrictDeleteAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsRestrictDeleteAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTrimLeadingFunction() {
      return this.beanTreeNode.getTrimLeadingFunction();
   }

   public void setTrimLeadingFunction(String value) {
      this.beanTreeNode.setTrimLeadingFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TrimLeadingFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsDefaultDeleteAction() {
      return this.beanTreeNode.getSupportsDefaultDeleteAction();
   }

   public void setSupportsDefaultDeleteAction(boolean value) {
      this.beanTreeNode.setSupportsDefaultDeleteAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsDefaultDeleteAction", (Object)null, (Object)null));
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

   public String getCrossJoinClause() {
      return this.beanTreeNode.getCrossJoinClause();
   }

   public void setCrossJoinClause(String value) {
      this.beanTreeNode.setCrossJoinClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CrossJoinClause", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxEmbeddedClobSize() {
      return this.beanTreeNode.getMaxEmbeddedClobSize();
   }

   public void setMaxEmbeddedClobSize(int value) {
      this.beanTreeNode.setMaxEmbeddedClobSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxEmbeddedClobSize", (Object)null, (Object)null));
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

   public boolean getSupportsAlterTableWithDropColumn() {
      return this.beanTreeNode.getSupportsAlterTableWithDropColumn();
   }

   public void setSupportsAlterTableWithDropColumn(boolean value) {
      this.beanTreeNode.setSupportsAlterTableWithDropColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsAlterTableWithDropColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCurrentTimeFunction() {
      return this.beanTreeNode.getCurrentTimeFunction();
   }

   public void setCurrentTimeFunction(String value) {
      this.beanTreeNode.setCurrentTimeFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CurrentTimeFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRequiresConditionForCrossJoin() {
      return this.beanTreeNode.getRequiresConditionForCrossJoin();
   }

   public void setRequiresConditionForCrossJoin(boolean value) {
      this.beanTreeNode.setRequiresConditionForCrossJoin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresConditionForCrossJoin", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRefTypeName() {
      return this.beanTreeNode.getRefTypeName();
   }

   public void setRefTypeName(String value) {
      this.beanTreeNode.setRefTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RefTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConcatenateDelimiter() {
      return this.beanTreeNode.getConcatenateDelimiter();
   }

   public void setConcatenateDelimiter(String value) {
      this.beanTreeNode.setConcatenateDelimiter(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConcatenateDelimiter", (Object)null, (Object)null));
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

   public boolean getSupportsModOperator() {
      return this.beanTreeNode.getSupportsModOperator();
   }

   public void setSupportsModOperator(boolean value) {
      this.beanTreeNode.setSupportsModOperator(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsModOperator", (Object)null, (Object)null));
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

   public String getJavaObjectTypeName() {
      return this.beanTreeNode.getJavaObjectTypeName();
   }

   public void setJavaObjectTypeName(String value) {
      this.beanTreeNode.setJavaObjectTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JavaObjectTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDriverVendor() {
      return this.beanTreeNode.getDriverVendor();
   }

   public void setDriverVendor(String value) {
      this.beanTreeNode.setDriverVendor(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DriverVendor", (Object)null, (Object)null));
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

   public String getDecimalTypeName() {
      return this.beanTreeNode.getDecimalTypeName();
   }

   public void setDecimalTypeName(String value) {
      this.beanTreeNode.setDecimalTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DecimalTypeName", (Object)null, (Object)null));
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

   public boolean getSupportsAlterTableWithAddColumn() {
      return this.beanTreeNode.getSupportsAlterTableWithAddColumn();
   }

   public void setSupportsAlterTableWithAddColumn(boolean value) {
      this.beanTreeNode.setSupportsAlterTableWithAddColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsAlterTableWithAddColumn", (Object)null, (Object)null));
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

   public boolean getSupportsNullTableForGetColumns() {
      return this.beanTreeNode.getSupportsNullTableForGetColumns();
   }

   public void setSupportsNullTableForGetColumns(boolean value) {
      this.beanTreeNode.setSupportsNullTableForGetColumns(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullTableForGetColumns", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getToUpperCaseFunction() {
      return this.beanTreeNode.getToUpperCaseFunction();
   }

   public void setToUpperCaseFunction(String value) {
      this.beanTreeNode.setToUpperCaseFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ToUpperCaseFunction", (Object)null, (Object)null));
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

   public boolean getStoreLargeNumbersAsStrings() {
      return this.beanTreeNode.getStoreLargeNumbersAsStrings();
   }

   public void setStoreLargeNumbersAsStrings(boolean value) {
      this.beanTreeNode.setStoreLargeNumbersAsStrings(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StoreLargeNumbersAsStrings", (Object)null, (Object)null));
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

   public boolean getAllowsAliasInBulkClause() {
      return this.beanTreeNode.getAllowsAliasInBulkClause();
   }

   public void setAllowsAliasInBulkClause(boolean value) {
      this.beanTreeNode.setAllowsAliasInBulkClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllowsAliasInBulkClause", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsSelectForUpdate() {
      return this.beanTreeNode.getSupportsSelectForUpdate();
   }

   public void setSupportsSelectForUpdate(boolean value) {
      this.beanTreeNode.setSupportsSelectForUpdate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSelectForUpdate", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDistinctCountColumnSeparator() {
      return this.beanTreeNode.getDistinctCountColumnSeparator();
   }

   public void setDistinctCountColumnSeparator(String value) {
      this.beanTreeNode.setDistinctCountColumnSeparator(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DistinctCountColumnSeparator", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsSubselect() {
      return this.beanTreeNode.getSupportsSubselect();
   }

   public void setSupportsSubselect(boolean value) {
      this.beanTreeNode.setSupportsSubselect(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSubselect", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTimeTypeName() {
      return this.beanTreeNode.getTimeTypeName();
   }

   public void setTimeTypeName(String value) {
      this.beanTreeNode.setTimeTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeTypeName", (Object)null, (Object)null));
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

   public boolean getUseGetObjectForBlobs() {
      return this.beanTreeNode.getUseGetObjectForBlobs();
   }

   public void setUseGetObjectForBlobs(boolean value) {
      this.beanTreeNode.setUseGetObjectForBlobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseGetObjectForBlobs", (Object)null, (Object)null));
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

   public String getStructTypeName() {
      return this.beanTreeNode.getStructTypeName();
   }

   public void setStructTypeName(String value) {
      this.beanTreeNode.setStructTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StructTypeName", (Object)null, (Object)null));
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

   public boolean getSupportsRestrictUpdateAction() {
      return this.beanTreeNode.getSupportsRestrictUpdateAction();
   }

   public void setSupportsRestrictUpdateAction(boolean value) {
      this.beanTreeNode.setSupportsRestrictUpdateAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsRestrictUpdateAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAutoAssignClause() {
      return this.beanTreeNode.getAutoAssignClause();
   }

   public void setAutoAssignClause(String value) {
      this.beanTreeNode.setAutoAssignClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AutoAssignClause", (Object)null, (Object)null));
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

   public String getBitLengthFunction() {
      return this.beanTreeNode.getBitLengthFunction();
   }

   public void setBitLengthFunction(String value) {
      this.beanTreeNode.setBitLengthFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BitLengthFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getCreatePrimaryKeys() {
      return this.beanTreeNode.getCreatePrimaryKeys();
   }

   public void setCreatePrimaryKeys(boolean value) {
      this.beanTreeNode.setCreatePrimaryKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CreatePrimaryKeys", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNullTypeName() {
      return this.beanTreeNode.getNullTypeName();
   }

   public void setNullTypeName(String value) {
      this.beanTreeNode.setNullTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NullTypeName", (Object)null, (Object)null));
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

   public boolean getUseGetBytesForBlobs() {
      return this.beanTreeNode.getUseGetBytesForBlobs();
   }

   public void setUseGetBytesForBlobs(boolean value) {
      this.beanTreeNode.setUseGetBytesForBlobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseGetBytesForBlobs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTableTypes() {
      return this.beanTreeNode.getTableTypes();
   }

   public void setTableTypes(String value) {
      this.beanTreeNode.setTableTypes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TableTypes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNumericTypeName() {
      return this.beanTreeNode.getNumericTypeName();
   }

   public void setNumericTypeName(String value) {
      this.beanTreeNode.setNumericTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NumericTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTableForUpdateClause() {
      return this.beanTreeNode.getTableForUpdateClause();
   }

   public void setTableForUpdateClause(String value) {
      this.beanTreeNode.setTableForUpdateClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TableForUpdateClause", (Object)null, (Object)null));
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

   public String getForUpdateClause() {
      return this.beanTreeNode.getForUpdateClause();
   }

   public void setForUpdateClause(String value) {
      this.beanTreeNode.setForUpdateClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ForUpdateClause", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBooleanTypeName() {
      return this.beanTreeNode.getBooleanTypeName();
   }

   public void setBooleanTypeName(String value) {
      this.beanTreeNode.setBooleanTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BooleanTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getUseGetBestRowIdentifierForPrimaryKeys() {
      return this.beanTreeNode.getUseGetBestRowIdentifierForPrimaryKeys();
   }

   public void setUseGetBestRowIdentifierForPrimaryKeys(boolean value) {
      this.beanTreeNode.setUseGetBestRowIdentifierForPrimaryKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseGetBestRowIdentifierForPrimaryKeys", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsForeignKeys() {
      return this.beanTreeNode.getSupportsForeignKeys();
   }

   public void setSupportsForeignKeys(boolean value) {
      this.beanTreeNode.setSupportsForeignKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsForeignKeys", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDropTableSQL() {
      return this.beanTreeNode.getDropTableSQL();
   }

   public void setDropTableSQL(String value) {
      this.beanTreeNode.setDropTableSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DropTableSQL", (Object)null, (Object)null));
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

   public String getFixedSizeTypeNames() {
      return this.beanTreeNode.getFixedSizeTypeNames();
   }

   public void setFixedSizeTypeNames(String value) {
      this.beanTreeNode.setFixedSizeTypeNames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FixedSizeTypeNames", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStoreCharsAsNumbers() {
      return this.beanTreeNode.getStoreCharsAsNumbers();
   }

   public void setStoreCharsAsNumbers(boolean value) {
      this.beanTreeNode.setStoreCharsAsNumbers(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StoreCharsAsNumbers", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxIndexesPerTable() {
      return this.beanTreeNode.getMaxIndexesPerTable();
   }

   public void setMaxIndexesPerTable(int value) {
      this.beanTreeNode.setMaxIndexesPerTable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxIndexesPerTable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRequiresCastForComparisons() {
      return this.beanTreeNode.getRequiresCastForComparisons();
   }

   public void setRequiresCastForComparisons(boolean value) {
      this.beanTreeNode.setRequiresCastForComparisons(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresCastForComparisons", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsHaving() {
      return this.beanTreeNode.getSupportsHaving();
   }

   public void setSupportsHaving(boolean value) {
      this.beanTreeNode.setSupportsHaving(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsHaving", (Object)null, (Object)null));
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

   public boolean getSupportsCorrelatedSubselect() {
      return this.beanTreeNode.getSupportsCorrelatedSubselect();
   }

   public void setSupportsCorrelatedSubselect(boolean value) {
      this.beanTreeNode.setSupportsCorrelatedSubselect(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsCorrelatedSubselect", (Object)null, (Object)null));
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

   public String getReservedWords() {
      return this.beanTreeNode.getReservedWords();
   }

   public void setReservedWords(String value) {
      this.beanTreeNode.setReservedWords(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReservedWords", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsNullUpdateAction() {
      return this.beanTreeNode.getSupportsNullUpdateAction();
   }

   public void setSupportsNullUpdateAction(boolean value) {
      this.beanTreeNode.setSupportsNullUpdateAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullUpdateAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getUseSchemaName() {
      return this.beanTreeNode.getUseSchemaName();
   }

   public void setUseSchemaName(boolean value) {
      this.beanTreeNode.setUseSchemaName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSchemaName", (Object)null, (Object)null));
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

   public boolean getSupportsNullTableForGetIndexInfo() {
      return this.beanTreeNode.getSupportsNullTableForGetIndexInfo();
   }

   public void setSupportsNullTableForGetIndexInfo(boolean value) {
      this.beanTreeNode.setSupportsNullTableForGetIndexInfo(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullTableForGetIndexInfo", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTrimTrailingFunction() {
      return this.beanTreeNode.getTrimTrailingFunction();
   }

   public void setTrimTrailingFunction(String value) {
      this.beanTreeNode.setTrimTrailingFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TrimTrailingFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsLockingWithSelectRange() {
      return this.beanTreeNode.getSupportsLockingWithSelectRange();
   }

   public void setSupportsLockingWithSelectRange(boolean value) {
      this.beanTreeNode.setSupportsLockingWithSelectRange(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithSelectRange", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStorageLimitationsFatal() {
      return this.beanTreeNode.getStorageLimitationsFatal();
   }

   public void setStorageLimitationsFatal(boolean value) {
      this.beanTreeNode.setStorageLimitationsFatal(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StorageLimitationsFatal", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsLockingWithInnerJoin() {
      return this.beanTreeNode.getSupportsLockingWithInnerJoin();
   }

   public void setSupportsLockingWithInnerJoin(boolean value) {
      this.beanTreeNode.setSupportsLockingWithInnerJoin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithInnerJoin", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCurrentTimestampFunction() {
      return this.beanTreeNode.getCurrentTimestampFunction();
   }

   public void setCurrentTimestampFunction(String value) {
      this.beanTreeNode.setCurrentTimestampFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CurrentTimestampFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCastFunction() {
      return this.beanTreeNode.getCastFunction();
   }

   public void setCastFunction(String value) {
      this.beanTreeNode.setCastFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CastFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOtherTypeName() {
      return this.beanTreeNode.getOtherTypeName();
   }

   public void setOtherTypeName(String value) {
      this.beanTreeNode.setOtherTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OtherTypeName", (Object)null, (Object)null));
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

   public String getDistinctTypeName() {
      return this.beanTreeNode.getDistinctTypeName();
   }

   public void setDistinctTypeName(String value) {
      this.beanTreeNode.setDistinctTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DistinctTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCharacterColumnSize() {
      return this.beanTreeNode.getCharacterColumnSize();
   }

   public void setCharacterColumnSize(int value) {
      this.beanTreeNode.setCharacterColumnSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CharacterColumnSize", (Object)null, (Object)null));
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

   public String getClosePoolSQL() {
      return this.beanTreeNode.getClosePoolSQL();
   }

   public void setClosePoolSQL(String value) {
      this.beanTreeNode.setClosePoolSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClosePoolSQL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCurrentDateFunction() {
      return this.beanTreeNode.getCurrentDateFunction();
   }

   public void setCurrentDateFunction(String value) {
      this.beanTreeNode.setCurrentDateFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CurrentDateFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJoinSyntax() {
      return this.beanTreeNode.getJoinSyntax();
   }

   public void setJoinSyntax(String value) {
      this.beanTreeNode.setJoinSyntax(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JoinSyntax", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxEmbeddedBlobSize() {
      return this.beanTreeNode.getMaxEmbeddedBlobSize();
   }

   public void setMaxEmbeddedBlobSize(int value) {
      this.beanTreeNode.setMaxEmbeddedBlobSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxEmbeddedBlobSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTrimBothFunction() {
      return this.beanTreeNode.getTrimBothFunction();
   }

   public void setTrimBothFunction(String value) {
      this.beanTreeNode.setTrimBothFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TrimBothFunction", (Object)null, (Object)null));
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

   public String getToLowerCaseFunction() {
      return this.beanTreeNode.getToLowerCaseFunction();
   }

   public void setToLowerCaseFunction(String value) {
      this.beanTreeNode.setToLowerCaseFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ToLowerCaseFunction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getArrayTypeName() {
      return this.beanTreeNode.getArrayTypeName();
   }

   public void setArrayTypeName(String value) {
      this.beanTreeNode.setArrayTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ArrayTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getInnerJoinClause() {
      return this.beanTreeNode.getInnerJoinClause();
   }

   public void setInnerJoinClause(String value) {
      this.beanTreeNode.setInnerJoinClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InnerJoinClause", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsDefaultUpdateAction() {
      return this.beanTreeNode.getSupportsDefaultUpdateAction();
   }

   public void setSupportsDefaultUpdateAction(boolean value) {
      this.beanTreeNode.setSupportsDefaultUpdateAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsDefaultUpdateAction", (Object)null, (Object)null));
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

   public boolean getSupportsNullTableForGetPrimaryKeys() {
      return this.beanTreeNode.getSupportsNullTableForGetPrimaryKeys();
   }

   public void setSupportsNullTableForGetPrimaryKeys(boolean value) {
      this.beanTreeNode.setSupportsNullTableForGetPrimaryKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullTableForGetPrimaryKeys", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSystemSchemas() {
      return this.beanTreeNode.getSystemSchemas();
   }

   public void setSystemSchemas(String value) {
      this.beanTreeNode.setSystemSchemas(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SystemSchemas", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRequiresCastForMathFunctions() {
      return this.beanTreeNode.getRequiresCastForMathFunctions();
   }

   public void setRequiresCastForMathFunctions(boolean value) {
      this.beanTreeNode.setRequiresCastForMathFunctions(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresCastForMathFunctions", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsNullDeleteAction() {
      return this.beanTreeNode.getSupportsNullDeleteAction();
   }

   public void setSupportsNullDeleteAction(boolean value) {
      this.beanTreeNode.setSupportsNullDeleteAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullDeleteAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRequiresAutoCommitForMetaData() {
      return this.beanTreeNode.getRequiresAutoCommitForMetaData();
   }

   public void setRequiresAutoCommitForMetaData(boolean value) {
      this.beanTreeNode.setRequiresAutoCommitForMetaData(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresAutoCommitForMetaData", (Object)null, (Object)null));
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

   public String getInitializationSQL() {
      return this.beanTreeNode.getInitializationSQL();
   }

   public void setInitializationSQL(String value) {
      this.beanTreeNode.setInitializationSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitializationSQL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsCascadeDeleteAction() {
      return this.beanTreeNode.getSupportsCascadeDeleteAction();
   }

   public void setSupportsCascadeDeleteAction(boolean value) {
      this.beanTreeNode.setSupportsCascadeDeleteAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsCascadeDeleteAction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getSupportsTimestampNanos() {
      return this.beanTreeNode.getSupportsTimestampNanos();
   }

   public void setSupportsTimestampNanos(boolean value) {
      this.beanTreeNode.setSupportsTimestampNanos(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsTimestampNanos", (Object)null, (Object)null));
      this.setModified(true);
   }
}
