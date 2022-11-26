package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableSchemaFactoryType;
import javax.xml.namespace.QName;

public class TableSchemaFactoryTypeImpl extends SchemaFactoryTypeImpl implements TableSchemaFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName SCHEMACOLUMN$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "schema-column");
   private static final QName TABLENAME$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-name");
   private static final QName TABLE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table");
   private static final QName PRIMARYKEYCOLUMN$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "primary-key-column");

   public TableSchemaFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getSchemaColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMACOLUMN$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSchemaColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACOLUMN$0, 0);
         return target;
      }
   }

   public boolean isNilSchemaColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACOLUMN$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSchemaColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCHEMACOLUMN$0) != 0;
      }
   }

   public void setSchemaColumn(String schemaColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMACOLUMN$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SCHEMACOLUMN$0);
         }

         target.setStringValue(schemaColumn);
      }
   }

   public void xsetSchemaColumn(XmlString schemaColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACOLUMN$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SCHEMACOLUMN$0);
         }

         target.set(schemaColumn);
      }
   }

   public void setNilSchemaColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACOLUMN$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SCHEMACOLUMN$0);
         }

         target.setNil();
      }
   }

   public void unsetSchemaColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCHEMACOLUMN$0, 0);
      }
   }

   public String getTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLENAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$2, 0);
         return target;
      }
   }

   public boolean isNilTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLENAME$2) != 0;
      }
   }

   public void setTableName(String tableName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLENAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLENAME$2);
         }

         target.setStringValue(tableName);
      }
   }

   public void xsetTableName(XmlString tableName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLENAME$2);
         }

         target.set(tableName);
      }
   }

   public void setNilTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLENAME$2);
         }

         target.setNil();
      }
   }

   public void unsetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLENAME$2, 0);
      }
   }

   public String getTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$4, 0);
         return target;
      }
   }

   public boolean isNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLE$4) != 0;
      }
   }

   public void setTable(String table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLE$4);
         }

         target.setStringValue(table);
      }
   }

   public void xsetTable(XmlString table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$4);
         }

         target.set(table);
      }
   }

   public void setNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$4);
         }

         target.setNil();
      }
   }

   public void unsetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLE$4, 0);
      }
   }

   public String getPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYCOLUMN$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$6, 0);
         return target;
      }
   }

   public boolean isNilPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIMARYKEYCOLUMN$6) != 0;
      }
   }

   public void setPrimaryKeyColumn(String primaryKeyColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYCOLUMN$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRIMARYKEYCOLUMN$6);
         }

         target.setStringValue(primaryKeyColumn);
      }
   }

   public void xsetPrimaryKeyColumn(XmlString primaryKeyColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PRIMARYKEYCOLUMN$6);
         }

         target.set(primaryKeyColumn);
      }
   }

   public void setNilPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PRIMARYKEYCOLUMN$6);
         }

         target.setNil();
      }
   }

   public void unsetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIMARYKEYCOLUMN$6, 0);
      }
   }
}
