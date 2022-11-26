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

public class DB2DictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DB2DictionaryBean beanTreeNode;

   public DB2DictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DB2DictionaryBean)btn;
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

   public boolean getSupportsLockingWithSelectRange() {
      return this.beanTreeNode.getSupportsLockingWithSelectRange();
   }

   public void setSupportsLockingWithSelectRange(boolean value) {
      this.beanTreeNode.setSupportsLockingWithSelectRange(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithSelectRange", (Object)null, (Object)null));
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

   public boolean getSupportsAlterTableWithDropColumn() {
      return this.beanTreeNode.getSupportsAlterTableWithDropColumn();
   }

   public void setSupportsAlterTableWithDropColumn(boolean value) {
      this.beanTreeNode.setSupportsAlterTableWithDropColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsAlterTableWithDropColumn", (Object)null, (Object)null));
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

   public String getValidationSQL() {
      return this.beanTreeNode.getValidationSQL();
   }

   public void setValidationSQL(String value) {
      this.beanTreeNode.setValidationSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ValidationSQL", (Object)null, (Object)null));
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

   public String getNumericTypeName() {
      return this.beanTreeNode.getNumericTypeName();
   }

   public void setNumericTypeName(String value) {
      this.beanTreeNode.setNumericTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NumericTypeName", (Object)null, (Object)null));
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

   public boolean getSupportsLockingWithOuterJoin() {
      return this.beanTreeNode.getSupportsLockingWithOuterJoin();
   }

   public void setSupportsLockingWithOuterJoin(boolean value) {
      this.beanTreeNode.setSupportsLockingWithOuterJoin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithOuterJoin", (Object)null, (Object)null));
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

   public boolean getRequiresAliasForSubselect() {
      return this.beanTreeNode.getRequiresAliasForSubselect();
   }

   public void setRequiresAliasForSubselect(boolean value) {
      this.beanTreeNode.setRequiresAliasForSubselect(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresAliasForSubselect", (Object)null, (Object)null));
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

   public boolean getSupportsLockingWithInnerJoin() {
      return this.beanTreeNode.getSupportsLockingWithInnerJoin();
   }

   public void setSupportsLockingWithInnerJoin(boolean value) {
      this.beanTreeNode.setSupportsLockingWithInnerJoin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithInnerJoin", (Object)null, (Object)null));
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

   public String getVarbinaryTypeName() {
      return this.beanTreeNode.getVarbinaryTypeName();
   }

   public void setVarbinaryTypeName(String value) {
      this.beanTreeNode.setVarbinaryTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VarbinaryTypeName", (Object)null, (Object)null));
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

   public String getToLowerCaseFunction() {
      return this.beanTreeNode.getToLowerCaseFunction();
   }

   public void setToLowerCaseFunction(String value) {
      this.beanTreeNode.setToLowerCaseFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ToLowerCaseFunction", (Object)null, (Object)null));
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

   public boolean getRequiresAutoCommitForMetaData() {
      return this.beanTreeNode.getRequiresAutoCommitForMetaData();
   }

   public void setRequiresAutoCommitForMetaData(boolean value) {
      this.beanTreeNode.setRequiresAutoCommitForMetaData(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresAutoCommitForMetaData", (Object)null, (Object)null));
      this.setModified(true);
   }
}
