package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DatabaseSpecificSqlType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DatabaseTypeType;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import javax.xml.namespace.QName;

public class DatabaseSpecificSqlTypeImpl extends XmlComplexContentImpl implements DatabaseSpecificSqlType {
   private static final long serialVersionUID = 1L;
   private static final QName DATABASETYPE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "database-type");
   private static final QName SQL$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "sql");

   public DatabaseSpecificSqlTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DatabaseTypeType getDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseTypeType target = null;
         target = (DatabaseTypeType)this.get_store().find_element_user(DATABASETYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDatabaseType(DatabaseTypeType databaseType) {
      this.generatedSetterHelperImpl(databaseType, DATABASETYPE$0, 0, (short)1);
   }

   public DatabaseTypeType addNewDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseTypeType target = null;
         target = (DatabaseTypeType)this.get_store().add_element_user(DATABASETYPE$0);
         return target;
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
}
