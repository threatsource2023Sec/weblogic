package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DetachOptionsFetchGroupsType;
import javax.xml.namespace.QName;

public class DetachOptionsFetchGroupsTypeImpl extends DetachStateTypeImpl implements DetachOptionsFetchGroupsType {
   private static final long serialVersionUID = 1L;
   private static final QName DETACHEDSTATEMANAGER$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detached-state-manager");
   private static final QName DETACHEDSTATETRANSIENT$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detached-state-transient");
   private static final QName ACCESSUNLOADED$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "access-unloaded");
   private static final QName DETACHEDSTATEFIELD$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detached-state-field");

   public DetachOptionsFetchGroupsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getDetachedStateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDSTATEMANAGER$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDetachedStateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDSTATEMANAGER$0, 0);
         return target;
      }
   }

   public boolean isSetDetachedStateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHEDSTATEMANAGER$0) != 0;
      }
   }

   public void setDetachedStateManager(boolean detachedStateManager) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDSTATEMANAGER$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DETACHEDSTATEMANAGER$0);
         }

         target.setBooleanValue(detachedStateManager);
      }
   }

   public void xsetDetachedStateManager(XmlBoolean detachedStateManager) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDSTATEMANAGER$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DETACHEDSTATEMANAGER$0);
         }

         target.set(detachedStateManager);
      }
   }

   public void unsetDetachedStateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHEDSTATEMANAGER$0, 0);
      }
   }

   public boolean getDetachedStateTransient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDSTATETRANSIENT$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDetachedStateTransient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDSTATETRANSIENT$2, 0);
         return target;
      }
   }

   public boolean isSetDetachedStateTransient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHEDSTATETRANSIENT$2) != 0;
      }
   }

   public void setDetachedStateTransient(boolean detachedStateTransient) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDSTATETRANSIENT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DETACHEDSTATETRANSIENT$2);
         }

         target.setBooleanValue(detachedStateTransient);
      }
   }

   public void xsetDetachedStateTransient(XmlBoolean detachedStateTransient) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDSTATETRANSIENT$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DETACHEDSTATETRANSIENT$2);
         }

         target.set(detachedStateTransient);
      }
   }

   public void unsetDetachedStateTransient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHEDSTATETRANSIENT$2, 0);
      }
   }

   public boolean getAccessUnloaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACCESSUNLOADED$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAccessUnloaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ACCESSUNLOADED$4, 0);
         return target;
      }
   }

   public boolean isSetAccessUnloaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACCESSUNLOADED$4) != 0;
      }
   }

   public void setAccessUnloaded(boolean accessUnloaded) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACCESSUNLOADED$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ACCESSUNLOADED$4);
         }

         target.setBooleanValue(accessUnloaded);
      }
   }

   public void xsetAccessUnloaded(XmlBoolean accessUnloaded) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ACCESSUNLOADED$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ACCESSUNLOADED$4);
         }

         target.set(accessUnloaded);
      }
   }

   public void unsetAccessUnloaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACCESSUNLOADED$4, 0);
      }
   }

   public boolean getDetachedStateField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDSTATEFIELD$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDetachedStateField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDSTATEFIELD$6, 0);
         return target;
      }
   }

   public boolean isSetDetachedStateField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHEDSTATEFIELD$6) != 0;
      }
   }

   public void setDetachedStateField(boolean detachedStateField) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDSTATEFIELD$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DETACHEDSTATEFIELD$6);
         }

         target.setBooleanValue(detachedStateField);
      }
   }

   public void xsetDetachedStateField(XmlBoolean detachedStateField) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDSTATEFIELD$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DETACHEDSTATEFIELD$6);
         }

         target.set(detachedStateField);
      }
   }

   public void unsetDetachedStateField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHEDSTATEFIELD$6, 0);
      }
   }
}
