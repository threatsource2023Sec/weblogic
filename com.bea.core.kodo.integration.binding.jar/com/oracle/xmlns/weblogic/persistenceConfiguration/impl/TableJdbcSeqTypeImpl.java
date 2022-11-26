package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableJdbcSeqType;
import javax.xml.namespace.QName;

public class TableJdbcSeqTypeImpl extends SequenceTypeImpl implements TableJdbcSeqType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "type");
   private static final QName ALLOCATE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "allocate");
   private static final QName TABLENAME$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-name");
   private static final QName TABLE$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table");
   private static final QName PRIMARYKEYCOLUMN$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "primary-key-column");
   private static final QName SEQUENCECOLUMN$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "sequence-column");
   private static final QName INCREMENT$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "increment");

   public TableJdbcSeqTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(TYPE$0, 0);
         return target;
      }
   }

   public boolean isSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TYPE$0) != 0;
      }
   }

   public void setType(int type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPE$0);
         }

         target.setIntValue(type);
      }
   }

   public void xsetType(XmlInt type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(TYPE$0);
         }

         target.set(type);
      }
   }

   public void unsetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TYPE$0, 0);
      }
   }

   public int getAllocate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOCATE$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetAllocate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(ALLOCATE$2, 0);
         return target;
      }
   }

   public boolean isSetAllocate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLOCATE$2) != 0;
      }
   }

   public void setAllocate(int allocate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOCATE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ALLOCATE$2);
         }

         target.setIntValue(allocate);
      }
   }

   public void xsetAllocate(XmlInt allocate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(ALLOCATE$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(ALLOCATE$2);
         }

         target.set(allocate);
      }
   }

   public void unsetAllocate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLOCATE$2, 0);
      }
   }

   public String getTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLENAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$4, 0);
         return target;
      }
   }

   public boolean isNilTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLENAME$4) != 0;
      }
   }

   public void setTableName(String tableName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLENAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLENAME$4);
         }

         target.setStringValue(tableName);
      }
   }

   public void xsetTableName(XmlString tableName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLENAME$4);
         }

         target.set(tableName);
      }
   }

   public void setNilTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLENAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLENAME$4);
         }

         target.setNil();
      }
   }

   public void unsetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLENAME$4, 0);
      }
   }

   public String getTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         return target;
      }
   }

   public boolean isNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLE$6) != 0;
      }
   }

   public void setTable(String table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLE$6);
         }

         target.setStringValue(table);
      }
   }

   public void xsetTable(XmlString table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$6);
         }

         target.set(table);
      }
   }

   public void setNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$6);
         }

         target.setNil();
      }
   }

   public void unsetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLE$6, 0);
      }
   }

   public String getPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYCOLUMN$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$8, 0);
         return target;
      }
   }

   public boolean isNilPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIMARYKEYCOLUMN$8) != 0;
      }
   }

   public void setPrimaryKeyColumn(String primaryKeyColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYCOLUMN$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRIMARYKEYCOLUMN$8);
         }

         target.setStringValue(primaryKeyColumn);
      }
   }

   public void xsetPrimaryKeyColumn(XmlString primaryKeyColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PRIMARYKEYCOLUMN$8);
         }

         target.set(primaryKeyColumn);
      }
   }

   public void setNilPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PRIMARYKEYCOLUMN$8);
         }

         target.setNil();
      }
   }

   public void unsetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIMARYKEYCOLUMN$8, 0);
      }
   }

   public String getSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCECOLUMN$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$10, 0);
         return target;
      }
   }

   public boolean isNilSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCECOLUMN$10) != 0;
      }
   }

   public void setSequenceColumn(String sequenceColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCECOLUMN$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SEQUENCECOLUMN$10);
         }

         target.setStringValue(sequenceColumn);
      }
   }

   public void xsetSequenceColumn(XmlString sequenceColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCECOLUMN$10);
         }

         target.set(sequenceColumn);
      }
   }

   public void setNilSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCECOLUMN$10);
         }

         target.setNil();
      }
   }

   public void unsetSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEQUENCECOLUMN$10, 0);
      }
   }

   public int getIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$12, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$12, 0);
         return target;
      }
   }

   public boolean isSetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCREMENT$12) != 0;
      }
   }

   public void setIncrement(int increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INCREMENT$12);
         }

         target.setIntValue(increment);
      }
   }

   public void xsetIncrement(XmlInt increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$12, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INCREMENT$12);
         }

         target.set(increment);
      }
   }

   public void unsetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCREMENT$12, 0);
      }
   }
}
