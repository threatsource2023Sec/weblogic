package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MysqlDictionaryType;
import javax.xml.namespace.QName;

public class MysqlDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements MysqlDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName TABLETYPE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-type");
   private static final QName USECLOBS$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-clobs");
   private static final QName DRIVERDESERIALIZESBLOBS$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "driver-deserializes-blobs");

   public MysqlDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getTableType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLETYPE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTableType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPE$0, 0);
         return target;
      }
   }

   public boolean isNilTableType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPE$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLETYPE$0) != 0;
      }
   }

   public void setTableType(String tableType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLETYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLETYPE$0);
         }

         target.setStringValue(tableType);
      }
   }

   public void xsetTableType(XmlString tableType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLETYPE$0);
         }

         target.set(tableType);
      }
   }

   public void setNilTableType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLETYPE$0);
         }

         target.setNil();
      }
   }

   public void unsetTableType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLETYPE$0, 0);
      }
   }

   public boolean getUseClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USECLOBS$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USECLOBS$2, 0);
         return target;
      }
   }

   public boolean isSetUseClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USECLOBS$2) != 0;
      }
   }

   public void setUseClobs(boolean useClobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USECLOBS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USECLOBS$2);
         }

         target.setBooleanValue(useClobs);
      }
   }

   public void xsetUseClobs(XmlBoolean useClobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USECLOBS$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USECLOBS$2);
         }

         target.set(useClobs);
      }
   }

   public void unsetUseClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USECLOBS$2, 0);
      }
   }

   public boolean getDriverDeserializesBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DRIVERDESERIALIZESBLOBS$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDriverDeserializesBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DRIVERDESERIALIZESBLOBS$4, 0);
         return target;
      }
   }

   public boolean isSetDriverDeserializesBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DRIVERDESERIALIZESBLOBS$4) != 0;
      }
   }

   public void setDriverDeserializesBlobs(boolean driverDeserializesBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DRIVERDESERIALIZESBLOBS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DRIVERDESERIALIZESBLOBS$4);
         }

         target.setBooleanValue(driverDeserializesBlobs);
      }
   }

   public void xsetDriverDeserializesBlobs(XmlBoolean driverDeserializesBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DRIVERDESERIALIZESBLOBS$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DRIVERDESERIALIZESBLOBS$4);
         }

         target.set(driverDeserializesBlobs);
      }
   }

   public void unsetDriverDeserializesBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DRIVERDESERIALIZESBLOBS$4, 0);
      }
   }
}
