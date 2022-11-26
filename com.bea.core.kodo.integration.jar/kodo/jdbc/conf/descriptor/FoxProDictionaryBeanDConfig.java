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

public class FoxProDictionaryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private FoxProDictionaryBean beanTreeNode;

   public FoxProDictionaryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (FoxProDictionaryBean)btn;
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

   public String getVarcharTypeName() {
      return this.beanTreeNode.getVarcharTypeName();
   }

   public void setVarcharTypeName(String value) {
      this.beanTreeNode.setVarcharTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VarcharTypeName", (Object)null, (Object)null));
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

   public String getTimeTypeName() {
      return this.beanTreeNode.getTimeTypeName();
   }

   public void setTimeTypeName(String value) {
      this.beanTreeNode.setTimeTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeTypeName", (Object)null, (Object)null));
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

   public String getRealTypeName() {
      return this.beanTreeNode.getRealTypeName();
   }

   public void setRealTypeName(String value) {
      this.beanTreeNode.setRealTypeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RealTypeName", (Object)null, (Object)null));
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

   public int getCharacterColumnSize() {
      return this.beanTreeNode.getCharacterColumnSize();
   }

   public void setCharacterColumnSize(int value) {
      this.beanTreeNode.setCharacterColumnSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CharacterColumnSize", (Object)null, (Object)null));
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
