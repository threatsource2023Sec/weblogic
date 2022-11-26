package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SingleJvmExclusiveLockManagerType;
import javax.xml.namespace.QName;

public class SingleJvmExclusiveLockManagerTypeImpl extends LockManagerTypeImpl implements SingleJvmExclusiveLockManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName VERSIONCHECKONREADLOCK$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "version-check-on-read-lock");
   private static final QName VERSIONUPDATEONWRITELOCK$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "version-update-on-write-lock");

   public SingleJvmExclusiveLockManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getVersionCheckOnReadLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONCHECKONREADLOCK$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetVersionCheckOnReadLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VERSIONCHECKONREADLOCK$0, 0);
         return target;
      }
   }

   public boolean isSetVersionCheckOnReadLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSIONCHECKONREADLOCK$0) != 0;
      }
   }

   public void setVersionCheckOnReadLock(boolean versionCheckOnReadLock) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONCHECKONREADLOCK$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSIONCHECKONREADLOCK$0);
         }

         target.setBooleanValue(versionCheckOnReadLock);
      }
   }

   public void xsetVersionCheckOnReadLock(XmlBoolean versionCheckOnReadLock) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VERSIONCHECKONREADLOCK$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(VERSIONCHECKONREADLOCK$0);
         }

         target.set(versionCheckOnReadLock);
      }
   }

   public void unsetVersionCheckOnReadLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSIONCHECKONREADLOCK$0, 0);
      }
   }

   public boolean getVersionUpdateOnWriteLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONUPDATEONWRITELOCK$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetVersionUpdateOnWriteLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VERSIONUPDATEONWRITELOCK$2, 0);
         return target;
      }
   }

   public boolean isSetVersionUpdateOnWriteLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSIONUPDATEONWRITELOCK$2) != 0;
      }
   }

   public void setVersionUpdateOnWriteLock(boolean versionUpdateOnWriteLock) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONUPDATEONWRITELOCK$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSIONUPDATEONWRITELOCK$2);
         }

         target.setBooleanValue(versionUpdateOnWriteLock);
      }
   }

   public void xsetVersionUpdateOnWriteLock(XmlBoolean versionUpdateOnWriteLock) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VERSIONUPDATEONWRITELOCK$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(VERSIONUPDATEONWRITELOCK$2);
         }

         target.set(versionUpdateOnWriteLock);
      }
   }

   public void unsetVersionUpdateOnWriteLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSIONUPDATEONWRITELOCK$2, 0);
      }
   }
}
