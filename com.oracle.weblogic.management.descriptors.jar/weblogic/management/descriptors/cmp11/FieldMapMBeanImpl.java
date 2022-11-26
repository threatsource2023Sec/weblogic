package weblogic.management.descriptors.cmp11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class FieldMapMBeanImpl extends XMLElementMBeanDelegate implements FieldMapMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_dbmsColumn = false;
   private String dbmsColumn;
   private boolean isSet_cmpField = false;
   private String cmpField;

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

      result.append(ToXML.indent(indentLevel)).append("</field-map>\n");
      return result.toString();
   }
}
