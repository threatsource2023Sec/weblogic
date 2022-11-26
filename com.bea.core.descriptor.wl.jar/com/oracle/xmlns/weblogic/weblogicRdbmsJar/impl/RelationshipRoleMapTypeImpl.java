package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ColumnMapType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ForeignKeyTableType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.PrimaryKeyTableType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.RelationshipRoleMapType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RelationshipRoleMapTypeImpl extends XmlComplexContentImpl implements RelationshipRoleMapType {
   private static final long serialVersionUID = 1L;
   private static final QName FOREIGNKEYTABLE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "foreign-key-table");
   private static final QName PRIMARYKEYTABLE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "primary-key-table");
   private static final QName COLUMNMAP$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "column-map");
   private static final QName ID$6 = new QName("", "id");

   public RelationshipRoleMapTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ForeignKeyTableType getForeignKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignKeyTableType target = null;
         target = (ForeignKeyTableType)this.get_store().find_element_user(FOREIGNKEYTABLE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetForeignKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FOREIGNKEYTABLE$0) != 0;
      }
   }

   public void setForeignKeyTable(ForeignKeyTableType foreignKeyTable) {
      this.generatedSetterHelperImpl(foreignKeyTable, FOREIGNKEYTABLE$0, 0, (short)1);
   }

   public ForeignKeyTableType addNewForeignKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignKeyTableType target = null;
         target = (ForeignKeyTableType)this.get_store().add_element_user(FOREIGNKEYTABLE$0);
         return target;
      }
   }

   public void unsetForeignKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FOREIGNKEYTABLE$0, 0);
      }
   }

   public PrimaryKeyTableType getPrimaryKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PrimaryKeyTableType target = null;
         target = (PrimaryKeyTableType)this.get_store().find_element_user(PRIMARYKEYTABLE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrimaryKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIMARYKEYTABLE$2) != 0;
      }
   }

   public void setPrimaryKeyTable(PrimaryKeyTableType primaryKeyTable) {
      this.generatedSetterHelperImpl(primaryKeyTable, PRIMARYKEYTABLE$2, 0, (short)1);
   }

   public PrimaryKeyTableType addNewPrimaryKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PrimaryKeyTableType target = null;
         target = (PrimaryKeyTableType)this.get_store().add_element_user(PRIMARYKEYTABLE$2);
         return target;
      }
   }

   public void unsetPrimaryKeyTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIMARYKEYTABLE$2, 0);
      }
   }

   public ColumnMapType[] getColumnMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COLUMNMAP$4, targetList);
         ColumnMapType[] result = new ColumnMapType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ColumnMapType getColumnMapArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ColumnMapType target = null;
         target = (ColumnMapType)this.get_store().find_element_user(COLUMNMAP$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfColumnMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COLUMNMAP$4);
      }
   }

   public void setColumnMapArray(ColumnMapType[] columnMapArray) {
      this.check_orphaned();
      this.arraySetterHelper(columnMapArray, COLUMNMAP$4);
   }

   public void setColumnMapArray(int i, ColumnMapType columnMap) {
      this.generatedSetterHelperImpl(columnMap, COLUMNMAP$4, i, (short)2);
   }

   public ColumnMapType insertNewColumnMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ColumnMapType target = null;
         target = (ColumnMapType)this.get_store().insert_element_user(COLUMNMAP$4, i);
         return target;
      }
   }

   public ColumnMapType addNewColumnMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ColumnMapType target = null;
         target = (ColumnMapType)this.get_store().add_element_user(COLUMNMAP$4);
         return target;
      }
   }

   public void removeColumnMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COLUMNMAP$4, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
