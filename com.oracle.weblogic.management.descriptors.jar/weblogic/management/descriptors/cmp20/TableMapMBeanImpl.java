package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class TableMapMBeanImpl extends XMLElementMBeanDelegate implements TableMapMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_tableName = false;
   private String tableName;
   private boolean isSet_optimisticColumn = false;
   private String optimisticColumn;
   private boolean isSet_fieldMaps = false;
   private List fieldMaps;
   private boolean isSet_verifyColumns = false;
   private String verifyColumns;
   private boolean isSet_verifyRows = false;
   private String verifyRows;

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

   public String getOptimisticColumn() {
      return this.optimisticColumn;
   }

   public void setOptimisticColumn(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.optimisticColumn;
      this.optimisticColumn = value;
      this.isSet_optimisticColumn = value != null;
      this.checkChange("optimisticColumn", old, this.optimisticColumn);
   }

   public FieldMapMBean[] getFieldMaps() {
      if (this.fieldMaps == null) {
         return new FieldMapMBean[0];
      } else {
         FieldMapMBean[] result = new FieldMapMBean[this.fieldMaps.size()];
         result = (FieldMapMBean[])((FieldMapMBean[])this.fieldMaps.toArray(result));
         return result;
      }
   }

   public void setFieldMaps(FieldMapMBean[] value) {
      FieldMapMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getFieldMaps();
      }

      this.isSet_fieldMaps = true;
      if (this.fieldMaps == null) {
         this.fieldMaps = Collections.synchronizedList(new ArrayList());
      } else {
         this.fieldMaps.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.fieldMaps.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("FieldMaps", _oldVal, this.getFieldMaps());
      }

   }

   public void addFieldMap(FieldMapMBean value) {
      this.isSet_fieldMaps = true;
      if (this.fieldMaps == null) {
         this.fieldMaps = Collections.synchronizedList(new ArrayList());
      }

      this.fieldMaps.add(value);
   }

   public void removeFieldMap(FieldMapMBean value) {
      if (this.fieldMaps != null) {
         this.fieldMaps.remove(value);
      }
   }

   public String getVerifyColumns() {
      return this.verifyColumns;
   }

   public void setVerifyColumns(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.verifyColumns;
      this.verifyColumns = value;
      this.isSet_verifyColumns = value != null;
      this.checkChange("verifyColumns", old, this.verifyColumns);
   }

   public String getVerifyRows() {
      return this.verifyRows;
   }

   public void setVerifyRows(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.verifyRows;
      this.verifyRows = value;
      this.isSet_verifyRows = value != null;
      this.checkChange("verifyRows", old, this.verifyRows);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<table-map");
      result.append(">\n");
      if (null != this.getTableName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<table-name>").append(this.getTableName()).append("</table-name>\n");
      }

      if (null != this.getFieldMaps()) {
         for(int i = 0; i < this.getFieldMaps().length; ++i) {
            result.append(this.getFieldMaps()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getVerifyRows()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<verify-rows>").append(this.getVerifyRows()).append("</verify-rows>\n");
      }

      if (null != this.getVerifyColumns()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<verify-columns>").append(this.getVerifyColumns()).append("</verify-columns>\n");
      }

      if (null != this.getOptimisticColumn()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<optimistic-column>").append(this.getOptimisticColumn()).append("</optimistic-column>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</table-map>\n");
      return result.toString();
   }
}
