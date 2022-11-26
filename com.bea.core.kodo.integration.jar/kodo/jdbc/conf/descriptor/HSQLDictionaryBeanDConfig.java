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

public class HSQLDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private HSQLDictionaryBean beanTreeNode;

   public HSQLDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (HSQLDictionaryBean)btn;
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

   public String getCrossJoinClause() {
      return this.beanTreeNode.getCrossJoinClause();
   }

   public void setCrossJoinClause(String value) {
      this.beanTreeNode.setCrossJoinClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CrossJoinClause", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getCacheTables() {
      return this.beanTreeNode.getCacheTables();
   }

   public void setCacheTables(boolean value) {
      this.beanTreeNode.setCacheTables(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheTables", (Object)null, (Object)null));
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

   public String getTrimLeadingFunction() {
      return this.beanTreeNode.getTrimLeadingFunction();
   }

   public void setTrimLeadingFunction(String value) {
      this.beanTreeNode.setTrimLeadingFunction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TrimLeadingFunction", (Object)null, (Object)null));
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

   public boolean getRequiresConditionForCrossJoin() {
      return this.beanTreeNode.getRequiresConditionForCrossJoin();
   }

   public void setRequiresConditionForCrossJoin(boolean value) {
      this.beanTreeNode.setRequiresConditionForCrossJoin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresConditionForCrossJoin", (Object)null, (Object)null));
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

   public boolean getSupportsSelectForUpdate() {
      return this.beanTreeNode.getSupportsSelectForUpdate();
   }

   public void setSupportsSelectForUpdate(boolean value) {
      this.beanTreeNode.setSupportsSelectForUpdate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsSelectForUpdate", (Object)null, (Object)null));
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

   public String getValidationSQL() {
      return this.beanTreeNode.getValidationSQL();
   }

   public void setValidationSQL(String value) {
      this.beanTreeNode.setValidationSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ValidationSQL", (Object)null, (Object)null));
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

   public boolean getRequiresCastForComparisons() {
      return this.beanTreeNode.getRequiresCastForComparisons();
   }

   public void setRequiresCastForComparisons(boolean value) {
      this.beanTreeNode.setRequiresCastForComparisons(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresCastForComparisons", (Object)null, (Object)null));
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

   public String getClosePoolSQL() {
      return this.beanTreeNode.getClosePoolSQL();
   }

   public void setClosePoolSQL(String value) {
      this.beanTreeNode.setClosePoolSQL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClosePoolSQL", (Object)null, (Object)null));
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

   public boolean getSupportsNullTableForGetPrimaryKeys() {
      return this.beanTreeNode.getSupportsNullTableForGetPrimaryKeys();
   }

   public void setSupportsNullTableForGetPrimaryKeys(boolean value) {
      this.beanTreeNode.setSupportsNullTableForGetPrimaryKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsNullTableForGetPrimaryKeys", (Object)null, (Object)null));
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
}
