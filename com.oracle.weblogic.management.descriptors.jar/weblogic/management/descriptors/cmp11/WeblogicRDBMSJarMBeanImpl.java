package weblogic.management.descriptors.cmp11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicRDBMSJarMBeanImpl extends XMLElementMBeanDelegate implements WeblogicRDBMSJarMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_createDefaultDBMSTables = false;
   private String createDefaultDBMSTables = "Disabled";
   private boolean isSet_weblogicRDBMSBeans = false;
   private List weblogicRDBMSBeans;
   private boolean isSet_databaseType = false;
   private String databaseType;
   private boolean isSet_version = false;
   private String version;
   private boolean isSet_encoding = false;
   private String encoding;
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
      if (null != this.getWeblogicRDBMSBeans()) {
         for(int i = 0; i < this.getWeblogicRDBMSBeans().length; ++i) {
            result.append(this.getWeblogicRDBMSBeans()[i].toXML(indentLevel + 2));
         }
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

      result.append(ToXML.indent(indentLevel)).append("</weblogic-rdbms-jar>\n");
      return result.toString();
   }
}
