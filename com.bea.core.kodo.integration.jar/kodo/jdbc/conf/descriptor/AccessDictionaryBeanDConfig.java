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

public class AccessDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AccessDictionaryBean beanTreeNode;

   public AccessDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AccessDictionaryBean)btn;
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

   public boolean getUseGetBytesForBlobs() {
      return this.beanTreeNode.getUseGetBytesForBlobs();
   }

   public void setUseGetBytesForBlobs(boolean value) {
      this.beanTreeNode.setUseGetBytesForBlobs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseGetBytesForBlobs", (Object)null, (Object)null));
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

   public String getBlobTypeName() {
      return this.beanTreeNode.getBlobTypeName();
   }

   public void setBlobTypeName(String value) {
      this.beanTreeNode.setBlobTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BlobTypeName", (Object)null, (Object)null));
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

   public String getBigintTypeName() {
      return this.beanTreeNode.getBigintTypeName();
   }

   public void setBigintTypeName(String value) {
      this.beanTreeNode.setBigintTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BigintTypeName", (Object)null, (Object)null));
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

   public int getMaxIndexesPerTable() {
      return this.beanTreeNode.getMaxIndexesPerTable();
   }

   public void setMaxIndexesPerTable(int value) {
      this.beanTreeNode.setMaxIndexesPerTable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxIndexesPerTable", (Object)null, (Object)null));
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

   public String getTinyintTypeName() {
      return this.beanTreeNode.getTinyintTypeName();
   }

   public void setTinyintTypeName(String value) {
      this.beanTreeNode.setTinyintTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TinyintTypeName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
