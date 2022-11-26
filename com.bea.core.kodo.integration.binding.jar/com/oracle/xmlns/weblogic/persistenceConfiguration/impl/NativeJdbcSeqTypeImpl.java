package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NativeJdbcSeqType;
import javax.xml.namespace.QName;

public class NativeJdbcSeqTypeImpl extends SequenceTypeImpl implements NativeJdbcSeqType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "type");
   private static final QName ALLOCATE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "allocate");
   private static final QName TABLENAME$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-name");
   private static final QName INITIALVALUE$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "initial-value");
   private static final QName SEQUENCE$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "sequence");
   private static final QName SEQUENCENAME$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "sequence-name");
   private static final QName FORMAT$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "format");
   private static final QName INCREMENT$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "increment");

   public NativeJdbcSeqTypeImpl(SchemaType sType) {
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

   public int getInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALVALUE$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INITIALVALUE$6, 0);
         return target;
      }
   }

   public boolean isSetInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALVALUE$6) != 0;
      }
   }

   public void setInitialValue(int initialValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALVALUE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INITIALVALUE$6);
         }

         target.setIntValue(initialValue);
      }
   }

   public void xsetInitialValue(XmlInt initialValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INITIALVALUE$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INITIALVALUE$6);
         }

         target.set(initialValue);
      }
   }

   public void unsetInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALVALUE$6, 0);
      }
   }

   public String getSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCE$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCE$8, 0);
         return target;
      }
   }

   public boolean isNilSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCE$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCE$8) != 0;
      }
   }

   public void setSequence(String sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SEQUENCE$8);
         }

         target.setStringValue(sequence);
      }
   }

   public void xsetSequence(XmlString sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCE$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCE$8);
         }

         target.set(sequence);
      }
   }

   public void setNilSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCE$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCE$8);
         }

         target.setNil();
      }
   }

   public void unsetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEQUENCE$8, 0);
      }
   }

   public String getSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCENAME$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCENAME$10, 0);
         return target;
      }
   }

   public boolean isNilSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCENAME$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCENAME$10) != 0;
      }
   }

   public void setSequenceName(String sequenceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEQUENCENAME$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SEQUENCENAME$10);
         }

         target.setStringValue(sequenceName);
      }
   }

   public void xsetSequenceName(XmlString sequenceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCENAME$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCENAME$10);
         }

         target.set(sequenceName);
      }
   }

   public void setNilSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEQUENCENAME$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEQUENCENAME$10);
         }

         target.setNil();
      }
   }

   public void unsetSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEQUENCENAME$10, 0);
      }
   }

   public String getFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORMAT$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORMAT$12, 0);
         return target;
      }
   }

   public boolean isNilFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORMAT$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FORMAT$12) != 0;
      }
   }

   public void setFormat(String format) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORMAT$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FORMAT$12);
         }

         target.setStringValue(format);
      }
   }

   public void xsetFormat(XmlString format) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORMAT$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FORMAT$12);
         }

         target.set(format);
      }
   }

   public void setNilFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORMAT$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FORMAT$12);
         }

         target.setNil();
      }
   }

   public void unsetFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FORMAT$12, 0);
      }
   }

   public int getIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$14, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$14, 0);
         return target;
      }
   }

   public boolean isSetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCREMENT$14) != 0;
      }
   }

   public void setIncrement(int increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INCREMENT$14);
         }

         target.setIntValue(increment);
      }
   }

   public void xsetIncrement(XmlInt increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$14, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INCREMENT$14);
         }

         target.set(increment);
      }
   }

   public void unsetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCREMENT$14, 0);
      }
   }
}
