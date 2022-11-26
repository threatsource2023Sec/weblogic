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

public class MySQLDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MySQLDictionaryBean beanTreeNode;

   public MySQLDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MySQLDictionaryBean)btn;
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

   public boolean getUseClobs() {
      return this.beanTreeNode.getUseClobs();
   }

   public void setUseClobs(boolean value) {
      this.beanTreeNode.setUseClobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseClobs", (Object)null, (Object)null));
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

   public int getMaxConstraintNameLength() {
      return this.beanTreeNode.getMaxConstraintNameLength();
   }

   public void setMaxConstraintNameLength(int value) {
      this.beanTreeNode.setMaxConstraintNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxConstraintNameLength", (Object)null, (Object)null));
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

   public String getLongVarcharTypeName() {
      return this.beanTreeNode.getLongVarcharTypeName();
   }

   public void setLongVarcharTypeName(String value) {
      this.beanTreeNode.setLongVarcharTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LongVarcharTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTableType() {
      return this.beanTreeNode.getTableType();
   }

   public void setTableType(String value) {
      this.beanTreeNode.setTableType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TableType", (Object)null, (Object)null));
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

   public int getMaxColumnNameLength() {
      return this.beanTreeNode.getMaxColumnNameLength();
   }

   public void setMaxColumnNameLength(int value) {
      this.beanTreeNode.setMaxColumnNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxColumnNameLength", (Object)null, (Object)null));
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

   public boolean getSupportsMultipleNontransactionalResultSets() {
      return this.beanTreeNode.getSupportsMultipleNontransactionalResultSets();
   }

   public void setSupportsMultipleNontransactionalResultSets(boolean value) {
      this.beanTreeNode.setSupportsMultipleNontransactionalResultSets(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsMultipleNontransactionalResultSets", (Object)null, (Object)null));
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

   public int getMaxIndexesPerTable() {
      return this.beanTreeNode.getMaxIndexesPerTable();
   }

   public void setMaxIndexesPerTable(int value) {
      this.beanTreeNode.setMaxIndexesPerTable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxIndexesPerTable", (Object)null, (Object)null));
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

   public boolean getSupportsSelectStartIndex() {
      return this.beanTreeNode.getSupportsSelectStartIndex();
   }

   public void setSupportsSelectStartIndex(boolean value) {
      this.beanTreeNode.setSupportsSelectStartIndex(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSelectStartIndex", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getDriverDeserializesBlobs() {
      return this.beanTreeNode.getDriverDeserializesBlobs();
   }

   public void setDriverDeserializesBlobs(boolean value) {
      this.beanTreeNode.setDriverDeserializesBlobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DriverDeserializesBlobs", (Object)null, (Object)null));
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
