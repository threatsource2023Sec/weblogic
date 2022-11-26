package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ColumnMapMBeanImpl extends XMLElementMBeanDelegate implements ColumnMapMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_foreignKeyColumn = false;
   private String foreignKeyColumn;
   private boolean isSet_keyColumn = false;
   private String keyColumn;

   public String getForeignKeyColumn() {
      return this.foreignKeyColumn;
   }

   public void setForeignKeyColumn(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.foreignKeyColumn;
      this.foreignKeyColumn = value;
      this.isSet_foreignKeyColumn = value != null;
      this.checkChange("foreignKeyColumn", old, this.foreignKeyColumn);
   }

   public String getKeyColumn() {
      return this.keyColumn;
   }

   public void setKeyColumn(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.keyColumn;
      this.keyColumn = value;
      this.isSet_keyColumn = value != null;
      this.checkChange("keyColumn", old, this.keyColumn);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<column-map");
      result.append(">\n");
      if (null != this.getForeignKeyColumn()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<foreign-key-column>").append(this.getForeignKeyColumn()).append("</foreign-key-column>\n");
      }

      if (null != this.getKeyColumn()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<key-column>").append(this.getKeyColumn()).append("</key-column>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</column-map>\n");
      return result.toString();
   }
}
