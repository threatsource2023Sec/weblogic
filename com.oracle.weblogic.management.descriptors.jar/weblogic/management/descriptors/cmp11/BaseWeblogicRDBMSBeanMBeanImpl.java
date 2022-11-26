package weblogic.management.descriptors.cmp11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class BaseWeblogicRDBMSBeanMBeanImpl extends XMLElementMBeanDelegate implements BaseWeblogicRDBMSBeanMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_tableName = false;
   private String tableName;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_dataSourceName = false;
   private String dataSourceName;

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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<base-weblogic-rdbms-bean");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</base-weblogic-rdbms-bean>\n");
      return result.toString();
   }
}
