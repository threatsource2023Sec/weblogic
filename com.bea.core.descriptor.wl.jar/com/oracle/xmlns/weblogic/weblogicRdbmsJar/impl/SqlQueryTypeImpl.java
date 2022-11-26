package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DatabaseSpecificSqlType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.SqlQueryType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.SqlShapeNameType;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SqlQueryTypeImpl extends XmlComplexContentImpl implements SqlQueryType {
   private static final long serialVersionUID = 1L;
   private static final QName SQLSHAPENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "sql-shape-name");
   private static final QName SQL$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "sql");
   private static final QName DATABASESPECIFICSQL$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "database-specific-sql");

   public SqlQueryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public SqlShapeNameType getSqlShapeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlShapeNameType target = null;
         target = (SqlShapeNameType)this.get_store().find_element_user(SQLSHAPENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSqlShapeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SQLSHAPENAME$0) != 0;
      }
   }

   public void setSqlShapeName(SqlShapeNameType sqlShapeName) {
      this.generatedSetterHelperImpl(sqlShapeName, SQLSHAPENAME$0, 0, (short)1);
   }

   public SqlShapeNameType addNewSqlShapeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlShapeNameType target = null;
         target = (SqlShapeNameType)this.get_store().add_element_user(SQLSHAPENAME$0);
         return target;
      }
   }

   public void unsetSqlShapeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SQLSHAPENAME$0, 0);
      }
   }

   public XsdStringType getSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(SQL$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SQL$2) != 0;
      }
   }

   public void setSql(XsdStringType sql) {
      this.generatedSetterHelperImpl(sql, SQL$2, 0, (short)1);
   }

   public XsdStringType addNewSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(SQL$2);
         return target;
      }
   }

   public void unsetSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SQL$2, 0);
      }
   }

   public DatabaseSpecificSqlType[] getDatabaseSpecificSqlArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DATABASESPECIFICSQL$4, targetList);
         DatabaseSpecificSqlType[] result = new DatabaseSpecificSqlType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DatabaseSpecificSqlType getDatabaseSpecificSqlArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseSpecificSqlType target = null;
         target = (DatabaseSpecificSqlType)this.get_store().find_element_user(DATABASESPECIFICSQL$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDatabaseSpecificSqlArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATABASESPECIFICSQL$4);
      }
   }

   public void setDatabaseSpecificSqlArray(DatabaseSpecificSqlType[] databaseSpecificSqlArray) {
      this.check_orphaned();
      this.arraySetterHelper(databaseSpecificSqlArray, DATABASESPECIFICSQL$4);
   }

   public void setDatabaseSpecificSqlArray(int i, DatabaseSpecificSqlType databaseSpecificSql) {
      this.generatedSetterHelperImpl(databaseSpecificSql, DATABASESPECIFICSQL$4, i, (short)2);
   }

   public DatabaseSpecificSqlType insertNewDatabaseSpecificSql(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseSpecificSqlType target = null;
         target = (DatabaseSpecificSqlType)this.get_store().insert_element_user(DATABASESPECIFICSQL$4, i);
         return target;
      }
   }

   public DatabaseSpecificSqlType addNewDatabaseSpecificSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseSpecificSqlType target = null;
         target = (DatabaseSpecificSqlType)this.get_store().add_element_user(DATABASESPECIFICSQL$4);
         return target;
      }
   }

   public void removeDatabaseSpecificSql(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATABASESPECIFICSQL$4, i);
      }
   }
}
