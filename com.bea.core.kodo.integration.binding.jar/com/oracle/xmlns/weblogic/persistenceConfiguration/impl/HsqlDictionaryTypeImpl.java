package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.HsqlDictionaryType;
import javax.xml.namespace.QName;

public class HsqlDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements HsqlDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName CACHETABLES$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "cache-tables");

   public HsqlDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getCacheTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETABLES$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCacheTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CACHETABLES$0, 0);
         return target;
      }
   }

   public boolean isSetCacheTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHETABLES$0) != 0;
      }
   }

   public void setCacheTables(boolean cacheTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETABLES$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHETABLES$0);
         }

         target.setBooleanValue(cacheTables);
      }
   }

   public void xsetCacheTables(XmlBoolean cacheTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CACHETABLES$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CACHETABLES$0);
         }

         target.set(cacheTables);
      }
   }

   public void unsetCacheTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHETABLES$0, 0);
      }
   }
}
