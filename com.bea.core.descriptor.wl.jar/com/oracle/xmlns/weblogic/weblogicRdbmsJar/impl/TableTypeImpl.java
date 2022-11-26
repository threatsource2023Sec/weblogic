package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DbmsColumnType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TableNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TableType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class TableTypeImpl extends XmlComplexContentImpl implements TableType {
   private static final long serialVersionUID = 1L;
   private static final QName TABLENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "table-name");
   private static final QName DBMSCOLUMN$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "dbms-column");
   private static final QName EJBRELATIONSHIPROLENAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "ejb-relationship-role-name");

   public TableTypeImpl(SchemaType sType) {
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

   public DbmsColumnType[] getDbmsColumnArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DBMSCOLUMN$2, targetList);
         DbmsColumnType[] result = new DbmsColumnType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DbmsColumnType getDbmsColumnArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnType target = null;
         target = (DbmsColumnType)this.get_store().find_element_user(DBMSCOLUMN$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDbmsColumnArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DBMSCOLUMN$2);
      }
   }

   public void setDbmsColumnArray(DbmsColumnType[] dbmsColumnArray) {
      this.check_orphaned();
      this.arraySetterHelper(dbmsColumnArray, DBMSCOLUMN$2);
   }

   public void setDbmsColumnArray(int i, DbmsColumnType dbmsColumn) {
      this.generatedSetterHelperImpl(dbmsColumn, DBMSCOLUMN$2, i, (short)2);
   }

   public DbmsColumnType insertNewDbmsColumn(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnType target = null;
         target = (DbmsColumnType)this.get_store().insert_element_user(DBMSCOLUMN$2, i);
         return target;
      }
   }

   public DbmsColumnType addNewDbmsColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnType target = null;
         target = (DbmsColumnType)this.get_store().add_element_user(DBMSCOLUMN$2);
         return target;
      }
   }

   public void removeDbmsColumn(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DBMSCOLUMN$2, i);
      }
   }

   public String getEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EJBRELATIONSHIPROLENAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBRELATIONSHIPROLENAME$4) != 0;
      }
   }

   public void setEjbRelationshipRoleName(String ejbRelationshipRoleName) {
      this.generatedSetterHelperImpl(ejbRelationshipRoleName, EJBRELATIONSHIPROLENAME$4, 0, (short)1);
   }

   public String addNewEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EJBRELATIONSHIPROLENAME$4);
         return target;
      }
   }

   public void unsetEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBRELATIONSHIPROLENAME$4, 0);
      }
   }
}
