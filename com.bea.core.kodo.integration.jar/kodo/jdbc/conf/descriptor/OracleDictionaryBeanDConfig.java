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

public class OracleDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private OracleDictionaryBean beanTreeNode;

   public OracleDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (OracleDictionaryBean)btn;
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

   public boolean getSupportsLockingWithDistinctClause() {
      return this.beanTreeNode.getSupportsLockingWithDistinctClause();
   }

   public void setSupportsLockingWithDistinctClause(boolean value) {
      this.beanTreeNode.setSupportsLockingWithDistinctClause(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsLockingWithDistinctClause", (Object)null, (Object)null));
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

   public String getNextSequenceQuery() {
      return this.beanTreeNode.getNextSequenceQuery();
   }

   public void setNextSequenceQuery(String value) {
      this.beanTreeNode.setNextSequenceQuery(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NextSequenceQuery", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getUseSetFormOfUseForUnicode() {
      return this.beanTreeNode.getUseSetFormOfUseForUnicode();
   }

   public void setUseSetFormOfUseForUnicode(boolean value) {
      this.beanTreeNode.setUseSetFormOfUseForUnicode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSetFormOfUseForUnicode", (Object)null, (Object)null));
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

   public int getMaxEmbeddedClobSize() {
      return this.beanTreeNode.getMaxEmbeddedClobSize();
   }

   public void setMaxEmbeddedClobSize(int value) {
      this.beanTreeNode.setMaxEmbeddedClobSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxEmbeddedClobSize", (Object)null, (Object)null));
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

   public int getMaxTableNameLength() {
      return this.beanTreeNode.getMaxTableNameLength();
   }

   public void setMaxTableNameLength(int value) {
      this.beanTreeNode.setMaxTableNameLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxTableNameLength", (Object)null, (Object)null));
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

   public String getBitTypeName() {
      return this.beanTreeNode.getBitTypeName();
   }

   public void setBitTypeName(String value) {
      this.beanTreeNode.setBitTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BitTypeName", (Object)null, (Object)null));
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

   public String getVarcharTypeName() {
      return this.beanTreeNode.getVarcharTypeName();
   }

   public void setVarcharTypeName(String value) {
      this.beanTreeNode.setVarcharTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VarcharTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getUseTriggersForAutoAssign() {
      return this.beanTreeNode.getUseTriggersForAutoAssign();
   }

   public void setUseTriggersForAutoAssign(boolean value) {
      this.beanTreeNode.setUseTriggersForAutoAssign(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseTriggersForAutoAssign", (Object)null, (Object)null));
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

   public String getIntegerTypeName() {
      return this.beanTreeNode.getIntegerTypeName();
   }

   public void setIntegerTypeName(String value) {
      this.beanTreeNode.setIntegerTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IntegerTypeName", (Object)null, (Object)null));
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

   public boolean getOpenjpa3GeneratedKeyNames() {
      return this.beanTreeNode.getOpenjpa3GeneratedKeyNames();
   }

   public void setOpenjpa3GeneratedKeyNames(boolean value) {
      this.beanTreeNode.setOpenjpa3GeneratedKeyNames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Openjpa3GeneratedKeyNames", (Object)null, (Object)null));
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

   public boolean getSupportsDeferredConstraints() {
      return this.beanTreeNode.getSupportsDeferredConstraints();
   }

   public void setSupportsDeferredConstraints(boolean value) {
      this.beanTreeNode.setSupportsDeferredConstraints(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SupportsDeferredConstraints", (Object)null, (Object)null));
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

   public String getAutoAssignSequenceName() {
      return this.beanTreeNode.getAutoAssignSequenceName();
   }

   public void setAutoAssignSequenceName(String value) {
      this.beanTreeNode.setAutoAssignSequenceName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AutoAssignSequenceName", (Object)null, (Object)null));
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
}
