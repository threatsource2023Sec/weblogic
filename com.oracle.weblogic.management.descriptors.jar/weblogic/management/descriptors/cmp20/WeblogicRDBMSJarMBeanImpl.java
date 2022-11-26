package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicRDBMSJarMBeanImpl extends XMLElementMBeanDelegate implements WeblogicRDBMSJarMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_createDefaultDBMSTables = false;
   private String createDefaultDBMSTables = "Disabled";
   private boolean isSet_databaseType = false;
   private String databaseType;
   private boolean isSet_orderDatabaseOperations = false;
   private boolean orderDatabaseOperations = true;
   private boolean isSet_weblogicRDBMSRelations = false;
   private List weblogicRDBMSRelations;
   private boolean isSet_enableBatchOperations = false;
   private boolean enableBatchOperations = true;
   private boolean isSet_encoding = false;
   private String encoding;
   private boolean isSet_compatibilityBean = false;
   private CompatibilityMBean compatibilityBean;
   private boolean isSet_defaultDbmsTablesDdl = false;
   private String defaultDbmsTablesDdl;
   private boolean isSet_weblogicRDBMSBeans = false;
   private List weblogicRDBMSBeans;
   private boolean isSet_version = false;
   private String version;
   private boolean isSet_validateDbSchemaWith = false;
   private String validateDbSchemaWith = "TableQuery";

   public String getCreateDefaultDBMSTables() {
      return this.createDefaultDBMSTables;
   }

   public void setCreateDefaultDBMSTables(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.createDefaultDBMSTables;
      this.createDefaultDBMSTables = value;
      this.isSet_createDefaultDBMSTables = value != null;
      this.checkChange("createDefaultDBMSTables", old, this.createDefaultDBMSTables);
   }

   public String getDatabaseType() {
      return this.databaseType;
   }

   public void setDatabaseType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.databaseType;
      this.databaseType = value;
      this.isSet_databaseType = value != null;
      this.checkChange("databaseType", old, this.databaseType);
   }

   public boolean getOrderDatabaseOperations() {
      return this.orderDatabaseOperations;
   }

   public void setOrderDatabaseOperations(boolean value) {
      boolean old = this.orderDatabaseOperations;
      this.orderDatabaseOperations = value;
      this.isSet_orderDatabaseOperations = true;
      this.checkChange("orderDatabaseOperations", old, this.orderDatabaseOperations);
   }

   public WeblogicRDBMSRelationMBean[] getWeblogicRDBMSRelations() {
      if (this.weblogicRDBMSRelations == null) {
         return new WeblogicRDBMSRelationMBean[0];
      } else {
         WeblogicRDBMSRelationMBean[] result = new WeblogicRDBMSRelationMBean[this.weblogicRDBMSRelations.size()];
         result = (WeblogicRDBMSRelationMBean[])((WeblogicRDBMSRelationMBean[])this.weblogicRDBMSRelations.toArray(result));
         return result;
      }
   }

   public void setWeblogicRDBMSRelations(WeblogicRDBMSRelationMBean[] value) {
      WeblogicRDBMSRelationMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getWeblogicRDBMSRelations();
      }

      this.isSet_weblogicRDBMSRelations = true;
      if (this.weblogicRDBMSRelations == null) {
         this.weblogicRDBMSRelations = Collections.synchronizedList(new ArrayList());
      } else {
         this.weblogicRDBMSRelations.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.weblogicRDBMSRelations.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("WeblogicRDBMSRelations", _oldVal, this.getWeblogicRDBMSRelations());
      }

   }

   public void addWeblogicRDBMSRelation(WeblogicRDBMSRelationMBean value) {
      this.isSet_weblogicRDBMSRelations = true;
      if (this.weblogicRDBMSRelations == null) {
         this.weblogicRDBMSRelations = Collections.synchronizedList(new ArrayList());
      }

      this.weblogicRDBMSRelations.add(value);
   }

   public void removeWeblogicRDBMSRelation(WeblogicRDBMSRelationMBean value) {
      if (this.weblogicRDBMSRelations != null) {
         this.weblogicRDBMSRelations.remove(value);
      }
   }

   public boolean getEnableBatchOperations() {
      return this.enableBatchOperations;
   }

   public void setEnableBatchOperations(boolean value) {
      boolean old = this.enableBatchOperations;
      this.enableBatchOperations = value;
      this.isSet_enableBatchOperations = true;
      this.checkChange("enableBatchOperations", old, this.enableBatchOperations);
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.encoding;
      this.encoding = value;
      this.isSet_encoding = value != null;
      this.checkChange("encoding", old, this.encoding);
   }

   public CompatibilityMBean getCompatibilityBean() {
      return this.compatibilityBean;
   }

   public void setCompatibilityBean(CompatibilityMBean value) {
      CompatibilityMBean old = this.compatibilityBean;
      this.compatibilityBean = value;
      this.isSet_compatibilityBean = value != null;
      this.checkChange("compatibilityBean", old, this.compatibilityBean);
   }

   public String getDefaultDbmsTablesDdl() {
      return this.defaultDbmsTablesDdl;
   }

   public void setDefaultDbmsTablesDdl(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.defaultDbmsTablesDdl;
      this.defaultDbmsTablesDdl = value;
      this.isSet_defaultDbmsTablesDdl = value != null;
      this.checkChange("defaultDbmsTablesDdl", old, this.defaultDbmsTablesDdl);
   }

   public WeblogicRDBMSBeanMBean[] getWeblogicRDBMSBeans() {
      if (this.weblogicRDBMSBeans == null) {
         return new WeblogicRDBMSBeanMBean[0];
      } else {
         WeblogicRDBMSBeanMBean[] result = new WeblogicRDBMSBeanMBean[this.weblogicRDBMSBeans.size()];
         result = (WeblogicRDBMSBeanMBean[])((WeblogicRDBMSBeanMBean[])this.weblogicRDBMSBeans.toArray(result));
         return result;
      }
   }

   public void setWeblogicRDBMSBeans(WeblogicRDBMSBeanMBean[] value) {
      WeblogicRDBMSBeanMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getWeblogicRDBMSBeans();
      }

      this.isSet_weblogicRDBMSBeans = true;
      if (this.weblogicRDBMSBeans == null) {
         this.weblogicRDBMSBeans = Collections.synchronizedList(new ArrayList());
      } else {
         this.weblogicRDBMSBeans.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.weblogicRDBMSBeans.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("WeblogicRDBMSBeans", _oldVal, this.getWeblogicRDBMSBeans());
      }

   }

   public void addWeblogicRDBMSBean(WeblogicRDBMSBeanMBean value) {
      this.isSet_weblogicRDBMSBeans = true;
      if (this.weblogicRDBMSBeans == null) {
         this.weblogicRDBMSBeans = Collections.synchronizedList(new ArrayList());
      }

      this.weblogicRDBMSBeans.add(value);
   }

   public void removeWeblogicRDBMSBean(WeblogicRDBMSBeanMBean value) {
      if (this.weblogicRDBMSBeans != null) {
         this.weblogicRDBMSBeans.remove(value);
      }
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.version;
      this.version = value;
      this.isSet_version = value != null;
      this.checkChange("version", old, this.version);
   }

   public String getValidateDbSchemaWith() {
      return this.validateDbSchemaWith;
   }

   public void setValidateDbSchemaWith(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.validateDbSchemaWith;
      this.validateDbSchemaWith = value;
      this.isSet_validateDbSchemaWith = value != null;
      this.checkChange("validateDbSchemaWith", old, this.validateDbSchemaWith);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-rdbms-jar");
      result.append(">\n");
      int i;
      if (null != this.getWeblogicRDBMSBeans()) {
         for(i = 0; i < this.getWeblogicRDBMSBeans().length; ++i) {
            result.append(this.getWeblogicRDBMSBeans()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getWeblogicRDBMSRelations()) {
         for(i = 0; i < this.getWeblogicRDBMSRelations().length; ++i) {
            result.append(this.getWeblogicRDBMSRelations()[i].toXML(indentLevel + 2));
         }
      }

      if (this.isSet_orderDatabaseOperations || !this.getOrderDatabaseOperations()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<order-database-operations>").append(ToXML.capitalize(Boolean.valueOf(this.getOrderDatabaseOperations()).toString())).append("</order-database-operations>\n");
      }

      if (this.isSet_enableBatchOperations || !this.getEnableBatchOperations()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<enable-batch-operations>").append(ToXML.capitalize(Boolean.valueOf(this.getEnableBatchOperations()).toString())).append("</enable-batch-operations>\n");
      }

      if ((this.isSet_createDefaultDBMSTables || !"Disabled".equals(this.getCreateDefaultDBMSTables())) && null != this.getCreateDefaultDBMSTables()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<create-default-dbms-tables>").append(this.getCreateDefaultDBMSTables()).append("</create-default-dbms-tables>\n");
      }

      if ((this.isSet_validateDbSchemaWith || !"TableQuery".equals(this.getValidateDbSchemaWith())) && null != this.getValidateDbSchemaWith()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<validate-db-schema-with>").append(this.getValidateDbSchemaWith()).append("</validate-db-schema-with>\n");
      }

      if (null != this.getDatabaseType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<database-type>").append(this.getDatabaseType()).append("</database-type>\n");
      }

      if (null != this.getDefaultDbmsTablesDdl()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<default-dbms-tables-ddl>").append(this.getDefaultDbmsTablesDdl()).append("</default-dbms-tables-ddl>\n");
      }

      if (null != this.getCompatibilityBean()) {
         result.append(this.getCompatibilityBean().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-rdbms-jar>\n");
      return result.toString();
   }
}
