package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoSqlFactoryType;
import javax.xml.namespace.QName;

public class KodoSqlFactoryTypeImpl extends SqlFactoryTypeImpl implements KodoSqlFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName ADVANCEDSQL$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "advanced-sql");

   public KodoSqlFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getAdvancedSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADVANCEDSQL$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAdvancedSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADVANCEDSQL$0, 0);
         return target;
      }
   }

   public boolean isNilAdvancedSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADVANCEDSQL$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAdvancedSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADVANCEDSQL$0) != 0;
      }
   }

   public void setAdvancedSql(String advancedSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADVANCEDSQL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ADVANCEDSQL$0);
         }

         target.setStringValue(advancedSql);
      }
   }

   public void xsetAdvancedSql(XmlString advancedSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADVANCEDSQL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADVANCEDSQL$0);
         }

         target.set(advancedSql);
      }
   }

   public void setNilAdvancedSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADVANCEDSQL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADVANCEDSQL$0);
         }

         target.setNil();
      }
   }

   public void unsetAdvancedSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADVANCEDSQL$0, 0);
      }
   }
}
