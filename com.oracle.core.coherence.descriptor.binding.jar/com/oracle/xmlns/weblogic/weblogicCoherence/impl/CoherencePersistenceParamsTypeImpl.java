package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherencePersistenceParamsType;
import javax.xml.namespace.QName;

public class CoherencePersistenceParamsTypeImpl extends XmlComplexContentImpl implements CoherencePersistenceParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTPERSISTENCEMODE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "default-persistence-mode");
   private static final QName ACTIVEDIRECTORY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "active-directory");
   private static final QName SNAPSHOTDIRECTORY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "snapshot-directory");
   private static final QName TRASHDIRECTORY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "trash-directory");

   public CoherencePersistenceParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getDefaultPersistenceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTPERSISTENCEMODE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefaultPersistenceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTPERSISTENCEMODE$0, 0);
         return target;
      }
   }

   public boolean isSetDefaultPersistenceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTPERSISTENCEMODE$0) != 0;
      }
   }

   public void setDefaultPersistenceMode(String defaultPersistenceMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTPERSISTENCEMODE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTPERSISTENCEMODE$0);
         }

         target.setStringValue(defaultPersistenceMode);
      }
   }

   public void xsetDefaultPersistenceMode(XmlString defaultPersistenceMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTPERSISTENCEMODE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTPERSISTENCEMODE$0);
         }

         target.set(defaultPersistenceMode);
      }
   }

   public void unsetDefaultPersistenceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTPERSISTENCEMODE$0, 0);
      }
   }

   public String getActiveDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACTIVEDIRECTORY$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetActiveDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTIVEDIRECTORY$2, 0);
         return target;
      }
   }

   public boolean isNilActiveDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTIVEDIRECTORY$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetActiveDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACTIVEDIRECTORY$2) != 0;
      }
   }

   public void setActiveDirectory(String activeDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACTIVEDIRECTORY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ACTIVEDIRECTORY$2);
         }

         target.setStringValue(activeDirectory);
      }
   }

   public void xsetActiveDirectory(XmlString activeDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTIVEDIRECTORY$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ACTIVEDIRECTORY$2);
         }

         target.set(activeDirectory);
      }
   }

   public void setNilActiveDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTIVEDIRECTORY$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ACTIVEDIRECTORY$2);
         }

         target.setNil();
      }
   }

   public void unsetActiveDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACTIVEDIRECTORY$2, 0);
      }
   }

   public String getSnapshotDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SNAPSHOTDIRECTORY$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSnapshotDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SNAPSHOTDIRECTORY$4, 0);
         return target;
      }
   }

   public boolean isNilSnapshotDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SNAPSHOTDIRECTORY$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSnapshotDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SNAPSHOTDIRECTORY$4) != 0;
      }
   }

   public void setSnapshotDirectory(String snapshotDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SNAPSHOTDIRECTORY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SNAPSHOTDIRECTORY$4);
         }

         target.setStringValue(snapshotDirectory);
      }
   }

   public void xsetSnapshotDirectory(XmlString snapshotDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SNAPSHOTDIRECTORY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SNAPSHOTDIRECTORY$4);
         }

         target.set(snapshotDirectory);
      }
   }

   public void setNilSnapshotDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SNAPSHOTDIRECTORY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SNAPSHOTDIRECTORY$4);
         }

         target.setNil();
      }
   }

   public void unsetSnapshotDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SNAPSHOTDIRECTORY$4, 0);
      }
   }

   public String getTrashDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRASHDIRECTORY$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTrashDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRASHDIRECTORY$6, 0);
         return target;
      }
   }

   public boolean isNilTrashDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRASHDIRECTORY$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTrashDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRASHDIRECTORY$6) != 0;
      }
   }

   public void setTrashDirectory(String trashDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRASHDIRECTORY$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRASHDIRECTORY$6);
         }

         target.setStringValue(trashDirectory);
      }
   }

   public void xsetTrashDirectory(XmlString trashDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRASHDIRECTORY$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRASHDIRECTORY$6);
         }

         target.set(trashDirectory);
      }
   }

   public void setNilTrashDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRASHDIRECTORY$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRASHDIRECTORY$6);
         }

         target.setNil();
      }
   }

   public void unsetTrashDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRASHDIRECTORY$6, 0);
      }
   }
}
