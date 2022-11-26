package com.oracle.weblogic.lifecycle.config;

public class ConfigDatabaseMetadata {
   private String tableName;
   private String[] columns;
   private String[] attributes;
   private String[] keys;
   private String propertyTable;
   private String propertyDescriptor;
   private String columnsList;
   private String xpath;

   public ConfigDatabaseMetadata(String tableName, String xpath, String[] columns, String[] keys) {
      this(tableName, xpath, columns, columns, keys, (String)null);
   }

   public ConfigDatabaseMetadata(String[] columns, String[] attributes, String[] keys) {
      this((String)null, (String)null, columns, attributes, keys, (String)null);
   }

   public ConfigDatabaseMetadata(String tableName, String xpath, String[] columns, String[] keys, String propertyTable) {
      this(tableName, xpath, columns, columns, keys, propertyTable);
   }

   public ConfigDatabaseMetadata(String tableName, String xpath, String[] attributes, String[] columns, String[] keys) {
      this(tableName, xpath, attributes, columns, keys, (String)null);
   }

   public ConfigDatabaseMetadata(String tableName, String xpath, String[] attributes, String[] columns, String[] keys, String propertyTable) {
      this(tableName, xpath, attributes, columns, keys, propertyTable, (String)null);
   }

   public ConfigDatabaseMetadata(String tableName, String xpath, String[] attributes, String[] columns, String[] keys, String propertyTable, String propertyDescriptor) {
      this.propertyTable = propertyTable;
      this.tableName = tableName;
      this.columns = columns;
      this.keys = keys;
      this.attributes = attributes;
      this.xpath = xpath;
      this.propertyDescriptor = propertyDescriptor;
      StringBuffer buf = new StringBuffer();
      String[] var9 = columns;
      int var10 = columns.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         String column = var9[var11];
         column = column.replace('.', '_');
         if (buf.length() > 0) {
            buf.append(",");
         }

         buf.append(column);
      }

      this.columnsList = buf.toString();
   }

   public String getPropertyTable() {
      return this.propertyTable;
   }

   public void setPropertyTable(String name) {
      this.propertyTable = name;
   }

   public String getPropertyDescriptor() {
      return this.propertyDescriptor;
   }

   public void setPropertyDescriptor(String name) {
      this.propertyDescriptor = name;
   }

   public String[] getKeys() {
      return this.keys;
   }

   public String[] getAttributes() {
      return this.attributes;
   }

   public String getTableName() {
      return this.tableName;
   }

   public void setTableName(String name) {
      this.tableName = name;
   }

   public String[] getColumns() {
      return this.columns;
   }

   public String getColumnsList() {
      return this.columnsList;
   }

   public String getColumnForAttribute(String attrName) {
      for(int i = 0; i < this.attributes.length; ++i) {
         if (this.attributes[i].equals(attrName) && i < this.columns.length) {
            return this.columns[i];
         }
      }

      return null;
   }

   public String getAttributeForColumn(String colName) {
      int indexOf = this.indexOf(this.columns, colName);
      return indexOf >= 0 ? this.attributes[indexOf] : null;
   }

   private int indexOf(String[] strings, String string) {
      for(int i = 0; i < strings.length; ++i) {
         if (strings[i].equalsIgnoreCase(string)) {
            return i;
         }
      }

      return -1;
   }

   public String[] getAttributesForColumns(String[] colNames) {
      String[] attributesForColumns = new String[colNames.length];

      for(int i = 0; i < colNames.length; ++i) {
         int index = this.indexOf(this.columns, colNames[i]);
         if (index >= 0) {
            attributesForColumns[i] = this.attributes[index];
         }
      }

      return attributesForColumns;
   }

   public boolean isKey(String attrName) {
      return this.indexOf(this.keys, attrName) >= 0;
   }

   public boolean ignoreForUpdate(String colName) {
      return "UpdatedOn".equalsIgnoreCase(colName) || "CreatedOn".equalsIgnoreCase(colName);
   }

   public String[] getAttributesForKeys() {
      return this.getAttributesForColumns(this.keys);
   }

   public String getXpath() {
      return this.xpath;
   }
}
