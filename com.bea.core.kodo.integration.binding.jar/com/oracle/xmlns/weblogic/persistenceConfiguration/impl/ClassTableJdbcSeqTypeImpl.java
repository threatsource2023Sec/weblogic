package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ClassTableJdbcSeqType;
import javax.xml.namespace.QName;

public class ClassTableJdbcSeqTypeImpl extends SequenceTypeImpl implements ClassTableJdbcSeqType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "type");
   private static final QName ALLOCATE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "allocate");
   private static final QName TABLENAME$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-name");
   private static final QName IGNOREVIRTUAL$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "ignore-virtual");
   private static final QName IGNOREUNMAPPED$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "ignore-unmapped");
   private static final QName TABLE$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table");
   private static final QName PRIMARYKEYCOLUMN$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "primary-key-column");
   private static final QName USEALIASES$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-aliases");
   private static final QName SEQUENCECOLUMN$16 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "sequence-column");
   private static final QName INCREMENT$18 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "increment");

   public ClassTableJdbcSeqTypeImpl(SchemaType sType) {
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

   public boolean getIgnoreVirtual() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNOREVIRTUAL$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIgnoreVirtual() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNOREVIRTUAL$6, 0);
         return target;
      }
   }

   public boolean isSetIgnoreVirtual() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IGNOREVIRTUAL$6) != 0;
      }
   }

   public void setIgnoreVirtual(boolean ignoreVirtual) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNOREVIRTUAL$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IGNOREVIRTUAL$6);
         }

         target.setBooleanValue(ignoreVirtual);
      }
   }

   public void xsetIgnoreVirtual(XmlBoolean ignoreVirtual) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNOREVIRTUAL$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(IGNOREVIRTUAL$6);
         }

         target.set(ignoreVirtual);
      }
   }

   public void unsetIgnoreVirtual() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IGNOREVIRTUAL$6, 0);
      }
   }

   public boolean getIgnoreUnmapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNOREUNMAPPED$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIgnoreUnmapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNOREUNMAPPED$8, 0);
         return target;
      }
   }

   public boolean isSetIgnoreUnmapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IGNOREUNMAPPED$8) != 0;
      }
   }

   public void setIgnoreUnmapped(boolean ignoreUnmapped) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNOREUNMAPPED$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IGNOREUNMAPPED$8);
         }

         target.setBooleanValue(ignoreUnmapped);
      }
   }

   public void xsetIgnoreUnmapped(XmlBoolean ignoreUnmapped) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNOREUNMAPPED$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(IGNOREUNMAPPED$8);
         }

         target.set(ignoreUnmapped);
      }
   }

   public void unsetIgnoreUnmapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IGNOREUNMAPPED$8, 0);
      }
   }

   public String getTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$10, 0);
         return target;
      }
   }

   public boolean isNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLE$10) != 0;
      }
   }

   public void setTable(String table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLE$10);
         }

         target.setStringValue(table);
      }
   }

   public void xsetTable(XmlString table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$10);
         }

         target.set(table);
      }
   }

   public void setNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$10);
         }

         target.setNil();
      }
   }

   public void unsetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLE$10, 0);
      }
   }

   public String getPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYCOLUMN$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$12, 0);
         return target;
      }
   }

   public boolean isNilPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIMARYKEYCOLUMN$12) != 0;
      }
   }

   public void setPrimaryKeyColumn(String primaryKeyColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYCOLUMN$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRIMARYKEYCOLUMN$12);
         }

         target.setStringValue(primaryKeyColumn);
      }
   }

   public void xsetPrimaryKeyColumn(XmlString primaryKeyColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PRIMARYKEYCOLUMN$12);
         }

         target.set(primaryKeyColumn);
      }
   }

   public void setNilPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRIMARYKEYCOLUMN$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PRIMARYKEYCOLUMN$12);
         }

         target.setNil();
      }
   }

   public void unsetPrimaryKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIMARYKEYCOLUMN$12, 0);
      }
   }

   public boolean getUseAliases() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEALIASES$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseAliases() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEALIASES$14, 0);
         return target;
      }
   }

   public boolean isSetUseAliases() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEALIASES$14) != 0;
      }
   }

   public void setUseAliases(boolean useAliases) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEALIASES$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USEALIASES$14);
         }

         target.setBooleanValue(useAliases);
      }
   }

   public void xsetUseAliases(XmlBoolean useAliases) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEALIASES$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USEALIASES$14);
         }

         target.set(useAliases);
      }
   }

   public void unsetUseAliases() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEALIASES$14, 0);
      }
   }

   public String getSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCECOLUMN$16, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$16, 0);
         return target;
      }
   }

   public boolean isNilSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$16, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCECOLUMN$16) != 0;
      }
   }

   public void setSequenceColumn(String sequenceColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCECOLUMN$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SEQUENCECOLUMN$16);
         }

         target.setStringValue(sequenceColumn);
      }
   }

   public void xsetSequenceColumn(XmlString sequenceColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCECOLUMN$16);
         }

         target.set(sequenceColumn);
      }
   }

   public void setNilSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCECOLUMN$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCECOLUMN$16);
         }

         target.setNil();
      }
   }

   public void unsetSequenceColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEQUENCECOLUMN$16, 0);
      }
   }

   public int getIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$18, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$18, 0);
         return target;
      }
   }

   public boolean isSetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCREMENT$18) != 0;
      }
   }

   public void setIncrement(int increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INCREMENT$18);
         }

         target.setIntValue(increment);
      }
   }

   public void xsetIncrement(XmlInt increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$18, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INCREMENT$18);
         }

         target.set(increment);
      }
   }

   public void unsetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCREMENT$18, 0);
      }
   }
}
