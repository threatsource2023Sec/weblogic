package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.persistenceConfiguration.InformixDictionaryType;
import javax.xml.namespace.QName;

public class InformixDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements InformixDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName LOCKMODEENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "lock-mode-enabled");
   private static final QName LOCKWAITSECONDS$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "lock-wait-seconds");
   private static final QName SWAPSCHEMAANDCATALOG$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "swap-schema-and-catalog");

   public InformixDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getLockModeEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCKMODEENABLED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetLockModeEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOCKMODEENABLED$0, 0);
         return target;
      }
   }

   public boolean isSetLockModeEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCKMODEENABLED$0) != 0;
      }
   }

   public void setLockModeEnabled(boolean lockModeEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCKMODEENABLED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCKMODEENABLED$0);
         }

         target.setBooleanValue(lockModeEnabled);
      }
   }

   public void xsetLockModeEnabled(XmlBoolean lockModeEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOCKMODEENABLED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(LOCKMODEENABLED$0);
         }

         target.set(lockModeEnabled);
      }
   }

   public void unsetLockModeEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCKMODEENABLED$0, 0);
      }
   }

   public int getLockWaitSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCKWAITSECONDS$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetLockWaitSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOCKWAITSECONDS$2, 0);
         return target;
      }
   }

   public boolean isSetLockWaitSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCKWAITSECONDS$2) != 0;
      }
   }

   public void setLockWaitSeconds(int lockWaitSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCKWAITSECONDS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCKWAITSECONDS$2);
         }

         target.setIntValue(lockWaitSeconds);
      }
   }

   public void xsetLockWaitSeconds(XmlInt lockWaitSeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOCKWAITSECONDS$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(LOCKWAITSECONDS$2);
         }

         target.set(lockWaitSeconds);
      }
   }

   public void unsetLockWaitSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCKWAITSECONDS$2, 0);
      }
   }

   public boolean getSwapSchemaAndCatalog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SWAPSCHEMAANDCATALOG$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSwapSchemaAndCatalog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SWAPSCHEMAANDCATALOG$4, 0);
         return target;
      }
   }

   public boolean isSetSwapSchemaAndCatalog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SWAPSCHEMAANDCATALOG$4) != 0;
      }
   }

   public void setSwapSchemaAndCatalog(boolean swapSchemaAndCatalog) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SWAPSCHEMAANDCATALOG$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SWAPSCHEMAANDCATALOG$4);
         }

         target.setBooleanValue(swapSchemaAndCatalog);
      }
   }

   public void xsetSwapSchemaAndCatalog(XmlBoolean swapSchemaAndCatalog) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SWAPSCHEMAANDCATALOG$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SWAPSCHEMAANDCATALOG$4);
         }

         target.set(swapSchemaAndCatalog);
      }
   }

   public void unsetSwapSchemaAndCatalog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SWAPSCHEMAANDCATALOG$4, 0);
      }
   }
}
