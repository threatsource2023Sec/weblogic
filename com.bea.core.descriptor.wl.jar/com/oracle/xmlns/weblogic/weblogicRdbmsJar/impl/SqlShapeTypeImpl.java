package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.SqlShapeNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.SqlShapeType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TableType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.XsdPositiveIntegerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SqlShapeTypeImpl extends XmlComplexContentImpl implements SqlShapeType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "description");
   private static final QName SQLSHAPENAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "sql-shape-name");
   private static final QName TABLE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "table");
   private static final QName PASSTHROUGHCOLUMNS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "pass-through-columns");
   private static final QName EJBRELATIONNAME$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "ejb-relation-name");

   public SqlShapeTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public SqlShapeNameType getSqlShapeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlShapeNameType target = null;
         target = (SqlShapeNameType)this.get_store().find_element_user(SQLSHAPENAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setSqlShapeName(SqlShapeNameType sqlShapeName) {
      this.generatedSetterHelperImpl(sqlShapeName, SQLSHAPENAME$2, 0, (short)1);
   }

   public SqlShapeNameType addNewSqlShapeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlShapeNameType target = null;
         target = (SqlShapeNameType)this.get_store().add_element_user(SQLSHAPENAME$2);
         return target;
      }
   }

   public TableType[] getTableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TABLE$4, targetList);
         TableType[] result = new TableType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TableType getTableArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableType target = null;
         target = (TableType)this.get_store().find_element_user(TABLE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLE$4);
      }
   }

   public void setTableArray(TableType[] tableArray) {
      this.check_orphaned();
      this.arraySetterHelper(tableArray, TABLE$4);
   }

   public void setTableArray(int i, TableType table) {
      this.generatedSetterHelperImpl(table, TABLE$4, i, (short)2);
   }

   public TableType insertNewTable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableType target = null;
         target = (TableType)this.get_store().insert_element_user(TABLE$4, i);
         return target;
      }
   }

   public TableType addNewTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableType target = null;
         target = (TableType)this.get_store().add_element_user(TABLE$4);
         return target;
      }
   }

   public void removeTable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLE$4, i);
      }
   }

   public XsdPositiveIntegerType getPassThroughColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(PASSTHROUGHCOLUMNS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPassThroughColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PASSTHROUGHCOLUMNS$6) != 0;
      }
   }

   public void setPassThroughColumns(XsdPositiveIntegerType passThroughColumns) {
      this.generatedSetterHelperImpl(passThroughColumns, PASSTHROUGHCOLUMNS$6, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewPassThroughColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(PASSTHROUGHCOLUMNS$6);
         return target;
      }
   }

   public void unsetPassThroughColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PASSTHROUGHCOLUMNS$6, 0);
      }
   }

   public String[] getEjbRelationNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBRELATIONNAME$8, targetList);
         String[] result = new String[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public String getEjbRelationNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EJBRELATIONNAME$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbRelationNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBRELATIONNAME$8);
      }
   }

   public void setEjbRelationNameArray(String[] ejbRelationNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRelationNameArray, EJBRELATIONNAME$8);
   }

   public void setEjbRelationNameArray(int i, String ejbRelationName) {
      this.generatedSetterHelperImpl(ejbRelationName, EJBRELATIONNAME$8, i, (short)2);
   }

   public String insertNewEjbRelationName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().insert_element_user(EJBRELATIONNAME$8, i);
         return target;
      }
   }

   public String addNewEjbRelationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EJBRELATIONNAME$8);
         return target;
      }
   }

   public void removeEjbRelationName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBRELATIONNAME$8, i);
      }
   }
}
