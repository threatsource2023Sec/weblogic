package weblogic.ejb.container.cmp.rdbms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class RDBMSDependent {
   private String m_name = null;
   private String m_dataSourceName = null;
   private String m_schemaName = null;
   private String m_tableName = null;
   private List m_fieldMap = new LinkedList();
   private List m_primaryKeyField = new LinkedList();
   private List m_fieldGroup = new LinkedList();

   public String toString() {
      return "[RDBMSDependent name:" + this.m_name + " dataSource:" + this.m_dataSourceName + " schema:" + this.m_schemaName + " table:" + this.m_tableName + " fieldMap:" + this.m_fieldMap + " pkField:" + this.m_primaryKeyField + " fieldGroup:" + this.m_fieldGroup + "]";
   }

   public void setName(String name) {
      this.m_name = name;
   }

   public String getName() {
      return this.m_name;
   }

   public void setDataSourceName(String name) {
      this.m_dataSourceName = name;
   }

   public String getDataSourceName() {
      return this.m_dataSourceName;
   }

   public void setSchemaName(String name) {
      this.m_schemaName = name;
   }

   public String schemaName() {
      return this.m_schemaName;
   }

   public void setTableName(String name) {
      this.m_tableName = name;
   }

   public String getTableName() {
      return this.m_tableName;
   }

   public void addFieldMap(ObjectLink ol) {
      this.m_fieldMap.add(ol);
   }

   public Iterator getFieldMaps() {
      return this.m_fieldMap.iterator();
   }

   public void addPrimaryKeyField(String field) {
      this.m_primaryKeyField.add(field);
   }

   public Iterator getPrimaryKeyFields() {
      return this.m_primaryKeyField.iterator();
   }

   public void addFieldGroup(FieldGroup field) {
      this.m_fieldGroup.add(field);
   }

   public Iterator getFieldGroups() {
      return this.m_fieldGroup.iterator();
   }

   public static class FieldGroupField {
      private boolean m_isCMP;
      private String m_name;

      public FieldGroupField(boolean isCMP, String name) {
         this.m_isCMP = isCMP;
         this.m_name = name;
      }

      public boolean isCMP() {
         return this.m_isCMP;
      }

      public String getName() {
         return this.m_name;
      }

      public String toString() {
         return "[FieldGroupField " + (this.m_isCMP ? "CMP" : "CMR") + ":" + this.m_name + "]";
      }
   }

   public static class FieldGroup {
      private String m_name;
      private List m_fields = new LinkedList();

      public void setName(String name) {
         this.m_name = name;
      }

      public String getName() {
         return this.m_name;
      }

      public void addCMPField(String name) {
         this.m_fields.add(new FieldGroupField(true, name));
      }

      public Iterator getFields() {
         return this.m_fields.iterator();
      }

      public void addCMRField(String name) {
         this.m_fields.add(new FieldGroupField(false, name));
      }

      public String toString() {
         return "[FieldGroup name:" + this.m_name + " fields:" + this.m_fields + "]";
      }
   }
}
