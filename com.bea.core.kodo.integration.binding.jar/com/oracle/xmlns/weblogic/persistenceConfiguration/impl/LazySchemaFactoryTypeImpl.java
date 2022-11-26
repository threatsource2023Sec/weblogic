package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LazySchemaFactoryType;
import javax.xml.namespace.QName;

public class LazySchemaFactoryTypeImpl extends SchemaFactoryTypeImpl implements LazySchemaFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName FOREIGNKEYS$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "foreign-keys");
   private static final QName INDEXES$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "indexes");
   private static final QName PRIMARYKEYS$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "primary-keys");

   public LazySchemaFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FOREIGNKEYS$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FOREIGNKEYS$0, 0);
         return target;
      }
   }

   public boolean isSetForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FOREIGNKEYS$0) != 0;
      }
   }

   public void setForeignKeys(boolean foreignKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FOREIGNKEYS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FOREIGNKEYS$0);
         }

         target.setBooleanValue(foreignKeys);
      }
   }

   public void xsetForeignKeys(XmlBoolean foreignKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FOREIGNKEYS$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(FOREIGNKEYS$0);
         }

         target.set(foreignKeys);
      }
   }

   public void unsetForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FOREIGNKEYS$0, 0);
      }
   }

   public boolean getIndexes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXES$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIndexes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXES$2, 0);
         return target;
      }
   }

   public boolean isSetIndexes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INDEXES$2) != 0;
      }
   }

   public void setIndexes(boolean indexes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXES$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INDEXES$2);
         }

         target.setBooleanValue(indexes);
      }
   }

   public void xsetIndexes(XmlBoolean indexes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXES$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(INDEXES$2);
         }

         target.set(indexes);
      }
   }

   public void unsetIndexes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INDEXES$2, 0);
      }
   }

   public boolean getPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYS$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PRIMARYKEYS$4, 0);
         return target;
      }
   }

   public boolean isSetPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIMARYKEYS$4) != 0;
      }
   }

   public void setPrimaryKeys(boolean primaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIMARYKEYS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRIMARYKEYS$4);
         }

         target.setBooleanValue(primaryKeys);
      }
   }

   public void xsetPrimaryKeys(XmlBoolean primaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PRIMARYKEYS$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(PRIMARYKEYS$4);
         }

         target.set(primaryKeys);
      }
   }

   public void unsetPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIMARYKEYS$4, 0);
      }
   }
}
