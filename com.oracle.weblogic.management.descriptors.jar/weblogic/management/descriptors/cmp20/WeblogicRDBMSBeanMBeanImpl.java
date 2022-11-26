package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicRDBMSBeanMBeanImpl extends XMLElementMBeanDelegate implements WeblogicRDBMSBeanMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_lockOrder = false;
   private int lockOrder = 0;
   private boolean isSet_useSelectForUpdate = false;
   private boolean useSelectForUpdate = false;
   private boolean isSet_checkExistsOnMethod = false;
   private boolean checkExistsOnMethod = true;
   private boolean isSet_automaticKeyGeneration = false;
   private AutomaticKeyGenerationMBean automaticKeyGeneration;
   private boolean isSet_delayDatabaseInsertUntil = false;
   private String delayDatabaseInsertUntil = "ejbPostCreate";
   private boolean isSet_tableMaps = false;
   private List tableMaps;
   private boolean isSet_weblogicQueries = false;
   private List weblogicQueries;
   private boolean isSet_tableName = false;
   private String tableName;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_dataSourceName = false;
   private String dataSourceName;
   private boolean isSet_fieldGroups = false;
   private List fieldGroups;
   private boolean isSet_instanceLockOrder = false;
   private String instanceLockOrder = "AccessOrder";
   private boolean isSet_relationshipCachings = false;
   private List relationshipCachings;

   public int getLockOrder() {
      return this.lockOrder;
   }

   public void setLockOrder(int value) {
      int old = this.lockOrder;
      this.lockOrder = value;
      this.isSet_lockOrder = value != -1;
      this.checkChange("lockOrder", old, this.lockOrder);
   }

   public boolean getUseSelectForUpdate() {
      return this.useSelectForUpdate;
   }

   public void setUseSelectForUpdate(boolean value) {
      boolean old = this.useSelectForUpdate;
      this.useSelectForUpdate = value;
      this.isSet_useSelectForUpdate = true;
      this.checkChange("useSelectForUpdate", old, this.useSelectForUpdate);
   }

   public boolean getCheckExistsOnMethod() {
      return this.checkExistsOnMethod;
   }

   public void setCheckExistsOnMethod(boolean value) {
      boolean old = this.checkExistsOnMethod;
      this.checkExistsOnMethod = value;
      this.isSet_checkExistsOnMethod = true;
      this.checkChange("checkExistsOnMethod", old, this.checkExistsOnMethod);
   }

   public AutomaticKeyGenerationMBean getAutomaticKeyGeneration() {
      return this.automaticKeyGeneration;
   }

   public void setAutomaticKeyGeneration(AutomaticKeyGenerationMBean value) {
      AutomaticKeyGenerationMBean old = this.automaticKeyGeneration;
      this.automaticKeyGeneration = value;
      this.isSet_automaticKeyGeneration = value != null;
      this.checkChange("automaticKeyGeneration", old, this.automaticKeyGeneration);
   }

   public String getDelayDatabaseInsertUntil() {
      return this.delayDatabaseInsertUntil;
   }

   public void setDelayDatabaseInsertUntil(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.delayDatabaseInsertUntil;
      this.delayDatabaseInsertUntil = value;
      this.isSet_delayDatabaseInsertUntil = value != null;
      this.checkChange("delayDatabaseInsertUntil", old, this.delayDatabaseInsertUntil);
   }

   public TableMapMBean[] getTableMaps() {
      if (this.tableMaps == null) {
         return new TableMapMBean[0];
      } else {
         TableMapMBean[] result = new TableMapMBean[this.tableMaps.size()];
         result = (TableMapMBean[])((TableMapMBean[])this.tableMaps.toArray(result));
         return result;
      }
   }

   public void setTableMaps(TableMapMBean[] value) {
      TableMapMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getTableMaps();
      }

      this.isSet_tableMaps = true;
      if (this.tableMaps == null) {
         this.tableMaps = Collections.synchronizedList(new ArrayList());
      } else {
         this.tableMaps.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.tableMaps.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("TableMaps", _oldVal, this.getTableMaps());
      }

   }

   public void addTableMap(TableMapMBean value) {
      this.isSet_tableMaps = true;
      if (this.tableMaps == null) {
         this.tableMaps = Collections.synchronizedList(new ArrayList());
      }

      this.tableMaps.add(value);
   }

   public void removeTableMap(TableMapMBean value) {
      if (this.tableMaps != null) {
         this.tableMaps.remove(value);
      }
   }

   public WeblogicQueryMBean[] getWeblogicQueries() {
      if (this.weblogicQueries == null) {
         return new WeblogicQueryMBean[0];
      } else {
         WeblogicQueryMBean[] result = new WeblogicQueryMBean[this.weblogicQueries.size()];
         result = (WeblogicQueryMBean[])((WeblogicQueryMBean[])this.weblogicQueries.toArray(result));
         return result;
      }
   }

   public void setWeblogicQueries(WeblogicQueryMBean[] value) {
      WeblogicQueryMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getWeblogicQueries();
      }

      this.isSet_weblogicQueries = true;
      if (this.weblogicQueries == null) {
         this.weblogicQueries = Collections.synchronizedList(new ArrayList());
      } else {
         this.weblogicQueries.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.weblogicQueries.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("WeblogicQueries", _oldVal, this.getWeblogicQueries());
      }

   }

   public void addWeblogicQuery(WeblogicQueryMBean value) {
      this.isSet_weblogicQueries = true;
      if (this.weblogicQueries == null) {
         this.weblogicQueries = Collections.synchronizedList(new ArrayList());
      }

      this.weblogicQueries.add(value);
   }

   public void removeWeblogicQuery(WeblogicQueryMBean value) {
      if (this.weblogicQueries != null) {
         this.weblogicQueries.remove(value);
      }
   }

   public String getTableName() {
      return this.tableName;
   }

   public void setTableName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.tableName;
      this.tableName = value;
      this.isSet_tableName = value != null;
      this.checkChange("tableName", old, this.tableName);
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public void setEJBName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbName;
      this.ejbName = value;
      this.isSet_ejbName = value != null;
      this.checkChange("ejbName", old, this.ejbName);
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public void setDataSourceName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.dataSourceName;
      this.dataSourceName = value;
      this.isSet_dataSourceName = value != null;
      this.checkChange("dataSourceName", old, this.dataSourceName);
   }

   public FieldGroupMBean[] getFieldGroups() {
      if (this.fieldGroups == null) {
         return new FieldGroupMBean[0];
      } else {
         FieldGroupMBean[] result = new FieldGroupMBean[this.fieldGroups.size()];
         result = (FieldGroupMBean[])((FieldGroupMBean[])this.fieldGroups.toArray(result));
         return result;
      }
   }

   public void setFieldGroups(FieldGroupMBean[] value) {
      FieldGroupMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getFieldGroups();
      }

      this.isSet_fieldGroups = true;
      if (this.fieldGroups == null) {
         this.fieldGroups = Collections.synchronizedList(new ArrayList());
      } else {
         this.fieldGroups.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.fieldGroups.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("FieldGroups", _oldVal, this.getFieldGroups());
      }

   }

   public void addFieldGroup(FieldGroupMBean value) {
      this.isSet_fieldGroups = true;
      if (this.fieldGroups == null) {
         this.fieldGroups = Collections.synchronizedList(new ArrayList());
      }

      this.fieldGroups.add(value);
   }

   public void removeFieldGroup(FieldGroupMBean value) {
      if (this.fieldGroups != null) {
         this.fieldGroups.remove(value);
      }
   }

   public String getInstanceLockOrder() {
      return this.instanceLockOrder;
   }

   public void setInstanceLockOrder(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.instanceLockOrder;
      this.instanceLockOrder = value;
      this.isSet_instanceLockOrder = value != null;
      this.checkChange("instanceLockOrder", old, this.instanceLockOrder);
   }

   public RelationshipCachingMBean[] getRelationshipCachings() {
      if (this.relationshipCachings == null) {
         return new RelationshipCachingMBean[0];
      } else {
         RelationshipCachingMBean[] result = new RelationshipCachingMBean[this.relationshipCachings.size()];
         result = (RelationshipCachingMBean[])((RelationshipCachingMBean[])this.relationshipCachings.toArray(result));
         return result;
      }
   }

   public void setRelationshipCachings(RelationshipCachingMBean[] value) {
      RelationshipCachingMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getRelationshipCachings();
      }

      this.isSet_relationshipCachings = true;
      if (this.relationshipCachings == null) {
         this.relationshipCachings = Collections.synchronizedList(new ArrayList());
      } else {
         this.relationshipCachings.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.relationshipCachings.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("RelationshipCachings", _oldVal, this.getRelationshipCachings());
      }

   }

   public void addRelationshipCaching(RelationshipCachingMBean value) {
      this.isSet_relationshipCachings = true;
      if (this.relationshipCachings == null) {
         this.relationshipCachings = Collections.synchronizedList(new ArrayList());
      }

      this.relationshipCachings.add(value);
   }

   public void removeRelationshipCaching(RelationshipCachingMBean value) {
      if (this.relationshipCachings != null) {
         this.relationshipCachings.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-rdbms-bean");
      result.append(">\n");
      if (null != this.getEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-name>").append(this.getEJBName()).append("</ejb-name>\n");
      }

      if (null != this.getDataSourceName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<data-source-name>").append(this.getDataSourceName()).append("</data-source-name>\n");
      }

      int i;
      if (null != this.getTableMaps()) {
         for(i = 0; i < this.getTableMaps().length; ++i) {
            result.append(this.getTableMaps()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getFieldGroups()) {
         for(i = 0; i < this.getFieldGroups().length; ++i) {
            result.append(this.getFieldGroups()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getRelationshipCachings()) {
         for(i = 0; i < this.getRelationshipCachings().length; ++i) {
            result.append(this.getRelationshipCachings()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getWeblogicQueries()) {
         for(i = 0; i < this.getWeblogicQueries().length; ++i) {
            result.append(this.getWeblogicQueries()[i].toXML(indentLevel + 2));
         }
      }

      if ((this.isSet_delayDatabaseInsertUntil || !"ejbPostCreate".equals(this.getDelayDatabaseInsertUntil())) && null != this.getDelayDatabaseInsertUntil()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<delay-database-insert-until>").append(this.getDelayDatabaseInsertUntil()).append("</delay-database-insert-until>\n");
      }

      if (this.isSet_useSelectForUpdate || this.getUseSelectForUpdate()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<use-select-for-update>").append(ToXML.capitalize(Boolean.valueOf(this.getUseSelectForUpdate()).toString())).append("</use-select-for-update>\n");
      }

      if (this.isSet_lockOrder || 0 != this.getLockOrder()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<lock-order>").append(this.getLockOrder()).append("</lock-order>\n");
      }

      if ((this.isSet_instanceLockOrder || !"AccessOrder".equals(this.getInstanceLockOrder())) && null != this.getInstanceLockOrder()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<instance-lock-order>").append(this.getInstanceLockOrder()).append("</instance-lock-order>\n");
      }

      if (null != this.getAutomaticKeyGeneration()) {
         result.append(this.getAutomaticKeyGeneration().toXML(indentLevel + 2)).append("\n");
      }

      if (this.isSet_checkExistsOnMethod || !this.getCheckExistsOnMethod()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<check-exists-on-method>").append(ToXML.capitalize(Boolean.valueOf(this.getCheckExistsOnMethod()).toString())).append("</check-exists-on-method>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-rdbms-bean>\n");
      return result.toString();
   }
}
