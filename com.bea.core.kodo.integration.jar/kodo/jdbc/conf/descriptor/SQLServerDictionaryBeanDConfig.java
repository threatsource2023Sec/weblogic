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

public class SQLServerDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SQLServerDictionaryBean beanTreeNode;

   public SQLServerDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SQLServerDictionaryBean)btn;
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

   public String getConcatenateFunction() {
      return this.beanTreeNode.getConcatenateFunction();
   }

   public void setConcatenateFunction(String value) {
      this.beanTreeNode.setConcatenateFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConcatenateFunction", (Object)null, (Object)null));
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

   public boolean getSupportsModOperator() {
      return this.beanTreeNode.getSupportsModOperator();
   }

   public void setSupportsModOperator(boolean value) {
      this.beanTreeNode.setSupportsModOperator(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsModOperator", (Object)null, (Object)null));
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

   public String getLongVarcharTypeName() {
      return this.beanTreeNode.getLongVarcharTypeName();
   }

   public void setLongVarcharTypeName(String value) {
      this.beanTreeNode.setLongVarcharTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LongVarcharTypeName", (Object)null, (Object)null));
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

   public String getCurrentTimeFunction() {
      return this.beanTreeNode.getCurrentTimeFunction();
   }

   public void setCurrentTimeFunction(String value) {
      this.beanTreeNode.setCurrentTimeFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CurrentTimeFunction", (Object)null, (Object)null));
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

   public boolean getSupportsNullTableForGetColumns() {
      return this.beanTreeNode.getSupportsNullTableForGetColumns();
   }

   public void setSupportsNullTableForGetColumns(boolean value) {
      this.beanTreeNode.setSupportsNullTableForGetColumns(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullTableForGetColumns", (Object)null, (Object)null));
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

   public int getRangePosition() {
      return this.beanTreeNode.getRangePosition();
   }

   public void setRangePosition(int value) {
      this.beanTreeNode.setRangePosition(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RangePosition", (Object)null, (Object)null));
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

   public boolean getUniqueIdentifierAsVarbinary() {
      return this.beanTreeNode.getUniqueIdentifierAsVarbinary();
   }

   public void setUniqueIdentifierAsVarbinary(boolean value) {
      this.beanTreeNode.setUniqueIdentifierAsVarbinary(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UniqueIdentifierAsVarbinary", (Object)null, (Object)null));
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

   public String getCurrentTimestampFunction() {
      return this.beanTreeNode.getCurrentTimestampFunction();
   }

   public void setCurrentTimestampFunction(String value) {
      this.beanTreeNode.setCurrentTimestampFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CurrentTimestampFunction", (Object)null, (Object)null));
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

   public String getTrimBothFunction() {
      return this.beanTreeNode.getTrimBothFunction();
   }

   public void setTrimBothFunction(String value) {
      this.beanTreeNode.setTrimBothFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TrimBothFunction", (Object)null, (Object)null));
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

   public String getTimeTypeName() {
      return this.beanTreeNode.getTimeTypeName();
   }

   public void setTimeTypeName(String value) {
      this.beanTreeNode.setTimeTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeTypeName", (Object)null, (Object)null));
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
}
