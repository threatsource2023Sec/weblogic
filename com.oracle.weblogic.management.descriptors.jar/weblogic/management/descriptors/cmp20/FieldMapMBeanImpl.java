package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class FieldMapMBeanImpl extends XMLElementMBeanDelegate implements FieldMapMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_dbmsColumn = false;
   private String dbmsColumn;
   private boolean isSet_groupName = false;
   private String groupName;
   private boolean isSet_cmpField = false;
   private String cmpField;
   private boolean isSet_dbmsColumnType = false;
   private String dbmsColumnType;

   public String getDBMSColumn() {
      return this.dbmsColumn;
   }

   public void setDBMSColumn(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.dbmsColumn;
      this.dbmsColumn = value;
      this.isSet_dbmsColumn = value != null;
      this.checkChange("dbmsColumn", old, this.dbmsColumn);
   }

   public String getGroupName() {
      return this.groupName;
   }

   public void setGroupName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.groupName;
      this.groupName = value;
      this.isSet_groupName = value != null;
      this.checkChange("groupName", old, this.groupName);
   }

   public String getCMPField() {
      return this.cmpField;
   }

   public void setCMPField(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cmpField;
      this.cmpField = value;
      this.isSet_cmpField = value != null;
      this.checkChange("cmpField", old, this.cmpField);
   }

   public String getDBMSColumnType() {
      return this.dbmsColumnType;
   }

   public void setDBMSColumnType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.dbmsColumnType;
      this.dbmsColumnType = value;
      this.isSet_dbmsColumnType = value != null;
      this.checkChange("dbmsColumnType", old, this.dbmsColumnType);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<field-map");
      result.append(">\n");
      if (null != this.getCMPField()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cmp-field>").append(this.getCMPField()).append("</cmp-field>\n");
      }

      if (null != this.getDBMSColumn()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<dbms-column>").append(this.getDBMSColumn()).append("</dbms-column>\n");
      }

      if (null != this.getDBMSColumnType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<dbms-column-type>").append(this.getDBMSColumnType()).append("</dbms-column-type>\n");
      }

      if (null != this.getGroupName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<group-name>").append(this.getGroupName()).append("</group-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</field-map>\n");
      return result.toString();
   }
}
