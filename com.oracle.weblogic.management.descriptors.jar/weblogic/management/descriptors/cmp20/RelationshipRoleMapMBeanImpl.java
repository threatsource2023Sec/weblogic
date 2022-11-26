package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class RelationshipRoleMapMBeanImpl extends XMLElementMBeanDelegate implements RelationshipRoleMapMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_columnMaps = false;
   private List columnMaps;
   private boolean isSet_foreignKeyTable = false;
   private String foreignKeyTable;
   private boolean isSet_primaryKeyTable = false;
   private String primaryKeyTable;

   public ColumnMapMBean[] getColumnMaps() {
      if (this.columnMaps == null) {
         return new ColumnMapMBean[0];
      } else {
         ColumnMapMBean[] result = new ColumnMapMBean[this.columnMaps.size()];
         result = (ColumnMapMBean[])((ColumnMapMBean[])this.columnMaps.toArray(result));
         return result;
      }
   }

   public void setColumnMaps(ColumnMapMBean[] value) {
      ColumnMapMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getColumnMaps();
      }

      this.isSet_columnMaps = true;
      if (this.columnMaps == null) {
         this.columnMaps = Collections.synchronizedList(new ArrayList());
      } else {
         this.columnMaps.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.columnMaps.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ColumnMaps", _oldVal, this.getColumnMaps());
      }

   }

   public void addColumnMap(ColumnMapMBean value) {
      this.isSet_columnMaps = true;
      if (this.columnMaps == null) {
         this.columnMaps = Collections.synchronizedList(new ArrayList());
      }

      this.columnMaps.add(value);
   }

   public void removeColumnMap(ColumnMapMBean value) {
      if (this.columnMaps != null) {
         this.columnMaps.remove(value);
      }
   }

   public String getForeignKeyTable() {
      return this.foreignKeyTable;
   }

   public void setForeignKeyTable(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.foreignKeyTable;
      this.foreignKeyTable = value;
      this.isSet_foreignKeyTable = value != null;
      this.checkChange("foreignKeyTable", old, this.foreignKeyTable);
   }

   public String getPrimaryKeyTable() {
      return this.primaryKeyTable;
   }

   public void setPrimaryKeyTable(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.primaryKeyTable;
      this.primaryKeyTable = value;
      this.isSet_primaryKeyTable = value != null;
      this.checkChange("primaryKeyTable", old, this.primaryKeyTable);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<relationship-role-map");
      result.append(">\n");
      if (null != this.getForeignKeyTable()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<foreign-key-table>").append(this.getForeignKeyTable()).append("</foreign-key-table>\n");
      }

      if (null != this.getPrimaryKeyTable()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<primary-key-table>").append(this.getPrimaryKeyTable()).append("</primary-key-table>\n");
      }

      if (null != this.getColumnMaps()) {
         for(int i = 0; i < this.getColumnMaps().length; ++i) {
            result.append(this.getColumnMaps()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</relationship-role-map>\n");
      return result.toString();
   }
}
