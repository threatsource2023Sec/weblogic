package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.FieldMapType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.OptimisticColumnType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TableMapType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TableNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.VerifyColumnsType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.VerifyRowsType;
import com.sun.java.xml.ns.j2Ee.XsdNonNegativeIntegerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class TableMapTypeImpl extends XmlComplexContentImpl implements TableMapType {
   private static final long serialVersionUID = 1L;
   private static final QName TABLENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "table-name");
   private static final QName FIELDMAP$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "field-map");
   private static final QName VERIFYROWS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "verify-rows");
   private static final QName VERIFYCOLUMNS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "verify-columns");
   private static final QName OPTIMISTICCOLUMN$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "optimistic-column");
   private static final QName TRIGGERUPDATESOPTIMISTICCOLUMN$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "trigger-updates-optimistic-column");
   private static final QName VERSIONCOLUMNINITIALVALUE$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "version-column-initial-value");
   private static final QName ID$14 = new QName("", "id");

   public TableMapTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TableNameType getTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableNameType target = null;
         target = (TableNameType)this.get_store().find_element_user(TABLENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setTableName(TableNameType tableName) {
      this.generatedSetterHelperImpl(tableName, TABLENAME$0, 0, (short)1);
   }

   public TableNameType addNewTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableNameType target = null;
         target = (TableNameType)this.get_store().add_element_user(TABLENAME$0);
         return target;
      }
   }

   public FieldMapType[] getFieldMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FIELDMAP$2, targetList);
         FieldMapType[] result = new FieldMapType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FieldMapType getFieldMapArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldMapType target = null;
         target = (FieldMapType)this.get_store().find_element_user(FIELDMAP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFieldMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELDMAP$2);
      }
   }

   public void setFieldMapArray(FieldMapType[] fieldMapArray) {
      this.check_orphaned();
      this.arraySetterHelper(fieldMapArray, FIELDMAP$2);
   }

   public void setFieldMapArray(int i, FieldMapType fieldMap) {
      this.generatedSetterHelperImpl(fieldMap, FIELDMAP$2, i, (short)2);
   }

   public FieldMapType insertNewFieldMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldMapType target = null;
         target = (FieldMapType)this.get_store().insert_element_user(FIELDMAP$2, i);
         return target;
      }
   }

   public FieldMapType addNewFieldMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldMapType target = null;
         target = (FieldMapType)this.get_store().add_element_user(FIELDMAP$2);
         return target;
      }
   }

   public void removeFieldMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELDMAP$2, i);
      }
   }

   public VerifyRowsType getVerifyRows() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VerifyRowsType target = null;
         target = (VerifyRowsType)this.get_store().find_element_user(VERIFYROWS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetVerifyRows() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERIFYROWS$4) != 0;
      }
   }

   public void setVerifyRows(VerifyRowsType verifyRows) {
      this.generatedSetterHelperImpl(verifyRows, VERIFYROWS$4, 0, (short)1);
   }

   public VerifyRowsType addNewVerifyRows() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VerifyRowsType target = null;
         target = (VerifyRowsType)this.get_store().add_element_user(VERIFYROWS$4);
         return target;
      }
   }

   public void unsetVerifyRows() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERIFYROWS$4, 0);
      }
   }

   public VerifyColumnsType getVerifyColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VerifyColumnsType target = null;
         target = (VerifyColumnsType)this.get_store().find_element_user(VERIFYCOLUMNS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetVerifyColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERIFYCOLUMNS$6) != 0;
      }
   }

   public void setVerifyColumns(VerifyColumnsType verifyColumns) {
      this.generatedSetterHelperImpl(verifyColumns, VERIFYCOLUMNS$6, 0, (short)1);
   }

   public VerifyColumnsType addNewVerifyColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VerifyColumnsType target = null;
         target = (VerifyColumnsType)this.get_store().add_element_user(VERIFYCOLUMNS$6);
         return target;
      }
   }

   public void unsetVerifyColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERIFYCOLUMNS$6, 0);
      }
   }

   public OptimisticColumnType getOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OptimisticColumnType target = null;
         target = (OptimisticColumnType)this.get_store().find_element_user(OPTIMISTICCOLUMN$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPTIMISTICCOLUMN$8) != 0;
      }
   }

   public void setOptimisticColumn(OptimisticColumnType optimisticColumn) {
      this.generatedSetterHelperImpl(optimisticColumn, OPTIMISTICCOLUMN$8, 0, (short)1);
   }

   public OptimisticColumnType addNewOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OptimisticColumnType target = null;
         target = (OptimisticColumnType)this.get_store().add_element_user(OPTIMISTICCOLUMN$8);
         return target;
      }
   }

   public void unsetOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPTIMISTICCOLUMN$8, 0);
      }
   }

   public TrueFalseType getTriggerUpdatesOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(TRIGGERUPDATESOPTIMISTICCOLUMN$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTriggerUpdatesOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRIGGERUPDATESOPTIMISTICCOLUMN$10) != 0;
      }
   }

   public void setTriggerUpdatesOptimisticColumn(TrueFalseType triggerUpdatesOptimisticColumn) {
      this.generatedSetterHelperImpl(triggerUpdatesOptimisticColumn, TRIGGERUPDATESOPTIMISTICCOLUMN$10, 0, (short)1);
   }

   public TrueFalseType addNewTriggerUpdatesOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(TRIGGERUPDATESOPTIMISTICCOLUMN$10);
         return target;
      }
   }

   public void unsetTriggerUpdatesOptimisticColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRIGGERUPDATESOPTIMISTICCOLUMN$10, 0);
      }
   }

   public XsdNonNegativeIntegerType getVersionColumnInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(VERSIONCOLUMNINITIALVALUE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetVersionColumnInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSIONCOLUMNINITIALVALUE$12) != 0;
      }
   }

   public void setVersionColumnInitialValue(XsdNonNegativeIntegerType versionColumnInitialValue) {
      this.generatedSetterHelperImpl(versionColumnInitialValue, VERSIONCOLUMNINITIALVALUE$12, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewVersionColumnInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(VERSIONCOLUMNINITIALVALUE$12);
         return target;
      }
   }

   public void unsetVersionColumnInitialValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSIONCOLUMNINITIALVALUE$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
