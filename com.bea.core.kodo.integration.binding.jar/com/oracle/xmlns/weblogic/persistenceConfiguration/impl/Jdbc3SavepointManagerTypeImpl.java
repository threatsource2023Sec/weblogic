package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Jdbc3SavepointManagerType;
import javax.xml.namespace.QName;

public class Jdbc3SavepointManagerTypeImpl extends SavepointManagerTypeImpl implements Jdbc3SavepointManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName RESTOREFIELDSTATE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "restore-field-state");

   public Jdbc3SavepointManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getRestoreFieldState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESTOREFIELDSTATE$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRestoreFieldState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RESTOREFIELDSTATE$0, 0);
         return target;
      }
   }

   public boolean isSetRestoreFieldState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESTOREFIELDSTATE$0) != 0;
      }
   }

   public void setRestoreFieldState(boolean restoreFieldState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESTOREFIELDSTATE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESTOREFIELDSTATE$0);
         }

         target.setBooleanValue(restoreFieldState);
      }
   }

   public void xsetRestoreFieldState(XmlBoolean restoreFieldState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RESTOREFIELDSTATE$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(RESTOREFIELDSTATE$0);
         }

         target.set(restoreFieldState);
      }
   }

   public void unsetRestoreFieldState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESTOREFIELDSTATE$0, 0);
      }
   }
}
