package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SqlServerDictionaryType;
import javax.xml.namespace.QName;

public class SqlServerDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements SqlServerDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName UNIQUEIDENTIFIERASVARBINARY$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "unique-identifier-as-varbinary");

   public SqlServerDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getUniqueIdentifierAsVarbinary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNIQUEIDENTIFIERASVARBINARY$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUniqueIdentifierAsVarbinary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(UNIQUEIDENTIFIERASVARBINARY$0, 0);
         return target;
      }
   }

   public boolean isSetUniqueIdentifierAsVarbinary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNIQUEIDENTIFIERASVARBINARY$0) != 0;
      }
   }

   public void setUniqueIdentifierAsVarbinary(boolean uniqueIdentifierAsVarbinary) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNIQUEIDENTIFIERASVARBINARY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNIQUEIDENTIFIERASVARBINARY$0);
         }

         target.setBooleanValue(uniqueIdentifierAsVarbinary);
      }
   }

   public void xsetUniqueIdentifierAsVarbinary(XmlBoolean uniqueIdentifierAsVarbinary) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(UNIQUEIDENTIFIERASVARBINARY$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(UNIQUEIDENTIFIERASVARBINARY$0);
         }

         target.set(uniqueIdentifierAsVarbinary);
      }
   }

   public void unsetUniqueIdentifierAsVarbinary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNIQUEIDENTIFIERASVARBINARY$0, 0);
      }
   }
}
